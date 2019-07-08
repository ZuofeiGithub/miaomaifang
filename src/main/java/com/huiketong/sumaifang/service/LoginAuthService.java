package com.huiketong.sumaifang.service;


public interface LoginAuthService {
    boolean login(String userid,String code);

    boolean saveUser(String telphone,String verifyCode,String token);

    boolean updateUserToken(String telphone);
}
