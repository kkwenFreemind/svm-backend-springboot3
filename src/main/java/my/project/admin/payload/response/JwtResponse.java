package my.project.admin.payload.response;

import java.util.List;

/**
 * @Author : Kevin Chang
 * @create 2023/9/6 下午4:19
 */
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String username;
    private String email;
    private List<String> roles;

    /**
     * {
     *     "code": 200,
     *     "message": "成功",
     *     "data": {
     *         "tokenHead": "Bearer",
     *         "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJhZG1pbkBhcHRnLmNvbS50dyIsImNyZWF0ZWQiOjE2OTQzOTU0NTEzMzMsImV4cCI6MTY5NDQyNDI1MX0.eumwgIcyTgBdhzLESRax6QiiEi1SYQfsXiMi6TfdUAZ9j27JnQs2gqiOJVTGXYgkcnR1OKcEIHv7ecbMK69laA"
     *     }
     * }
     * @param accessToken
     * @param id
     * @param username
     * @param email
     * @param roles
     */
    public JwtResponse(String accessToken, Long id, String username, String email, List<String> roles) {
        this.token = accessToken;
        this.id = id;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }

    public String getAccessToken() {
        return token;
    }

    public void setAccessToken(String accessToken) {
        this.token = accessToken;
    }

    public String getTokenType() {
        return type;
    }

    public void setTokenType(String tokenType) {
        this.type = tokenType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<String> getRoles() {
        return roles;
    }


}
