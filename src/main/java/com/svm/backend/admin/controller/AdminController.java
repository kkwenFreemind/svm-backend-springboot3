package com.svm.backend.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.svm.backend.admin.model.*;
import com.svm.backend.admin.payload.request.UpdateUserRequest;
import com.svm.backend.utils.PageUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.executable.ValidateOnExecution;
import lombok.extern.slf4j.Slf4j;
import com.svm.backend.admin.payload.request.LoginRequest;
import com.svm.backend.admin.payload.request.SignupRequest;
import com.svm.backend.admin.repository.*;
import com.svm.backend.admin.security.jwt.JwtUtils;
import com.svm.backend.admin.security.service.UserDetailsImpl;
import com.svm.backend.common.api.CommonPage;
import com.svm.backend.common.api.CommonResult;
import com.svm.backend.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author : Kevin Chang on 2023/9/13 上午9:43
 */
@Slf4j
@RestController
@RequestMapping("/admin")
@Tag(name = "Admin Controller")
public class AdminController {

    private final Integer initMapSize = 10;
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRolesRepository userRolesRepository;

    @Autowired
    MenuRepository menuRepository;

    @Autowired
    ApiEventRepository apiEventRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    /**
     * 帳號登入檢查
     *
     * @param request
     * @param userAgent
     * @param loginRequest
     * @return CommonResult
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult login(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            @Valid @RequestBody LoginRequest loginRequest) {

        StopWatch sw = new StopWatch();
        sw.start("Login Start");
        String ipAddress = IpUtil.getIpAddr(request);

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);

            Map<String, String> tokenMap = new HashMap<>(initMapSize);
            tokenMap.put("token", " " + jwt);
            tokenMap.put("tokenHead", "Bearer");

            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            User user = userRepository.findActiveUserByUsername(userDetails.getUsername())
                    .orElseThrow(() -> new UsernameNotFoundException("User Not Found with username: " + userDetails.getUsername()));

            if (user.getStatus() != 1) {
                return CommonResult.unauthorized("account locked");
            }

            user.setLoginTime(new Date());
            userRepository.save(user);

            sw.stop();
            ApiEvents apiEvents = new ApiEvents(user.getId(), ipAddress, request.getMethod(), user.getUsername(), request.getRequestURL().toString(), 1, "success", userAgent, 1, sw.getTotalTimeMillis());
            apiEventRepository.save(apiEvents);
            return CommonResult.success(tokenMap);

        } catch (Exception exception) {

            log.info(loginRequest.getUsername() + " login exception=>" + exception.toString());
            ApiEvents apiEvents = new ApiEvents(999L, ipAddress, request.getMethod(), loginRequest.getUsername(), request.getRequestURL().toString(), 0, "failed" + exception.toString(), userAgent, 1, sw.getTotalTimeMillis());
            apiEventRepository.save(apiEvents);

            return CommonResult.failed();
        }
    }

    /**
     * 回傳該名登入者的帳號資訊與權限角色
     *
     * @param request
     * @param userAgent
     * @param principal
     * @return CommonResult
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult info(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            Principal principal) {

        String username = principal.getName();
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        //正文開始
        try {

            Map<String, Object> data = new HashMap<>(initMapSize);
            data.put("username", user.getUsername());
            data.put("menus", menuRepository.getMenuList(user.getId()));

            List<Role> roleList = roleRepository.getRoleList(user.getId());
            if (CollUtil.isNotEmpty(roleList)) {
                List<String> roles = roleList.stream().map(Role::getName).collect(Collectors.toList());
                data.put("roles", roles);
            }


            if (menuRepository.getMenuList(user.getId()).isEmpty() || roleRepository.getRoleList(user.getId()).isEmpty()) {
                //該帳號沒有設定對應的menu or resource (api)
                log.debug(user.getUsername() + " menu list size = " + menuRepository.getMenuList(user.getId()).size());
                log.debug(user.getUsername() + " role list size = " + roleRepository.getRoleList(user.getId()).size());

                return CommonResult.failed("account did not config any resource");
            } else {
                //Success Audit log
                return CommonResult.success(data);
            }
        } catch (Exception exception) {
            //Failed Audit log
            return CommonResult.unauthorized(null);
        }
    }


    /**
     * @param request
     * @param userAgent
     * @param principal
     * @param keyword
     * @param pageNum
     * @param pageSize
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<User>> list(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            Principal principal,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize) {

        //正文開始

        List<User> adminList;
        if (Objects.isNull(keyword)) {
            adminList = userRepository.findAll();
        } else {
            adminList = userRepository.getUserByLike(keyword);
        }
        Page<User> pageData = PageUtil.listToPage(adminList, pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(pageData));
    }


    /**
     * 變更用戶狀態，並紀錄修改者資訊與更改日期
     *
     * @param request
     * @param userAgent
     * @param id
     * @param principal
     * @param status
     * @return CommonResult
     */
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateStatus(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id, Principal principal,
            @RequestParam(value = "status") Integer status) {

        StopWatch sw = new StopWatch();
        sw.start("updateStatus Start");
        String ipAddress = IpUtil.getIpAddr(request);

        String username = principal.getName();
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        //正文開始
        userRepository.updateById(status, user.getUsername(), user.getId(), id);
        sw.stop();

        //Success Audit log
        ApiEvents apiEvents = new ApiEvents(user.getId(), ipAddress, request.getMethod(), user.getUsername(), request.getRequestURL().toString(), 1, "success", userAgent, 0, sw.getTotalTimeMillis());
        apiEventRepository.save(apiEvents);

        return CommonResult.success(null);

    }

    /**
     * 新增帳號
     *
     * @param request
     * @param userAgent
     * @param signUpRequest
     * @param principal
     * @return
     */
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ResponseBody
    @ValidateOnExecution
    public CommonResult register(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            @Valid @RequestBody SignupRequest signUpRequest,
            Principal principal) {

        StopWatch sw = new StopWatch();
        sw.start("updateStatus Start");
        String ipAddress = IpUtil.getIpAddr(request);

        String username = principal.getName();
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        //正文開始
        try {
            if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                return CommonResult.failed("Error: Username is already taken!");
            }

            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return CommonResult.failed("Error: Email is already in use!");
            }

            // Create new user's account
            User newUser = new User(
                    signUpRequest.getUsername(),
                    signUpRequest.getEmail(),
                    encoder.encode(signUpRequest.getPassword()),
                    new Date(),
                    signUpRequest.getMobile(),
                    signUpRequest.getStatus(),
                    signUpRequest.getNickName(),
                    signUpRequest.getNote(),
                    user.getId(),
                    user.getUsername()
            );

            Set<String> strRoles = signUpRequest.getRole();
            Set<Role> roles = new HashSet<>();
            user.setRoles(roles);

            Organization newOrganization = organizationRepository.findById(signUpRequest.getOrg_id()).orElseThrow();
            newUser.setOrganization(newOrganization);

            userRepository.save(newUser);

            sw.stop();

            //Success Audit log
            ApiEvents apiEvents = new ApiEvents(user.getId(), ipAddress, request.getMethod(), user.getUsername(), request.getRequestURL().toString(), 1, "success", userAgent, 0, sw.getTotalTimeMillis());
            apiEventRepository.save(apiEvents);

            return CommonResult.success("User registered successfully!");
        } catch (Exception exception) {
            return CommonResult.failed(exception.toString());
        }

    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id,
            @RequestBody UpdateUserRequest updateUserRequest,
            Principal principal) {

        StopWatch sw = new StopWatch();
        sw.start("updateStatus Start");
        String ipAddress = IpUtil.getIpAddr(request);

        String username = principal.getName();
        User updateUser = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        User user = userRepository.getById(id);

        User editUser = userRepository.findById(updateUserRequest.getId()).orElseThrow();
        Organization organization = organizationRepository.findById(updateUserRequest.getOrg_id()).orElseThrow();

        editUser.setUpdateTime(new Date());
        editUser.setUpdateBy(updateUser.getId());
        editUser.setUpdateName(updateUser.getUsername());
        editUser.setOrganization(organization);
        editUser.setPassword(updateUserRequest.getPassword());
        editUser.setMobile(updateUserRequest.getMobile());
        editUser.setEmail(updateUserRequest.getEmail());
        editUser.setNote(updateUserRequest.getNote());
        editUser.setStatus(updateUserRequest.getStatus());

        userRepository.save(editUser);
        sw.stop();

        ApiEvents apiEvents = new ApiEvents(user.getId(), ipAddress, request.getMethod(), user.getUsername(), request.getRequestURL().toString(), 1, "success", userAgent, 0, sw.getTotalTimeMillis());
        apiEventRepository.save(apiEvents);

        return CommonResult.success(null);
    }

    /**
     * 刪除該筆帳號，並刪除該筆帳號關聯的user_roles
     *
     * @param request
     * @param userAgent
     * @param id
     * @param principal
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id,
            Principal principal) {

        StopWatch sw = new StopWatch();
        sw.start("updateStatus Start");
        String ipAddress = IpUtil.getIpAddr(request);

        String username = principal.getName();
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        userRepository.deleteById(id);
        userRolesRepository.deleteByUserId(id);
        sw.stop();

        ApiEvents apiEvents = new ApiEvents(user.getId(), ipAddress, request.getMethod(), user.getUsername(), request.getRequestURL().toString(), 1, "success", userAgent, 0, sw.getTotalTimeMillis());
        apiEventRepository.save(apiEvents);

        return CommonResult.success(null);

    }

    /**
     * 回傳該筆帳號的角色清單
     *
     * @param request
     * @param adminId
     * @param userAgent
     * @param principal
     * @return
     */
    @RequestMapping(value = "/role/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Role>> role(
            HttpServletRequest request,
            @PathVariable Long adminId,
            @RequestHeader(value = "User-Agent") String userAgent,
            Principal principal) {

        List<Role> roleList = roleRepository.getRoleList(adminId);
        return CommonResult.success(roleList);
    }

    /**
     * 指派角色給帳號
     *
     * @param request
     * @param adminId
     * @param roleIds
     * @param userAgent
     * @param principal
     * @return
     */
    @RequestMapping(value = "/role/update", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateRole(
            HttpServletRequest request,
            @RequestParam("adminId") Long adminId,
            @RequestParam("roleIds") List<Integer> roleIds,
            @RequestHeader(value = "User-Agent") String userAgent,
            Principal principal) {

        StopWatch sw = new StopWatch();
        sw.start("updateStatus Start");
        String ipAddress = IpUtil.getIpAddr(request);

        String username = principal.getName();
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        //正文開始
        int count = roleIds == null ? 0 : roleIds.size();
        //先删除原来的關聯
        userRolesRepository.deleteByUserId(adminId);

        //建立新關聯
        if (!CollectionUtils.isEmpty(roleIds)) {
            List<UserRoles> list = new ArrayList<>();
            for (Integer roleId : roleIds) {
                UserRoles userRoles = new UserRoles();
                userRoles.setUser_id(adminId);
                userRoles.setRole_id(roleId);
                userRolesRepository.save(userRoles);
            }
        }
        sw.stop();

        ApiEvents apiEvents = new ApiEvents(user.getId(), ipAddress, request.getMethod(), user.getUsername(), request.getRequestURL().toString(), 1, "success", userAgent, 0, sw.getTotalTimeMillis());
        apiEventRepository.save(apiEvents);
        return CommonResult.success(count);

    }

    @RequestMapping(value = "/listMyAccount", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<User>> listMyAccount(
            HttpServletRequest request,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestHeader(value = "User-Agent") String userAgent,
            Principal principal) {

        String username = principal.getName();
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        User admin = userRepository.findActiveUserByUsername(username).orElseThrow();
        List<User> userList = new ArrayList<>();
        userList.add(admin);
        Page<User> pageData = PageUtil.listToPage(userList, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(pageData));
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult logout(
            HttpServletRequest request,
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            Principal principal) {

        StopWatch sw = new StopWatch();
        sw.start("logout Start");
        //取得呼叫者的資訊
        String username = principal.getName();
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        String ipAddress = IpUtil.getIpAddr(request);

        //登出時間更新
        user.setLogoutTime(new Date());
        userRepository.save(user);

        sw.stop();
        ApiEvents apiEvents = new ApiEvents(user.getId(), ipAddress, request.getMethod(), user.getUsername(), request.getRequestURL().toString(), 1, "success", userAgent, 1, sw.getTotalTimeMillis());
        apiEventRepository.save(apiEvents);
        return CommonResult.success("ok");
    }
}
