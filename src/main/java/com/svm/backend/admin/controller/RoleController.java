package com.svm.backend.admin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import com.svm.backend.admin.model.ApiEvents;
import com.svm.backend.admin.model.Role;
import com.svm.backend.admin.model.User;
import com.svm.backend.admin.repository.ApiEventRepository;
import com.svm.backend.admin.repository.RoleRepository;
import com.svm.backend.admin.repository.UserRepository;
import com.svm.backend.common.api.CommonResult;
import com.svm.backend.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;
import java.util.List;

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

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Role>> listAll(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            Principal principal) {

        //取得呼叫者的資訊
        StopWatch sw = new StopWatch();
        sw.start("Role listAll Start");

        //取得呼叫者的資訊
        String ipAddress = IpUtil.getIpAddr(request);

        if (principal == null) {
            return CommonResult.unauthorized(null);
        }

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
}
