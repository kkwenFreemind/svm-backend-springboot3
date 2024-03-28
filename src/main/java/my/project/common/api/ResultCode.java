package my.project.common.api;

/**
 * @Author : Kevin Chang
 * @create 2023/9/11 上午9:29
 */
public enum ResultCode implements IErrorCode{

    SUCCESS(200, "成功"),
    FAILED(500, "失敗"),
    UNAUTHORIZED(401, "尚未登入或令牌token已過期"),
    PASSWORD_FAILED(402, "密碼強度不足"),
    FORBIDDEN(403, "無此權限"),
    VALIDATE_FAILED(404, "參數有誤");
    private long code;
    private String message;

    private ResultCode(long code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public long getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
