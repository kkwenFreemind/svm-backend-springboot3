package com.svm.backend.admin.repository;

import jakarta.transaction.Transactional;
import com.svm.backend.admin.model.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @Author : Kevin Chang
 * @create 2023/9/6 下午4:07
 */
@Repository
@Transactional
public interface UserRepository extends JpaRepository<User, Long> {

    @Query(value = "select r.* from users r where r.username = :username and r.status = 1",nativeQuery = true)
    Optional<User> findActiveUserByUsername(@Param("username") String username);
    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query(value = "select a.*,b.org_name ,b.name_sn from users a left join organization b on (a.org_id = b.id)",nativeQuery = true)
    List<User> getAllUser();

    @Query(value = "select a.* from users a where username like CONCAT('%',:username,'%')",nativeQuery = true)
    List<User> getUserByLike(@Param("username") String username);

    @Query(value = "update users set status =?1,update_time=now(),update_name=?2,update_by=?3 where id=?4",nativeQuery = true)
    @Modifying
    public void updateById(Integer status,String updateName, Long updateUserId,Long id);




}
