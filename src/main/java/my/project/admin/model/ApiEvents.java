package my.project.admin.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * @Author : Kevin Chang
 * @create 2023/9/12 上午11:03
 */
@Entity
@Data
@Table(name = "api_events")
public class ApiEvents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "帳號Id")
    private Long userId;

    @Schema(description = "IP")
    private String ipAddress;

    @Schema(description = "請求類型")
    private String requestMethod;

    @Schema(description = "事件日期")
    private Date createTime;

    @Schema(description = "帳號")
    private String username;

    @Schema(description = "事件")
    private String event;

    @Schema(description = "結果：0->異常 1:->成功")
    private Integer status;

    @Schema(description = "結果描述")
    private String result;

    @Schema(description = "備註")
    private String memo;

    @Schema(description = "類型")
    private Integer logType;

    @Schema(description = "花費時間：毫秒")
    private Long eventCost;

    public ApiEvents(Long userId , String ipAddress,String requestMethod,String username,String event,Integer status,String result,String memo,Integer logType,Long eventCost ){
        this.userId = userId;
        this.ipAddress = ipAddress;
        this.requestMethod = requestMethod;
        this.username = username;
        this.event = event;
        this.status = status;
        this.result = result;
        this.memo = memo;
        this.logType = logType;
        this.eventCost = eventCost;
        this.createTime = new Date();
    }
}
