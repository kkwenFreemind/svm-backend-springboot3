package my.project.admin.repository;

import my.project.admin.model.Organization;
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

    @Query(value = "SELECT m.* FROM organization m WHERE m.parent_id = :parentId",nativeQuery = true)
    List<Organization> getOrgListByParentId(@Param("parentId") Long parentId);

    /**
     * Get Org Data By orgName
     * @param name
     * @return
     */
    @Query(value = "SELECT m.* FROM organization m WHERE m.name = :name",nativeQuery = true)
    Organization getOrgByName(@Param("name") String name);

    /**
     * Get Max Org Id Number
     * @return
     */
    @Query(value = "SELECT count(*) as maxvalue FROM organization",nativeQuery = true)
    Long getOrgMax();

    @Query(value = "SELECT m.* FROM organization m",nativeQuery = true)
    List<Organization> listCombox(Pageable pageable);


}
