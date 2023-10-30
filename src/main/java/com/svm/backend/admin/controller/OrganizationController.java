package com.svm.backend.admin.controller;

import com.svm.backend.admin.model.OrganizationNode;
import com.svm.backend.utils.PageUtil;
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
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
            @RequestParam(value = "pageNum") Integer pageNum,
            @RequestParam(value = "pageSize") Integer pageSize,
            @RequestHeader(value = "User-Agent") String userAgent,
            Principal principal){

        StopWatch sw = new StopWatch();
        sw.start("Org listD Start");

        String ipAddress = IpUtil.getIpAddr(request);

        String username = principal.getName();
        User user = userRepository.findActiveUserByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User Not Found: " + username));

        //正文開始
        pageNum = pageNum -1 ;
        List<Organization> orgList = organizationRepository.findAll();
        Page<Organization> pageData = PageUtil.listToPage(orgList, pageNum, pageSize);
        sw.stop();

        //Success Audit log
        ApiEvents apiEvents = new ApiEvents(user.getId(), ipAddress, request.getMethod(), user.getUsername(), request.getRequestURL().toString(), 1, "success", userAgent, 0, sw.getTotalTimeMillis());
        apiEventRepository.save(apiEvents);

        return CommonResult.success(CommonPage.restPage(pageData));
    }

    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<OrganizationNode>> treeList(
            HttpServletRequest request,
            Principal principal,
            @RequestHeader(value = "User-Agent") String userAgent) {

        List<Organization> organizationList = organizationRepository.findAll();
        List<OrganizationNode> result = organizationList.stream()
                .filter(organization -> organization.getParent_id().equals(0L))
                .map(organization -> covertOrgNode(organization, organizationList)).collect(Collectors.toList());

        return CommonResult.success(result);
    }
    private OrganizationNode covertOrgNode(Organization organization, List<Organization> organizationList) {
        OrganizationNode node = new OrganizationNode();
        BeanUtils.copyProperties(organization, node);
        List<OrganizationNode> children = organizationList.stream()
                .filter(subOrg -> subOrg.getParent_id().equals(organization.getId()))
                .map(subOrg -> covertOrgNode(subOrg, organizationList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }

    @RequestMapping(value = "/listLevel", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<CommonPage<Organization>> listLevel(
            HttpServletRequest request,
            Principal principal,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum) {

        pageNum = pageNum -1;
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Organization> orgList = organizationRepository.findAll(pageable);

        return CommonResult.success(CommonPage.restPage(orgList));
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<Organization> getItem(
            HttpServletRequest request,
            Principal principal,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id) {

        Organization organization = organizationRepository.findById(id).orElseThrow();
        log.info("Long id:"+organization.getOrg_name());
        return CommonResult.success(organization);

    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateOrganization(
            HttpServletRequest request,
            Principal principal,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id,
            @RequestBody Organization organization) {

        organizationRepository.save(organization);
        return CommonResult.success(null);
    }

    @RequestMapping(value = "/updateStatus/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult updateOrganizationStatus(
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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createOrganization(
            HttpServletRequest request,
            Principal principal,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestBody Organization organization) {

        Organization upper_org = organizationRepository.findById(organization.getParent_id()).orElseThrow();
        organization.setCreateTime(new Date());
        organization.setLevel(upper_org.getLevel()+1);
        organizationRepository.save(organization);
        return CommonResult.success(null);
    }

    @RequestMapping(value = "/delete/{id}", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult deleteOrganization(
            HttpServletRequest request,
            Principal principal,
            @RequestHeader(value = "User-Agent") String userAgent,
            @PathVariable Long id) {

        Organization organization = organizationRepository.findById(id).orElseThrow();
        List<Organization> organizationList = organizationRepository.getOrgListByParentId(id);
        if(organizationList.isEmpty()){
            organizationRepository.deleteById(id);
        }else{
            return CommonResult.failed("有下層組織，無法刪除");
        }
        return CommonResult.success(null);
    }
}
