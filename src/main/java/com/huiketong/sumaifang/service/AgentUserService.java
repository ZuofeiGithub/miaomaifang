package com.huiketong.sumaifang.service;

import com.huiketong.sumaifang.domain.AgentUser;

public interface AgentUserService {
    AgentUser findAgentById(Integer id);

    AgentUser findByOpenId(String openid);

    String updateToken(String openid);

    String saveAgentInfo(String openid, String nickName, Integer gender, String language, String city, String province, String country, String avatarUrl);

    Integer register(String city_name, String user_name, String user_telphone, String tel_code, String company, String token);

    AgentUser findUserByTokenAndTelphone(String token,String telphone);

    void bindUser(String token,String phone);

    AgentUser findbindUserByToken(String token);

    void updateUserInfo(String headimg, String wx_account, String telphone, String company, String stores, String introduce, String token);

    Integer modifyPwd(String telphone, String verify_code, String newpassword);

    Integer getVerifyCode(String telphone,String token);

    AgentUser findUserByPhoneAndIsBind(String telphone,Integer isbind);

    AgentUser findUserByToken(String token);
}
