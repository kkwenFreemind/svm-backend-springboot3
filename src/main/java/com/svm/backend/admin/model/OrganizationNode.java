package com.svm.backend.admin.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class OrganizationNode extends Organization{
    private List<OrganizationNode> children;
}
