package com.svm.backend.admin.controller;

import com.svm.backend.admin.model.*;
import com.svm.backend.admin.repository.MenuRepository;
import com.svm.backend.common.api.CommonPage;
import com.svm.backend.utils.PageUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import com.svm.backend.admin.repository.ApiEventRepository;
import com.svm.backend.admin.repository.RoleRepository;
import com.svm.backend.admin.repository.UserRepository;
import com.svm.backend.common.api.CommonResult;
import com.svm.backend.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Objects;

/**
 * @Author : Kevin Chang
 * @create 2023/10/3 上午8:45
 */
@Slf4j
@Controller
@RequestMapping("/role")
@Tag(name = "Role Controller")
public class RoleController {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    ApiEventRepository apiEventRepository;

    @Autowired
    MenuRepository menuRepository;

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Role>> listAll(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            Principal principal) {

        //取得呼叫者的資訊
        StopWatch sw = new StopWatch();
        sw.start("Role listAll Start");
        String ipAddress = IpUtil.getIpAddr(request);

        String username = principal.getName();
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        try {

            List<Role> roleList = roleRepository.findAll();

            sw.stop();
            ApiEvents apiEvents = new ApiEvents(user.getId(), ipAddress, request.getMethod(), user.getUsername(), request.getRequestURL().toString(), 1, "success", userAgent, 0, sw.getTotalTimeMillis());
            apiEventRepository.save(apiEvents);
            return CommonResult.success(roleList);

        } catch (Exception exception) {

            ApiEvents apiEvents = new ApiEvents(999L, ipAddress, request.getMethod(), username, request.getRequestURL().toString(), 0, "failed", userAgent, 1, sw.getTotalTimeMillis());
            apiEventRepository.save(apiEvents);
            return CommonResult.failed();
        }
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Role>> list(
            HttpServletRequest request,
            Principal principal,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        StopWatch sw = new StopWatch();
        sw.start("Role list Start");
        String ipAddress = IpUtil.getIpAddr(request);

        //取得呼叫者的資訊
        String username = principal.getName();
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        pageNum = pageNum - 1;
        Page<Role> roleList;
        if (Objects.isNull(keyword)) {
            Pageable pageable = PageRequest.of(pageNum, pageSize);
            roleList = roleRepository.findAll(pageable);
        } else {
            List<Role> roleByLike = roleRepository.getRoleByLike(keyword);
            roleList = PageUtil.listToPage(roleByLike, pageNum, pageSize);
        }
        sw.stop();
        ApiEvents apiEvents = new ApiEvents(user.getId(), ipAddress, request.getMethod(), user.getUsername(), request.getRequestURL().toString(), 1, "success", userAgent, 0, sw.getTotalTimeMillis());
        apiEventRepository.save(apiEvents);
        return CommonResult.success(CommonPage.restPage(roleList));
    }

    @RequestMapping(value = "/listMenu/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Menus>> listMenu(
            HttpServletRequest request,
            Principal principal,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long roleId) {

        StopWatch sw = new StopWatch();
        sw.start("Role list menu Start");
        String ipAddress = IpUtil.getIpAddr(request);

        //取得呼叫者的資訊
        String username = principal.getName();
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        List<Menus> roleListMenu = menuRepository.getMenuListByRoleId(roleId);

        sw.stop();
        ApiEvents apiEvents = new ApiEvents(user.getId(), ipAddress, request.getMethod(), user.getUsername(), request.getRequestURL().toString(), 1, "success", userAgent, 0, sw.getTotalTimeMillis());
        apiEventRepository.save(apiEvents);
        return CommonResult.success(roleListMenu);
    }


    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateStatus(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id,
            @RequestParam(value = "status") Integer status,
            Principal principal) {

        StopWatch sw = new StopWatch();
        sw.start("Role list menu Start");
        String ipAddress = IpUtil.getIpAddr(request);

        //取得呼叫者的資訊
        String username = principal.getName();
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        Role role = roleRepository.findById(id).orElseThrow();
        role.setStatus(status);
        roleRepository.save(role);

        sw.stop();
        ApiEvents apiEvents = new ApiEvents(user.getId(), ipAddress, request.getMethod(), user.getUsername(), request.getRequestURL().toString(), 1, "success", userAgent, 0, sw.getTotalTimeMillis());
        apiEventRepository.save(apiEvents);

        return CommonResult.success(null);
    }
}
