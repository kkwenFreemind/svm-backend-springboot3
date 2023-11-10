package com.svm.backend.admin.controller;

import com.svm.backend.admin.model.*;
import com.svm.backend.admin.repository.*;
import com.svm.backend.common.api.CommonPage;
import com.svm.backend.utils.IpUtil;
import com.svm.backend.utils.PageUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
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
import java.util.stream.Collectors;

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
    MenuRepository menuRepository;

    @Autowired
    ResourceRepository resourceRepository;

    @Autowired
    UserRolesRepository userRolesRepository;

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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestBody Role role,
            Principal principal) {

        //正文開始
        log.debug(role.getName()+","+role.getDescription()+","+role.getStatus());
        Role newRole = new Role(
                role.getName(),
                role.getDescription(),
                role.getStatus()
        );
        roleRepository.save(newRole);
        return CommonResult.success(role);
    }

    @RequestMapping(value = "/allocMenu", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult allocMenu(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestParam Long roleId,
            @RequestParam List<Long> menuIds,
            Principal principal) {

        //刪除roleId的menu關聯
        Role role = roleRepository.findById(roleId).orElseThrow();
        log.info("role menu size:"+role.getMenus().size());
        List<Menus> copiedList = role.getMenus().stream().collect(Collectors.toList());
        for (Menus menus : copiedList) {
            Menus test = menuRepository.findById(menus.getId()).orElseThrow();
            role.getMenus().remove(test);
        }
        log.debug("role menu size::"+role.getMenus().size());

        //批量插入新關聯
        for(Long id: menuIds){
            log.debug("id::"+id);
            Menus test = menuRepository.findById(id).orElseThrow();
            role.getMenus().add(test);
        }
        log.debug("role menu size:::"+role.getMenus().size());
        roleRepository.save(role);

        return CommonResult.success(null);
    }

    @RequestMapping(value = "/listResource/{roleId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<Resources>> listResource(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long roleId,
            Principal principal) {

        List<Resources> listResource =resourceRepository.getResourceByRoleId(roleId);
        return CommonResult.success(listResource);
    }

    @RequestMapping(value = "/allocResource", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult allocResource(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestParam Long roleId,
            @RequestParam List<Long> resourceIds,
            Principal principal) {

        //先删除原有關聯
        Role role = roleRepository.findById(roleId).orElseThrow();
        List<Resources> copiedList = role.getResources().stream().collect(Collectors.toList());
        for (Resources resources : copiedList) {
            Resources test = resourceRepository.findById(resources.getId()).orElseThrow();
            role.getResources().remove(test);
        }
        //批量插入新關聯
        for(Long id: resourceIds){
            Resources test = resourceRepository.findById(id).orElseThrow();
            role.getResources().add(test);
        }
        roleRepository.save(role);

        return CommonResult.success(null);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id,
            @RequestBody Role role,
            Principal principal) {

        Role updateRole = roleRepository.findById(id).orElseThrow();
        updateRole.setStatus(role.getStatus());
        updateRole.setDescription(role.getDescription());
        updateRole.setName(role.getName());
        roleRepository.save(updateRole);
        return CommonResult.success(updateRole);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestParam("ids") List<Long> roleIds,
            Principal principal) {

        //是否有用戶使用該角色
        for(Long id: roleIds){
            log.debug("roleId:"+id);
            List<UserRoles> userRolesList  = userRolesRepository.getUserByRoleId(id);
            if(userRolesList.size()>0){
                return CommonResult.failed("有用戶帳號使用該角色，無法刪除");
            }
        }
        roleRepository.deleteAllById(roleIds);
        return CommonResult.success(null);
    }


}
