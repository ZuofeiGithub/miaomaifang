package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.LoginAuth;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;

public interface LoginAuthDao extends JpaRepository<LoginAuth,Integer> {
    @Override
    <S extends LoginAuth> S save(S s);

    /**
     * 根据电话号码查找用户
     * @param telphone
     * @return
     */
    LoginAuth findLoginAuthByUserTelphone(String telphone);


    /**
     * 更新用户token
     * @param token
     * @param telphone
     */
    @Transactional
    @Query(value = "update login_auth set token = ?1 where user_telphone = ?2",nativeQuery = true)
    void updateToken(String token,String telphone);

    /**
     * 根据openid找到用户
     * @param openId
     * @return
     */
    LoginAuth findLoginAuthByOpenid(String openId);

    /**
     * 根据token找到用户
     * @param token
     * @return
     */
    LoginAuth findLoginAuthByToken(String token);



    @Transactional
    @Modifying
    @Query(value = "update login_auth set user_telphone = ?1 where token = ?2",nativeQuery = true)
    void bindUserTelphone(String telphone,String token);


    /**
     * 更新手机验证码
     */
    @Transactional
    @Modifying
    @Query(value = "update login_auth set user_telphone = ?1,verify_code = ?2 where token = ?3",nativeQuery = true)
    void updateTelphoneVerifyCode(String telphone,String verifyCode,String token);
}
