package my.project.admin.repository;


import my.project.admin.model.Menus;
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

    @Query(value="SELECT m.id , m.parent_id, m.create_time, m.title , m.level , m.sort , m.name , m.icon , m.hidden FROM user_roles arr LEFT JOIN testdb.roles r ON arr.role_id = r.id LEFT JOIN testdb.role_menus rmr ON r.id = rmr.role_id LEFT JOIN testdb.menus m ON rmr.menu_id = m.id WHERE arr.role_id = :sysId AND m.id IS NOT NULL GROUP BY m.id",nativeQuery=true)
    public List<Menus> getMenuList(@Param("sysId") Long sysId);

}
