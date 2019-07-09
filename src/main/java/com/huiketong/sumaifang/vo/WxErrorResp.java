package com.huiketong.sumaifang.vo;

import lombok.Data;

@Data
public class WxErrorResp extends WxBaseResp{

    /**
     * errcode : 40163
     * errmsg : code been used, hints: [ req_id: weed46aLRa-BVc0TA ]
     */

    private int errcode;
    private String errmsg;

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
