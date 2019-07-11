package com.huiketong.sumaifang.constant;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class TencentProperties implements InitializingBean {
    @Value("${tencent.key}")
    private String key;

    public static String KEY;

    @Override
    public void afterPropertiesSet() throws Exception {
        TencentProperties.KEY = this.key;
    }
}
