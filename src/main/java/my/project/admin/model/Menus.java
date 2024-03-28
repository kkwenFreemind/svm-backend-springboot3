package my.project.admin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * @Author : Kevin Chang
 * @create 2023/9/11 上午10:43
 */
@Entity
@Data
@Table(name = "menus")
public class Menus {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @Schema(description = "父級ID")
        private Long parentId;

        @Schema(description = "創建時間")
        private Date createTime;

        @Schema(description = "菜單名稱")
        private String title;

        @Schema(description = "菜單級數")
        private Integer level;

        @Schema(description = "排序")
        private Integer sort;

        @Schema(description = "前端名稱")
        private String name;

        @Schema(description = "前端圖標")
        private String icon;

        @Schema(description = "前端是否隱藏")
        private Integer hidden;

        public Menus() {

        }


}
