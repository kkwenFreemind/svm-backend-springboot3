package com.svm.backend.admin.controller;

import com.svm.backend.admin.model.ApiEvents;
import com.svm.backend.admin.model.User;
import com.svm.backend.admin.repository.ApiEventRepository;
import com.svm.backend.admin.repository.UserRepository;
import com.svm.backend.common.api.CommonPage;
import com.svm.backend.common.api.CommonResult;
import com.svm.backend.utils.PageUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @Author : Kevin Chang on 2023/9/13 上午9:43
 */
@Slf4j
@RestController
@RequestMapping("/operate")
@Tag(name = "ApiEvent Controller")
public class ApiEventController {

    @Autowired
    ApiEventRepository apiEventRepository;

    @Autowired
    UserRepository userRepository;

    @RequestMapping(value = "/listType", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<ApiEvents>> listType(
            HttpServletRequest request,
            @RequestParam(value = "keyword", required = false) String keyword,
            @RequestParam(value = "startDateTime", required = false) String startDateTime,
            @RequestParam(value = "endDateTime", required = false) String endDateTime,
            @RequestParam(value = "logType", required = false) Integer logType,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            Principal principal) {

        //取得呼叫者的資訊
        String username = principal.getName();
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        //正文開始, list to page
        List<ApiEvents> apiEventsList =new ArrayList<>();
        if(Objects.isNull(keyword)){
            apiEventsList = apiEventRepository.getEventByType(user.getId(),logType);
        }else{

        }
        Page<ApiEvents> pageData = PageUtil.listToPage(apiEventsList, pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(pageData));
    }
}
