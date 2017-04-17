package com.gangsterhyj.response;

/**
 * Created by gangsterhyj on 17-3-22.
 */
public class FormatResponse {
    private String code;
    private String message;
    private Object data;
    public FormatResponse() {
        this.setCode(ResultCode.SUCCESS.getCode());
        this.setMessage(ResultCode.SUCCESS.getMessage());
    }
    public FormatResponse(Object data) {
        this.setCode(ResultCode.SUCCESS.getCode());
        this.setMessage(ResultCode.SUCCESS.getMessage());
        this.setData(data);
    }
    public FormatResponse(ResultCode resultCode, Object data) {
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
        this.setData(data);
    }

    public Object getData() {
        return data;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
