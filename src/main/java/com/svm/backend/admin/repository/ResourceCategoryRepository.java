package com.svm.backend.admin.repository;

import com.svm.backend.admin.model.ResourceCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by kevin on 2023/11/2 上午9:04
 */
@Repository
public interface ResourceCategoryRepository extends JpaRepository<ResourceCategory, Long> {
}
