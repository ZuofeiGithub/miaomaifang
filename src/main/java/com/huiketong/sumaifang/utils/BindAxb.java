package com.huiketong.sumaifang.utils;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class BindAxb {
    public static void main(String[] args) {
        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAID7owPVleFPC5","ubCTSt5xjZXmUEm77ywAj6s8xFgJG3");
        IAcsClient client = new DefaultAcsClient(profile);

        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime date = LocalDateTime.now();
        System.out.println(dateTimeFormatter.format(date));

        LocalDateTime localDateTime = date.plusMinutes(5);
        System.out.println(dateTimeFormatter.format(localDateTime));
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dyplsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("BindAxb");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNoA", "15962847050");
        request.putQueryParameter("Expiration",dateTimeFormatter.format(localDateTime));
        request.putQueryParameter("PoolKey", "FC100000072744848");
        request.putQueryParameter("PhoneNoB", "18051661999");
        request.putQueryParameter("ExpectCity", "南通市");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
    }
}
