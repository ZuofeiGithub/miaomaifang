package com.huiketong.sumaifang.service;


import com.huiketong.sumaifang.domain.LoginAuth;

public interface LoginAuthService {
    boolean login(String userid,String code,String token);

    boolean saveUser(String telphone,String verifyCode,String token);

    boolean updateUserToken(String telphone);

    boolean save(LoginAuth auth);

    boolean isLogin(String openid);

    boolean isBind(String openid);
}
