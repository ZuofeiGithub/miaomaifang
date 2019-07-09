package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.domain.LoginAuth;
import com.huiketong.sumaifang.repository.LoginAuthDao;
import com.huiketong.sumaifang.service.LoginAuthService;
import com.huiketong.sumaifang.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


@Service
public class LoginAuthServiceImpl implements LoginAuthService {

    @Autowired
    LoginAuthDao loginAuthDao;
    @Override
    public boolean login(String userid,String code,String token) {
       LoginAuth loginAuth =  loginAuthDao.findLoginAuthByToken(token);
       if(!ObjectUtils.isEmpty(loginAuth)){
           if(loginAuth.getVerifyCode().equals(code)){
               loginAuthDao.bindUserTelphone(userid,token);
               return true;
//               if(TokenUtil.verifyToken(loginAuth.getToken())){
//
//                   return true;
//               }else{
//                    updateUserToken(userid);
//               }
           }
       }
        return false;
    }

    @Override
    public boolean saveUser(String telphone, String verifyCode, String token) {
        LoginAuth loginAuth = loginAuthDao.findLoginAuthByToken(token);
        if(loginAuth == null){
            loginAuth = new LoginAuth();
            loginAuth.setUserTelphone(telphone);
            loginAuth.setVerifyCode(verifyCode);
            loginAuth.setToken(TokenUtil.createJwtToken(telphone));
            loginAuthDao.save(loginAuth);
            return true;
        }else{
            loginAuthDao.updateTelphoneVerifyCode(telphone,verifyCode,token);
        }
        return false;
    }

    @Override
    public boolean updateUserToken(String telphone) {
        try {
            loginAuthDao.updateToken(TokenUtil.createJwtToken(telphone),telphone);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean save(LoginAuth auth) {
        try {
            loginAuthDao.save(auth);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    /**
     * 是否已经登陆
     * @param openid
     * @return
     */
    @Override
    public boolean isLogin(String openid) {

       LoginAuth auth =  loginAuthDao.findLoginAuthByOpenid(openid);
       if(ObjectUtils.isEmpty(auth)){
           return false;
       }
        return true;
    }

    /**
     * 是否已经绑定手机
     * @param openid
     * @return
     */
    @Override
    public boolean isBind(String openid) {
        LoginAuth auth = loginAuthDao.findLoginAuthByOpenid(openid);
        if(ObjectUtils.isEmpty(auth)){
            return false;
        }
        if(ObjectUtils.isEmpty(auth.getUserTelphone())){
            return false;
        }
        return true;
    }

}
