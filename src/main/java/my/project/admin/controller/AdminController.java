package my.project.admin.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import jakarta.validation.executable.ValidateOnExecution;
import lombok.extern.slf4j.Slf4j;
import my.project.admin.model.*;
import my.project.admin.payload.request.LoginRequest;
import my.project.admin.payload.request.SignupRequest;
import my.project.admin.repository.*;
import my.project.admin.security.jwt.JwtUtils;
import my.project.admin.security.service.UserDetailsImpl;
import my.project.common.api.CommonPage;
import my.project.common.api.CommonResult;
import my.project.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
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
 * @Author : Kevin Chang
 * @create 2023/9/13 上午9:43
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
     * @param request
     * @param userAgent
     * @param loginRequest
     * @return CommonResult
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult authenticateUser(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            @Valid @RequestBody LoginRequest loginRequest) {

        log.info("login==>");
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

            log.info("login exception=>" + exception.toString());
            sw.stop();

            ApiEvents apiEvents = new ApiEvents(999L, ipAddress, request.getMethod(), loginRequest.getUsername(), request.getRequestURL().toString(), 0, "failed" + exception.toString(), userAgent, 1, sw.getTotalTimeMillis());
            apiEventRepository.save(apiEvents);

            return CommonResult.unauthorized(null);
        }
    }

    /**
     * 回傳該名登入者的帳號資訊與權限角色
     * @param request
     * @param userAgent
     * @param principal
     * @return CommonResult
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult getUserInfo(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            Principal principal) {

        StopWatch sw = new StopWatch();
        sw.start("info Start");

        String ipAddress = IpUtil.getIpAddr(request);

        /**
         * 取得使用帳號 重複檢查，以防萬一，備而不用
         */

        if (principal == null) {
            return CommonResult.unauthorized(null);
        }

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

            sw.stop();

            if(menuRepository.getMenuList(user.getId()).isEmpty() ){
                return  CommonResult.failed("account did not config any menu");
            } else if (roleRepository.getRoleList(user.getId()).isEmpty()) {
                return  CommonResult.failed("account did not config any roles");
            }else{
                //Success Audit log
                ApiEvents apiEvents = new ApiEvents(user.getId(), ipAddress, request.getMethod(), user.getUsername(), request.getRequestURL().toString(), 1, "success", userAgent, 0, sw.getTotalTimeMillis());
                apiEventRepository.save(apiEvents);
                return CommonResult.success(data);
            }
        } catch (Exception exception) {
            sw.stop();
            //Failed Audit log
            ApiEvents apiEvents = new ApiEvents(999L, ipAddress, request.getMethod(), "test", request.getRequestURL().toString(), 0, "failed", userAgent, 1, sw.getTotalTimeMillis());
            apiEventRepository.save(apiEvents);

            return CommonResult.unauthorized(null);
        }
    }

    /**
     * 列舉後台用戶帳號資訊
     * @param request
     * @param userAgent
     * @param principal
     * @return CommonResult
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<User>> list(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            Principal principal,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum) {

        StopWatch sw = new StopWatch();
        sw.start("admin list Start");
        String ipAddress = IpUtil.getIpAddr(request);

        /**
         * 取得使用帳號 重複檢查，以防萬一，備而不用
         */

        if (principal == null) {
            return CommonResult.unauthorized(null);
        }

        String username = principal.getName();
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        //正文開始
        pageNum = pageNum -1;
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        List<User> userList = userRepository.getAllUser();
        Page<User> adminList = userRepository.findAll(pageable);
        sw.stop();

        //Success Audit log
        ApiEvents apiEvents = new ApiEvents(user.getId(), ipAddress, request.getMethod(), user.getUsername(), request.getRequestURL().toString(), 1, "success", userAgent, 0, sw.getTotalTimeMillis());
        apiEventRepository.save(apiEvents);

        return CommonResult.success(CommonPage.restPage(adminList));
    }

    /**
     * 變更用戶狀態，並紀錄修改者資訊與更改日期
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

        /**
         * 取得使用帳號 重複檢查，以防萬一，備而不用
         */

        if (principal == null) {
            return CommonResult.unauthorized(null);
        }

        String username = principal.getName();
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        //正文開始
        userRepository.updateById(status,user.getUsername(),user.getId(), id);
        sw.stop();

        //Success Audit log
        ApiEvents apiEvents = new ApiEvents(user.getId(), ipAddress, request.getMethod(), user.getUsername(), request.getRequestURL().toString(), 1, "success", userAgent, 0, sw.getTotalTimeMillis());
        apiEventRepository.save(apiEvents);

        return CommonResult.success(null);

    }

    /**
     * 新增帳號
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

        log.info("Success Audit log");
        StopWatch sw = new StopWatch();
        sw.start("updateStatus Start");
        String ipAddress = IpUtil.getIpAddr(request);

        try {

            if (userRepository.existsByUsername(signUpRequest.getUsername())) {
                return CommonResult.failed("Error: Username is already taken!");
            }

            if (userRepository.existsByEmail(signUpRequest.getEmail())) {
                return CommonResult.failed("Error: Email is already in use!");
            }

            //Organization organization = organizationRepository.getById(long(10));
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
                    (long)10,
                    "test",
                    (long)1234567890,
                    (long)1,
                    signUpRequest.getUsername()

            );

            Set<String> strRoles = signUpRequest.getRole();
            Set<Role> roles = new HashSet<>();


            userRepository.save(newUser);

            sw.stop();

            log.info("Success Audit log");


            return CommonResult.success("User registered successfully!");
        }catch (Exception exception){
            return CommonResult.failed(exception.toString());
        }

    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id, @RequestBody User admin,
            Principal principal) {

        StopWatch sw = new StopWatch();
        sw.start("updateStatus Start");
        String ipAddress = IpUtil.getIpAddr(request);

        /**
         * 取得使用帳號 重複檢查，以防萬一，備而不用
         */

        if (principal == null) {
            return CommonResult.unauthorized(null);
        }

        String username = principal.getName();
        User updateUser = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));


        User user = userRepository.getById(id);
        Organization organization = organizationRepository.getById(admin.getOrgId());
        log.info("admin.getOrgId():"+admin.getOrgId());
        log.info("orgId:"+organization.getId()+","+organization.getName_sn()+","+organization.getName()+","+organization.getCreate_time());

        admin.setUpdateTime(new Date());
        admin.setUpdateBy(updateUser.getId());
        admin.setUpdateName(updateUser.getUsername());
        admin.setOrgName(organization.getName());
        admin.setOrgSn(organization.getName_sn());
        userRepository.save(admin);
        sw.stop();

        ApiEvents apiEvents = new ApiEvents(user.getId(), ipAddress, request.getMethod(), user.getUsername(), request.getRequestURL().toString(), 1, "success", userAgent, 0, sw.getTotalTimeMillis());
        apiEventRepository.save(apiEvents);

        return CommonResult.success(null);
    }

    /**
     * 刪除該筆帳號，並刪除該筆帳號關聯的user_roles
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

        /**
         * 取得使用帳號 重複檢查，以防萬一，備而不用
         */

        if (principal == null) {
            return CommonResult.unauthorized(null);
        }

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
     * @param request
     * @param adminId
     * @param userAgent
     * @param principal
     * @return
     */
    @RequestMapping(value = "/role/{adminId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Role>> getRoleList(
            HttpServletRequest request,
            @PathVariable Long adminId,
            @RequestHeader(value = "User-Agent") String userAgent,
            Principal principal) {

        StopWatch sw = new StopWatch();
        sw.start("updateStatus Start");
        String ipAddress = IpUtil.getIpAddr(request);

        /**
         * 取得使用帳號 重複檢查，以防萬一，備而不用
         */

        if (principal == null) {
            return CommonResult.unauthorized(null);
        }

        String username = principal.getName();
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        List<Role> roleList = roleRepository.getRoleList(adminId);
        sw.stop();

        ApiEvents apiEvents = new ApiEvents(user.getId(), ipAddress, request.getMethod(), user.getUsername(), request.getRequestURL().toString(), 1, "success", userAgent, 0, sw.getTotalTimeMillis());
        apiEventRepository.save(apiEvents);

        return CommonResult.success(roleList);
    }

    /**
     * 指派角色給帳號
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

        /**
         * 取得使用帳號 重複檢查，以防萬一，備而不用
         */

        if (principal == null) {
            return CommonResult.unauthorized(null);
        }
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
}
