package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.domain.CommonUser;
import com.huiketong.sumaifang.repository.CommonUserDao;
import com.huiketong.sumaifang.service.CommonUserService;
import com.huiketong.sumaifang.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;


@Service
public class CommonUserServiceImpl implements CommonUserService {

    @Autowired
    CommonUserDao commonUserDao;
    @Override
    public boolean login(String userid,String code,String token) {
       CommonUser commonUser =  commonUserDao.findCommonUserByToken(token);
       if(!ObjectUtils.isEmpty(commonUser)){
           if(commonUser.getVerifyCode().equals(code)){
               commonUserDao.bindUserTelphone(userid,token);
               return true;
//               if(TokenUtil.verifyToken(commonUser.getToken())){
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
        CommonUser commonUser = commonUserDao.findCommonUserByToken(token);
        if(commonUser == null){
            commonUser = new CommonUser();
            commonUser.setUserTelphone(telphone);
            commonUser.setVerifyCode(verifyCode);
            commonUser.setToken(TokenUtil.createJwtToken(telphone));
            commonUserDao.save(commonUser);
            return true;
        }else{
            commonUserDao.updateTelphoneVerifyCode(telphone,verifyCode,token);
        }
        return false;
    }


    @Override
    public boolean save(CommonUser auth) {
        try {
            commonUserDao.save(auth);
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

       CommonUser auth =  commonUserDao.findCommonUserByOpenid(openid);
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
        CommonUser auth = commonUserDao.findCommonUserByOpenid(openid);
        if(ObjectUtils.isEmpty(auth)){
            return false;
        }
        if(ObjectUtils.isEmpty(auth.getUserTelphone())){
            return false;
        }
        return true;
    }

}
