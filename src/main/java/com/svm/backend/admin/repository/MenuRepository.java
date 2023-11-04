package com.svm.backend.admin.repository;


import com.svm.backend.admin.model.Menus;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author : Kevin Chang
 * @create 2023/9/11 上午11:22
 */
@Repository
public interface MenuRepository  extends JpaRepository<Menus, Long> {

    @Query(value="SELECT m.id , m.parent_id, m.create_time, m.title , m.level , m.sort , m.name , m.icon , m.hidden FROM user_roles arr LEFT JOIN roles r ON arr.role_id = r.id LEFT JOIN role_menus rmr ON r.id = rmr.role_id LEFT JOIN menus m ON rmr.menu_id = m.id WHERE arr.user_id = :sysId AND m.id IS NOT NULL GROUP BY m.id",nativeQuery=true)
    public List<Menus> getMenuList(@Param("sysId") Long sysId);

    @Query(value="SELECT m.id  id, m.parent_id, m.create_time , m.title title, m.level level, m.sort sort, m.name name, m.icon icon, m.hidden hidden  FROM role_menus rmr LEFT JOIN menus m ON rmr.menu_id = m.id WHERE rmr.role_id = :roleId AND m.id IS NOT NULL GROUP BY m.id",nativeQuery=true)
    public List<Menus> getMenuListByRoleId(@Param("roleId") Long roleId);


    @Query(value="SELECT m.* FROM menus m WHERE m.parent_id = :parentId",nativeQuery=true)
    public List<Menus> getMenuListByParentId(@Param("parentId") Long parentId);
}
