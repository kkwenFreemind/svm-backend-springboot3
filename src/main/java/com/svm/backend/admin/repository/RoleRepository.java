package com.svm.backend.admin.repository;

import com.svm.backend.admin.model.Role;
import com.svm.backend.admin.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author : Kevin Chang
 * @create 2023/9/6 下午4:06
 */
@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);

    @Query(value = "select r.* from user_roles ar left join roles r on ar.role_id = r.id where ar.user_id = :userId",nativeQuery = true)
    List<Role> getRoleList(@Param("userId") Long userId);


    @Query(value = "select a.* from roles a where name like CONCAT('%',:name,'%')",nativeQuery = true)
    List<Role> getRoleByLike(@Param("name") String name);

//    @Query(value = "select a.*, count(b.admin_id) as adminCount from ums_role a left join ums_admin_role_relation b on a.id = b.role_id group by a.id, a.name, a.description, a.create_time, a.status, a.sort",nativeQuery = true)
//    List<Role> getAllRoleAndCount();
}
