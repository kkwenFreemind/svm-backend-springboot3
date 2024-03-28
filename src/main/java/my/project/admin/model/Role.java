package my.project.admin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author : Kevin Chang
 * @create 2023/9/6 上午11:06
 */
@Entity
@Data
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20)
    private String name;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "role_menus",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id"))
    private Set<Menus> menus = new HashSet<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "role_resources",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "resource_id"))
    private Set<Resources> resources = new HashSet<>();


    @Schema(description = "描述")
    private String description;

    @Schema(description = "後台用户數目")
    private Integer adminCount;

    @Schema(description = "創建時間")
    private Date createTime;

    @Schema(description = "啟用狀態：0->停用；1->啟用")
    private Integer status;


    private Integer sort;
    public Role() {

    }


}
