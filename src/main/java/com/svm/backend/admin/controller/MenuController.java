package com.svm.backend.admin.controller;

import com.svm.backend.admin.model.*;
import com.svm.backend.admin.repository.ApiEventRepository;
import com.svm.backend.admin.repository.MenuRepository;
import com.svm.backend.admin.repository.UserRepository;
import com.svm.backend.common.api.CommonPage;
import com.svm.backend.common.api.CommonResult;
import com.svm.backend.utils.PageUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : Kevin Chang on 2023/9/13 上午9:43
 */

@Slf4j
@RestController
@RequestMapping("/menu")
@Tag(name = "Menu Controller")
public class MenuController {

    @Autowired
    MenuRepository menuRepository;

    /**
     * @param request
     * @param userAgent
     * @param principal
     * @return
     */
    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<MenuNode>> treeList(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            Principal principal) {

        //正文開始
        List<Menus> menuList = menuRepository.findAll();
        List<MenuNode> result = menuList.stream()
                .filter(menus -> menus.getParentId().equals(0L))
                .map(menus -> covertMenuNode(menus, menuList)).collect(Collectors.toList());

        return CommonResult.success(result);
    }

    /**
     * @param menu
     * @param menuList
     * @return
     */
    private MenuNode covertMenuNode(Menus menu, List<Menus> menuList) {
        MenuNode node = new MenuNode();
        BeanUtils.copyProperties(menu, node);
        List<MenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    @RequestMapping(value = "/list/{parentId}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Menus>> list(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long parentId,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            Principal principal) {

        //正文開始
        List<Menus> menusList = menuRepository.getMenuListByParentId(parentId);
        Page<Menus> pageData = PageUtil.listToPage(menusList, pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(pageData));
    }
}
