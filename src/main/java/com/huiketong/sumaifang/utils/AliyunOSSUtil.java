package com.huiketong.sumaifang.utils;

import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.CannedAccessControlList;
import com.aliyun.oss.model.CreateBucketRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyun.oss.model.PutObjectResult;
import com.huiketong.sumaifang.constant.OSSProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

public class AliyunOSSUtil {
    private static final Logger logger = LoggerFactory.getLogger(AliyunOSSUtil.class);
    private static String endpoint = OSSProperties.OSS_END_POINT;
    private static String accessKeyId = OSSProperties.OSS_ACCESS_KEY_ID;
    private static String accessKeySecret = OSSProperties.OSS_ACCESS_KEY_SECRET;
    private static String bucketName = OSSProperties.OSS_BUCKET_NAME;
    private static String fileHost = OSSProperties.OSS_FILE_HOST;
    private static OSSClient ossClient;
    public static String upload(File file) {
        logger.info("=====>OSS文件上传开始: " + file.getName());


        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String dateStr = format.format(new Date());
        if (null == file) {
            return null;
        }
        ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);

        try {
            if (!ossClient.doesBucketExist(bucketName)) {
                ossClient.createBucket(bucketName);
                CreateBucketRequest createBucketRequest = new CreateBucketRequest(bucketName);
                createBucketRequest.setCannedACL(CannedAccessControlList.PublicRead);
                ossClient.createBucket(createBucketRequest);
            }

            //创建文件路径
            //String fileUrl = fileHost + "/" + (dateStr + "/" + UUID.randomUUID().toString().replace("-", "") + "-" + file.getName());
            String fileUrl = fileHost + "/" + file.getName();
            //上传文件
            PutObjectResult request = ossClient.putObject(new PutObjectRequest(bucketName, fileUrl, file));
            //设置权限 这里是公开读
            ossClient.setBucketAcl(bucketName, CannedAccessControlList.PublicRead);
            String url = getUrl(fileUrl);
            if (null != request) {
                logger.info("========>OSS文件上传成功,OSS地址: " + url);
            }
            return url;
        } catch (OSSException os) {

        } catch (ClientException ce) {

        } finally {
            ossClient.shutdown();
        }
        return null;
    }


    /**
     * 获得url链接
     *
     * @param key
     * @return
     */
    private  static String getUrl(String key) {
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
        // 生成URL
        URL url = ossClient.generatePresignedUrl(bucketName, key, expiration);
        if (url != null) {
            return url.toString();
        }
        return null;
    }

}
