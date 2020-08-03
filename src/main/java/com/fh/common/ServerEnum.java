package com.fh.common;

public enum  ServerEnum {
    SUCCESS(200,"成功"),
    ERROR(110,"失败"),
    LOGIN_ERROR(123,"登陆失败"),
    EXIST_ERROR(111,"商品不存在"),
    STATUS_ERROR(112,"商品未上架"),
    ;
    private Integer code;
    private String msg;

    ServerEnum() {
    }

    ServerEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
