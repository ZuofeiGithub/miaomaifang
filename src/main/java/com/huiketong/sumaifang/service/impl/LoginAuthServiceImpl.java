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
    public boolean login(String userid,String code) {
       LoginAuth loginAuth =  loginAuthDao.findLoginAuthByUserTelphone(userid);
       if(!ObjectUtils.isEmpty(loginAuth)){
           if(loginAuth.getVerifyCode().equals(code)){
               if(TokenUtil.verifyToken(loginAuth.getToken())){
                   return true;
               }else{
                    updateUserToken(userid);
               }
           }
       }
        return false;
    }

    @Override
    public boolean saveUser(String telphone, String verifyCode, String token) {
        LoginAuth loginAuth = loginAuthDao.findLoginAuthByUserTelphone(telphone);
        if(loginAuth == null){
            loginAuth = new LoginAuth();
            loginAuth.setUserTelphone(telphone);
            loginAuth.setVerifyCode(verifyCode);
            loginAuth.setToken(TokenUtil.createJwtToken(telphone));
            loginAuthDao.save(loginAuth);
            return true;
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
}
