package com.svm.backend.admin.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @Author : Kevin Chang
 * @create 2023/10/3 上午10:05
 */

@Getter
@Setter
public class MenuNode extends Menus{
    private List<MenuNode> children;
}
