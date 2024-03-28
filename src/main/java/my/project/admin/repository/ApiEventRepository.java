package my.project.admin.repository;

import my.project.admin.model.ApiEvents;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author : Kevin Chang
 * @create 2023/9/12 上午11:08
 */
@Repository
public interface ApiEventRepository extends JpaRepository<ApiEvents, Long> {
}
