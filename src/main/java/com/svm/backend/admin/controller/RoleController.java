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
 * @Author : Kevin Chang on 2023/10/3 上午8:45
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

        List<Role> roleList = roleRepository.findAll();
        return CommonResult.success(roleList);
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

        pageNum = pageNum -1 ;
        Page<Role> roleList;
        if (Objects.isNull(keyword)) {
            Pageable pageable = PageRequest.of(pageNum, pageSize);
            roleList = roleRepository.findAll(pageable);
        } else {
            List<Role> roleByLike = roleRepository.getRoleByLike(keyword);
            roleList = PageUtil.listToPage(roleByLike, pageNum, pageSize);
        }
        return CommonResult.success(CommonPage.restPage(roleList));
    }

    @RequestMapping(value = "/listMenu/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Menus>> listMenu(
            HttpServletRequest request,
            Principal principal,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long roleId) {

        List<Menus> roleListMenu = menuRepository.getMenuListByRoleId(roleId);
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

        Role role = roleRepository.findById(id).orElseThrow();
        role.setStatus(status);
        roleRepository.save(role);
        return CommonResult.success(null);
    }
}
