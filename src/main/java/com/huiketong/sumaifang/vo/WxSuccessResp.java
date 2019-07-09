package com.huiketong.sumaifang.vo;

import lombok.Data;

@Data
public class WxSuccessResp extends WxBaseResp {
    String openid;
    String session_key;
}
