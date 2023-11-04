package com.svm.backend.admin.controller;


import com.svm.backend.admin.model.Menus;
import com.svm.backend.admin.model.Resources;
import com.svm.backend.admin.repository.ResourceRepository;
import com.svm.backend.common.api.CommonPage;
import com.svm.backend.common.api.CommonResult;
import com.svm.backend.utils.PageUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by kevin on 2023/11/2 上午8:45
 */
@Slf4j
@RestController
@RequestMapping("/resource")
@Tag(name = "Resource Controller")
public class ResourceController {

    @Autowired
    ResourceRepository resourceRepository;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Resources>> list(
            HttpServletRequest request,
            @RequestParam(required = false) Long categoryId,
            @RequestParam(required = false) String nameKeyword,
            @RequestParam(required = false) String urlKeyword,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        List<Resources> resourcesList = resourceRepository.findAll();
        Page<Resources> pageData = PageUtil.listToPage(resourcesList, pageNum, pageSize);
        return CommonResult.success(CommonPage.restPage(pageData));
    }

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Resources>> listAll(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent) {

        List<Resources> resourceList = resourceRepository.findAll();
        return CommonResult.success(resourceList);
    }
}
