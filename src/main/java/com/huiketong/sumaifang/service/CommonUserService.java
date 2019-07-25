package com.huiketong.sumaifang.service;


import com.huiketong.sumaifang.domain.CommonUser;

import java.util.List;

public interface CommonUserService {
    Integer login(String userid, String code, String token, String nickname);

    boolean saveUser(String telphone,String verifyCode,String token);

    boolean save(CommonUser auth);

    boolean isLogin(String openid);

    boolean isBind(String openid);

    String findVerifyCode(String telphone);

    CommonUser findMine(String token);

    boolean unBind(String token);

    CommonUser findMineById(Integer id);

    boolean modifyNickName(String name,Integer id);

    CommonUser findUserByOpenId(String openid);

    CommonUser findUserByOpenIdAndTelphone(String openid,String telphone);

    List<CommonUser> findBuyersOnCity(String cityname);
}
