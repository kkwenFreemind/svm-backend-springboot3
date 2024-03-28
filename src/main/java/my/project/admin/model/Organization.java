package my.project.admin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author : Kevin Chang
 * @create 2023/10/3 上午10:05
 */
@Entity
@Data
@Table(name = "organization")
public class Organization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "上層組織代碼")
    private Long parent_id;

    @Schema(description = "組織編號")
    private Long name_sn;

    @Schema(description = "組織名稱")
    private String name;

    @Schema(description = "層級")
    private Integer level;

    @Schema(description = "狀態")
    private Integer status;

    @Schema(description = "排序")
    private Integer sort;

    @Schema(description = "創建時間")
    private Date create_time;

    @Override
    public String toString(){

        String pattern = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);

        StringBuilder dataBuilder = new StringBuilder();
//        appendFieldValue(dataBuilder, id.toString());
        appendFieldValue(dataBuilder, simpleDateFormat.format(create_time));
        appendFieldValue(dataBuilder, name);
        appendFieldValue(dataBuilder, parent_id.toString());
        appendFieldValue(dataBuilder, name_sn.toString());
        appendFieldValue(dataBuilder, name);
        appendFieldValue(dataBuilder, level.toString());
        appendFieldValue(dataBuilder, status.toString());

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
