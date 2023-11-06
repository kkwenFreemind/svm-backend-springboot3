package com.svm.backend.admin.repository;

import com.svm.backend.admin.model.Organization;
import com.svm.backend.admin.model.Resources;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * @Author : Kevin Chang
 * @create 2023/9/11 上午11:23
 */
@Repository
public interface ResourceRepository  extends JpaRepository<Resources, Long> {

    @Query(value = "select a.* from resources a left join role_resources b on (a.id = b.role_id) where b.role_id = :roleId",nativeQuery = true)
    List<Resources> getResourceByRoleId(@Param("roleId") Long roleId);



}
