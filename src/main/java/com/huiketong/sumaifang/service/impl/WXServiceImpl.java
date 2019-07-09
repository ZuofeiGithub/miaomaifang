package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.constant.WXProperties;
import com.huiketong.sumaifang.constant.WXUrl;
import com.huiketong.sumaifang.service.WXService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class WXServiceImpl implements WXService {
    @Autowired
    private RestTemplate restTemplate;
    @Override
    public String login(String code) {
        String authcode_session_url = WXUrl.AUTHCODE2SESSIONURL.replace("APPID", WXProperties.APPID).replace("SECRET",WXProperties.SECRET).replace("JSCODE",code);

        return restTemplate.getForObject(authcode_session_url, String.class);
    }
}
