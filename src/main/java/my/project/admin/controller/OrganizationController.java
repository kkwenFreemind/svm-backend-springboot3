package my.project.admin.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import my.project.admin.model.ApiEvents;
import my.project.admin.model.Organization;
import my.project.admin.model.OrganizationNode;
import my.project.admin.model.User;
import my.project.admin.repository.ApiEventRepository;
import my.project.admin.repository.OrganizationRepository;
import my.project.admin.repository.UserRepository;
import my.project.common.api.CommonPage;
import my.project.common.api.CommonResult;
import my.project.utils.IpUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.*;


import java.security.Principal;
import java.util.ArrayList;
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
            @RequestHeader(value = "User-Agent") String userAgent,
            Principal principal,
            @RequestParam(value = "pageSize", defaultValue = "5") int pageSize,
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum){

        //正文開始
        pageNum = pageNum -1;
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        Page<Organization> orglist = organizationRepository.findAll(pageable);

        return CommonResult.success(CommonPage.restPage(orglist));
    }

    @RequestMapping(value = "/treeList", method = RequestMethod.GET)
    @ResponseBody
    public CommonResult<List<OrganizationNode>> treeList(
            HttpServletRequest request,
            Principal principal,
            @RequestHeader(value = "User-Agent") String userAgent) {

        //正文
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
        log.info("Long id:"+organization.getName());
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

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult createOrganization(
            HttpServletRequest request,
            Principal principal,
            @RequestHeader(value = "User-Agent") String userAgent,
            @RequestBody Organization organization) {

        Organization upper_org = organizationRepository.findById(organization.getParent_id()).orElseThrow();
        organization.setCreate_time(new Date());
        organization.setLevel(upper_org.getLevel()+1);
        organizationRepository.save(organization);
        return CommonResult.success(null);
    }
}
