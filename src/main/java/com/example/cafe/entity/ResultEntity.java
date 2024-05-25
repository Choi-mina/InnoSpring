package com.example.cafe.entity;

public class ResultEntity<T> {

    private String code = ApiResult.SUCCESSS.getCode();
    private String message = ApiResult.SUCCESSS.getMessage();
    private T data;


    public ResultEntity(String code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public ResultEntity(T data) {
        this.data = data;
    }

    public ResultEntity(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResultEntity() {

    }

    public String getCode() {
        return code;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
