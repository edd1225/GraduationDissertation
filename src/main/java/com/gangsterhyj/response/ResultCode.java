package com.gangsterhyj.response;

/**
 * Created by gangsterhyj on 17-3-22.
 */
public enum ResultCode {
    SUCCESS("200", "SUCCESS"),
    NOT_LOGIN("400", "NOT_LOGIN"),
    REGISTERED("407", "REGISTERED"),
    USER_NOT_EXIST("408", "USER_NOT_EXIST"),
    PASSWORD_ERROR("409", "PASSWORD_ERROR"),
    EXCEPTION("401", "EXCEPTION"),
    SYSTEN_ERROR("402", "SYSTEM_ERROR"),
    PARAMS_ERROR("403", "PARAMS_ERROR"),
    NOT_SUPPORTED("410", "NOT_SUPPORTED"),
    INVALID_AUTHCODE("444", "INVALID_AUTHCODE"),
    TOO_FREQUENT("445", "TOO_FREQUENT"),
    UNKNOWN_ERROR("499", "UNKNOWN_ERROR");
    private ResultCode(String code, String msg) {
        this.code = code;
        this.message = msg;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    private String code;
    private String message;

}
