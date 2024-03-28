package my.project.admin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author : Kevin Chang
 * @create 2023/9/11 上午10:51
 */
@Entity
@Data
@Table(name = "resources")
public class Resources {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "創建時間")
    private Date createTime;

    @Schema(description = "資源名稱")
    private String name;

    @Schema(description = "資源URL")
    private String url;

    @Schema(description = "描述")
    private String description;

    @Schema(description = "資源分類ID")
    private Long categoryId;

    @Override
    public String toString(){

        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        StringBuilder dataBuilder = new StringBuilder();
        appendFieldValue(dataBuilder, id.toString());
        appendFieldValue(dataBuilder, simpleDateFormat.format(createTime));
        appendFieldValue(dataBuilder, name);
        appendFieldValue(dataBuilder, url);
        appendFieldValue(dataBuilder, description);
        appendFieldValue(dataBuilder, categoryId.toString());

        return dataBuilder.toString();
    }

    private void appendFieldValue(StringBuilder dataBuilder, String fieldValue) {
        if(fieldValue != null) {
            dataBuilder.append(fieldValue).append(",");
        } else {
            dataBuilder.append("").append(",");
        }
    }
}
