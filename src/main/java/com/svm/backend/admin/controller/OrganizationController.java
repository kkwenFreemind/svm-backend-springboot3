package com.svm.backend.admin.controller;

import com.svm.backend.admin.model.OrganizationNode;
import com.svm.backend.utils.PageUtil;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import com.svm.backend.admin.model.Organization;
import com.svm.backend.admin.model.User;
import com.svm.backend.admin.repository.OrganizationRepository;
import com.svm.backend.admin.repository.UserRepository;
import com.svm.backend.common.api.CommonPage;
import com.svm.backend.common.api.CommonResult;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : Kevin Chang on 2023/10/3 上午8:54
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

    /**
     *
     * @param request
     * @param pageNum
     * @param pageSize
     * @param userAgent
     * @param principal
     * @return
     */
    @RequestMapping(value = "/listD", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Organization>> listOrganizationPage(
            HttpServletRequest request,
            @RequestParam(value = "pageNum", required = false) Integer pageNum,
            @RequestParam(value = "pageSize", required = false) Integer pageSize,
            @RequestHeader(value = "User-Agent") String userAgent,
            Principal principal) {

        String username = principal.getName();
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        //正文開始
        List<Organization> orgList = organizationRepository.findAll();
        Page<Organization> pageData = PageUtil.listToPage(orgList, pageNum, pageSize);

        return CommonResult.success(CommonPage.restPage(pageData));
    }

    /**
     *
     * @param request
     * @param principal
     * @param userAgent
     * @return
     */
    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<OrganizationNode>> treeList(
            HttpServletRequest request,
            Principal principal,
            @RequestHeader(value = "User-Agent") String userAgent) {

        List<Organization> organizationList = organizationRepository.findAll();
        List<OrganizationNode> result = organizationList.stream()
                .filter(organization -> organization.getParentId().equals(0L))
                .map(organization -> covertOrgNode(organization, organizationList)).collect(Collectors.toList());

        return CommonResult.success(result);
    }

    /**
     *
     * @param organization
     * @param organizationList
     * @return
     */
    private OrganizationNode covertOrgNode(Organization organization, List<Organization> organizationList) {
        OrganizationNode node = new OrganizationNode();
        BeanUtils.copyProperties(organization, node);
        List<OrganizationNode> children = organizationList.stream()
                .filter(subOrg -> subOrg.getParentId().equals(organization.getId()))
                .map(subOrg -> covertOrgNode(subOrg, organizationList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    /**
     *
     * @param request
     * @param principal
     * @param userAgent
     * @param pageSize
     * @param pageNum
     * @return
     */
    @RequestMapping(value = "/listLevel", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Organization>> listLevel(
            HttpServletRequest request,
            Principal principal,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Organization> orgList = organizationRepository.findAll(pageable);

        return CommonResult.success(CommonPage.restPage(orgList));
    }

    /**
     *
     * @param request
     * @param principal
     * @param userAgent
     * @param id
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Organization> getIdInfo(
            HttpServletRequest request,
            Principal principal,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id) {

        Organization organization = organizationRepository.findById(id).orElseThrow();
        log.debug("Long id:" + organization.toString());
        return CommonResult.success(organization);

    }

    /**
     *
     * @param request
     * @param principal
     * @param userAgent
     * @param id
     * @param organization
     * @return
     */
    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult update(
            HttpServletRequest request,
            Principal principal,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id,
            @RequestBody Organization organization) {

        organizationRepository.save(organization);
        return CommonResult.success(null);
    }

    /**
     *
     * @param request
     * @param userAgent
     * @param principal
     * @param id
     * @param status
     * @return
     */
    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateStatus(
            HttpServletRequest request,
            @RequestHeader(value = "User-Agent") String userAgent,
            Principal principal,
            @PathVariable Long id,
            @RequestParam(value = "status") Integer status) {

        Organization organization = organizationRepository.findById(id).orElseThrow();
        organization.setStatus(status);
        organizationRepository.save(organization);

        return CommonResult.success(null);

    }

    /**
     *
     * @param request
     * @param principal
     * @param userAgent
     * @param organization
     * @return
     */
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult create(
            HttpServletRequest request,
            Principal principal,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestBody Organization organization) {

        Organization upper_org = organizationRepository.findById(organization.getParentId()).orElseThrow();
        organization.setCreateTime(new Date());
        organization.setLevel(upper_org.getLevel() + 1);
        organizationRepository.save(organization);
        return CommonResult.success(null);
    }

    /**
     *
     * @param request
     * @param principal
     * @param userAgent
     * @param id
     * @return
     */
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult delete(
            HttpServletRequest request,
            Principal principal,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id) {

        Organization organization = organizationRepository.findById(id).orElseThrow();
        List<User> userList = userRepository.findUserByOrgId(id);
        List<Organization> organizationList = organizationRepository.getOrgListByParentId(id);
        if (organizationList.isEmpty() && userList.isEmpty()) {
            organizationRepository.deleteById(id);
        } else {
            log.debug("delete org "+organization.getOrgName()+" ,but userlist size:"+userList.size());
            log.debug("delete org "+organization.getOrgName()+",but orglist size:"+organizationList.size());
            return CommonResult.failed(organization.getOrgName() + "有下層組織或帳號，無法刪除");
        }
        return CommonResult.success(null);
    }
}
