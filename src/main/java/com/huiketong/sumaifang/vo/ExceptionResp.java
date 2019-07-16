package com.huiketong.sumaifang.vo;

public class ExceptionResp {
    Integer code;
    String msg;

    public Integer getCode() {
        return code;
    }

    public ExceptionResp setCode(Integer code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public ExceptionResp setMsg(String msg) {
        this.msg = msg;
        return this;
    }
}
