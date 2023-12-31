package com.svm.backend.admin.model;

import lombok.Getter;
import lombok.Setter;
import java.util.List;

/**
 * @Author : Kevin Chang
 * @create 2023/10/31 上午10:05
 */

@Getter
@Setter
public class OrganizationNode extends Organization{
    private List<OrganizationNode> children;
}
