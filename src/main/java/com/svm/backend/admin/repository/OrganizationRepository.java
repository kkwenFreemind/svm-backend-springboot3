package com.svm.backend.admin.repository;

import com.svm.backend.admin.model.Organization;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author : Kevin Chang
 * @create 2023/10/3 上午10:20
 */
@Repository
public interface OrganizationRepository extends JpaRepository<Organization, Long> {

    /**
     * 顯示該單位下是否有sub單位
     * @param parentId
     * @return
     */
    @Query(value = "SELECT m.* FROM organization m WHERE m.parent_id = :parentId",nativeQuery = true)
    List<Organization> getOrgListByParentId(@Param("parentId") Long parentId);


}
