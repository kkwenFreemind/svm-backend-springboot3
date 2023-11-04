package com.svm.backend.admin.repository;

import com.svm.backend.admin.model.ApiEvents;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author : Kevin Chang
 * @create 2023/9/12 上午11:08
 */
@Repository
public interface ApiEventRepository extends JpaRepository<ApiEvents, Long> {

    @Query(value="SELECT a.* FROM api_events a where a.user_id = :userId and a.create_time between :fromdate and :todate",nativeQuery=true)
    public List<ApiEvents> getEventByDate(@Param("userId") Long userId,@Param("fromdate") String fromdate, @Param("todate") String todate);

    @Query(value="SELECT a.* FROM api_events a where a.user_id = :userId and a.log_type = :logType",nativeQuery=true)
    public List<ApiEvents> getEventByType(@Param("userId") Long userId,@Param("logType") Integer logType);

}
