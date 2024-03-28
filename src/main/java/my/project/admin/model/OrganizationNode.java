package my.project.admin.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author : Kevin Chang
 * @create 2023/10/13 下午1:45
 */
@Getter
@Setter
public class OrganizationNode extends Organization{
    private List<OrganizationNode> children;

}
