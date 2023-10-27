package com.svm.backend.admin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import com.svm.backend.admin.model.ApiEvents;
import com.svm.backend.admin.model.Organization;
import com.svm.backend.admin.model.User;
import com.svm.backend.admin.repository.ApiEventRepository;
import com.svm.backend.admin.repository.OrganizationRepository;
import com.svm.backend.admin.repository.UserRepository;
import com.svm.backend.common.api.CommonPage;
import com.svm.backend.common.api.CommonResult;
import com.svm.backend.utils.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

/**
 * @Author : Kevin Chang
 * @create 2023/10/3 上午8:54
 */
@Slf4j
@Controller
@RequestMapping("/org")
@Tag(name = "Organization Controller")
public class OrganizationController {

    @Autowired
    OrganizationRepository organizationRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ApiEventRepository apiEventRepository;

    @RequestMapping(value = "/listD", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Organization>> listOrganizationPage(
            HttpServletRequest request,
            @PageableDefault(page = 0, size = 15)
            @SortDefault.SortDefaults({@SortDefault(sort = "id",direction = Sort.Direction.ASC)}) Pageable pageable,
            @RequestHeader(value = "User-Agent") String userAgent,
            Principal principal){

        StopWatch sw = new StopWatch();
        sw.start("Org listD Start");

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
        Page<Organization> orglist = organizationRepository.findAll(pageable);
        sw.stop();

        //Success Audit log
        ApiEvents apiEvents = new ApiEvents(user.getId(), ipAddress, request.getMethod(), user.getUsername(), request.getRequestURL().toString(), 1, "success", userAgent, 0, sw.getTotalTimeMillis());
        apiEventRepository.save(apiEvents);

        return CommonResult.success(CommonPage.restPage(orglist));
    }
}
