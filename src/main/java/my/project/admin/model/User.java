package my.project.admin.model;

import com.baomidou.mybatisplus.annotation.TableField;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @Author : Kevin Chang
 * @create 2023/9/6 下午1:56
 */
@Entity
@Data
@Table(name = "users",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "username"),
                @UniqueConstraint(columnNames = "email")
        })
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 20)
    private String username;

    @NotBlank
    @Size(max = 30)
    @Email
    private String email;

    @NotBlank
    @Size(max = 120)
    private String password;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(  name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles = new HashSet<>();

    @Size(max = 10)
    @Schema(description = "手機號碼")
    private String mobile;

    @Size(max = 20)
    @Schema(description = "暱稱")
    private String nickName;

    @Size(max = 200)
    @Schema(description = "備註")
    private String note;

    @Schema(description = "創建時間")
    private Date createTime;

    @Schema(description = "異動時間")
    private Date updateTime;

    @Schema(description = "最新登入時間")
    private Date loginTime;

    @Schema(description = "登出時間")
    private Date logoutTime;

    @Schema(description = "帳號狀態：0->禁用；1->啟用")
    private Integer status;

    @Schema(description = "組織代碼")
    private Long orgId;

//    @TableField(exist = false)
    @Schema(description = "組織編碼")
    private Long orgSn;

//    @TableField(exist = false)
    @Schema(description = "組織名稱")
    private String orgName;

    @Schema(description = "創建者ID")
    private Long createBy;

    @Schema(description = "創建者")
    private String createName;

    @Schema(description = "異動者ID")
    private Long updateBy;

    @Schema(description = "異動者")
    private String updateName;

    public User() {
    }

    public User(String username, String email, String password,Date createTime,String mobile ,
                Integer status,String nickName, String note,Long orgId,
                String orgName,Long orgNameSn, Long createBy,String createName) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.createTime = createTime;
        this.mobile = mobile;
        this.status = status;
        this.nickName = nickName;
        this.note = note;
        this.orgId = orgId;
        this.orgName = orgName;
        this.orgSn = orgNameSn;
        this.createBy = createBy;
        this.createName = createName;

    }


}
