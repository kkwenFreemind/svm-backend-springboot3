package my.project.common.exception;


import my.project.common.api.IErrorCode;

/**
 * @Author : Kevin Chang
 * @create 2023/9/11 上午9:27
 */
public class Asserts {
    public static void fail(String message) {
        throw new ApiException(message);
    }

    public static void fail(IErrorCode errorCode) {
        throw new ApiException(errorCode);
    }
}
