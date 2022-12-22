package dev.silvia.wechattrade.dto.response;

import lombok.Data;

@Data
public class Result<T> {
    private String code; //登陆状态标识
    private String msg;  //登陆状态描述

    //结果数据
    private T data;

    public Result(ResultCode resultCode) {
        this.code = resultCode.code();
        this.msg = resultCode.msg();
    }
    public Result(String code,String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }
    public Result(ResultCode resultCode, T data) {
        this.code = resultCode.code();
        this.msg = resultCode.msg();
        this.data = data;
    }

    public Result(String msg) {
        this.msg = msg;
    }

    public static Result SUCCESS() {
        return new Result(ResultCode.SUCCESS);
    }

    public static <T> Result SUCCESS(T data) {
        return new Result(ResultCode.SUCCESS, data);
    }

    public static Result FAIL() {
        return new Result(ResultCode.FAIL);
    }

    public static Result FAIL(String msg) {
        return new Result(msg);
    }
}
