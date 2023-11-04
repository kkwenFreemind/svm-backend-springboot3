package com.svm.backend.admin.controller;

import com.svm.backend.admin.model.ResourceCategory;
import com.svm.backend.admin.repository.ResourceCategoryRepository;
import com.svm.backend.common.api.CommonResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by kevin on 2023/11/2 上午9:08
 */
@Slf4j
@RestController
@RequestMapping("/resourceCategory")
@Tag(name = "ResourceCategory Controller")
public class ResourceCategoryController {

    @Autowired
    ResourceCategoryRepository resourceCategoryRepository;

    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<ResourceCategory>> listAll(
            HttpServletRequest request,
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent) {

        List<ResourceCategory> resourceList = resourceCategoryRepository.findAll();

        return CommonResult.success(resourceList);
    }
}
