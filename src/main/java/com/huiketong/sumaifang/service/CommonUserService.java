package com.huiketong.sumaifang.service;


import com.huiketong.sumaifang.domain.CommonUser;

public interface CommonUserService {
    boolean login(String userid,String code,String token);

    boolean saveUser(String telphone,String verifyCode,String token);

    boolean save(CommonUser auth);

    boolean isLogin(String openid);

    boolean isBind(String openid);

    String findVerifyCode(String telphone);

    CommonUser findMine(String token);
}
