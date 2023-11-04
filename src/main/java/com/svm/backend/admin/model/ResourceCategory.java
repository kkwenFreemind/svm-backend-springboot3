package com.svm.backend.admin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * Created by kevin on 2023/11/2 上午8:55
 */
@Entity
@Data
@Table(name = "resource_category")
public class ResourceCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "創建時間")
    private Date createTime;

    @Schema(description = "分類名稱")
    private String name;

    @Schema(description = "排序")
    private Integer sort;
}
