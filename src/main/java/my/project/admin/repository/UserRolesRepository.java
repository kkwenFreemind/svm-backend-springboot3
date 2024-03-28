package my.project.admin.repository;

import jakarta.transaction.Transactional;
import my.project.admin.model.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
@Transactional
public interface UserRolesRepository  extends JpaRepository<UserRoles, Long> {
    @Query(value = "delete from user_roles where user_id=?1",nativeQuery = true)
    @Modifying
    public void deleteByUserId(Long id);

}
