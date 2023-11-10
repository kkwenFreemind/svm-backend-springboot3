package com.svm.backend.admin.controller;

import com.svm.backend.admin.model.ResourceCategory;
import com.svm.backend.admin.repository.ResourceCategoryRepository;
import com.svm.backend.common.api.CommonResult;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(
            HttpServletRequest request,
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id,
            @RequestBody ResourceCategory resourceCategory) {

        ResourceCategory updateResourceCategory = resourceCategoryRepository.findById(id).orElseThrow();
        updateResourceCategory.setName(resourceCategory.getName());
        updateResourceCategory.setSort(resourceCategory.getSort());
        resourceCategoryRepository.save(updateResourceCategory);
        return CommonResult.success(updateResourceCategory);
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestBody ResourceCategory resourceCategory) {

        resourceCategory.setCreateTime(new Date());
        resourceCategoryRepository.save(resourceCategory);
        return CommonResult.success(resourceCategory);

    }


    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(
            HttpServletRequest request,
            @RequestHeader(value = "Authorization") String bearer,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id) {
        //沒有判斷是否有關聯資料
        resourceCategoryRepository.deleteById(id);
        return CommonResult.success(null);
    }
}
