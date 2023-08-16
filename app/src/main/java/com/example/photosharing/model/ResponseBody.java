package com.example.photosharing.model;

public class ResponseBody<T> {
    private Integer code;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    private String msg;
    private T data;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}