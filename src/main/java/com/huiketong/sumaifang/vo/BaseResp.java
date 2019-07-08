package com.huiketong.sumaifang.vo;

public class BaseResp {
    String code;
    String msg;
    Object data;

    public String getCode() {
        return code;
    }

    public BaseResp setCode(String code) {
        this.code = code;
        return this;
    }

    public String getMsg() {
        return msg;
    }

    public BaseResp setMsg(String msg) {
        this.msg = msg;
        return this;
    }

    public Object getData() {
        return data;
    }

    public BaseResp setData(Object data) {
        this.data = data;
        return this;
    }
}
