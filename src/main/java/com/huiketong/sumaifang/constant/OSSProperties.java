package com.huiketong.sumaifang.constant;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class OSSProperties implements InitializingBean {
    @Value("${oss.file.endpoint}")
    private String endpoint;
    @Value("${oss.file.keyid}")
    private String keyid;
    @Value("${oss.file.keysecret}")
    private String keysecret;
    @Value("${oss.file.filehost}")
    private String filehost;
    @Value("${oss.file.bucketname}")
    private String bucketname;

    public static String OSS_END_POINT;
    public static String OSS_ACCESS_KEY_ID;
    public static String OSS_ACCESS_KEY_SECRET;
    public static String OSS_BUCKET_NAME;
    public static String OSS_FILE_HOST;
    @Override
    public void afterPropertiesSet() throws Exception {
        OSS_END_POINT = endpoint;
        OSS_ACCESS_KEY_ID = keyid;
        OSS_ACCESS_KEY_SECRET = keysecret;
        OSS_BUCKET_NAME = bucketname;
        OSS_FILE_HOST = filehost;
    }
}
