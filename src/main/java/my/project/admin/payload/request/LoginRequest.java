package my.project.admin.payload.request;

import jakarta.validation.constraints.NotBlank;

/**
 * @Author : Kevin Chang
 * @create 2023/9/6 下午4:19
 */
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
