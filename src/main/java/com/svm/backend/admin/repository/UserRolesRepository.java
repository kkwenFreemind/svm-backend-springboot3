package com.svm.backend.admin.repository;

import jakarta.transaction.Transactional;
import com.svm.backend.admin.model.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@Transactional
public interface UserRolesRepository  extends JpaRepository<UserRoles, Long> {
    @Query(value = "delete from user_roles where user_id=?1",nativeQuery = true)
    @Modifying
    public void deleteByUserId(Long id);

    @Query(value = "SELECT * from user_roles where role_id = :roleId",nativeQuery = true)
    @Modifying
    public List<UserRoles> getUserByRoleId(Long roleId);

}
