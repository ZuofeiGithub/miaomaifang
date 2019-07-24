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
    @Value("${wx.agent_appid}")
    String agentAppId;
    @Value("${wx.agent_secret}")
    String agentSecret;


    public static String APPID;
    public static String SECRET;
    public static String AGENTAPPID;
    public static String AGENTSECRET;
    @Override
    public void afterPropertiesSet() throws Exception {
        WXProperties.APPID = appid;
        WXProperties.SECRET = secret;
        WXProperties.AGENTAPPID = agentAppId;
        WXProperties.AGENTSECRET = agentSecret;
    }
}
