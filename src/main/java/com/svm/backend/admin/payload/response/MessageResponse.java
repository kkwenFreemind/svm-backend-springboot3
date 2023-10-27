package com.svm.backend.admin.payload.response;

/**
 * @Author : Kevin Chang
 * @create 2023/9/6 下午4:20
 */
public class MessageResponse {
    private String message;

    public MessageResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
