package com.huiketong.sumaifang.service;

public interface UserService {
    //用户注册
    boolean register(String user_name, String user_phone, String uuid, Integer user_type, String city_name, String company_info);
}
