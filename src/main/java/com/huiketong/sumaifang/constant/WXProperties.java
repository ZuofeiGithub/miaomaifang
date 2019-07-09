package com.huiketong.sumaifang.constant;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class WXProperties implements InitializingBean {
    @Value("${wx.appid}")
    String appid;
    @Value("${wx.secret}")
    String secret;


    public static String APPID;
    public static String SECRET;
    @Override
    public void afterPropertiesSet() throws Exception {
        WXProperties.APPID = appid;
        WXProperties.SECRET = secret;
    }
}
