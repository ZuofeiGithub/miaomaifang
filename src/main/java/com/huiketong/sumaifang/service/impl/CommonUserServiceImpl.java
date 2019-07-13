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
    public boolean login(String userid, String code, String token) {
        CommonUser commonUser = commonUserDao.findCommonUserByUserTelphone(userid);
        if(ObjectUtils.isEmpty(commonUser)){
            commonUserDao.updateTelphoneVerifyCode(userid,code,token);
            commonUserDao.updateBindStatusByTelphoneAndToken(userid,token);
        }else{

        }
        return false;
    }

    @Override
    public boolean saveUser(String telphone, String verifyCode, String token) {
        CommonUser commonUser = commonUserDao.findCommonUserByTokenAndIsbind(token,false);
        if (commonUser == null) {
            commonUser = new CommonUser();
            commonUser.setUserTelphone(telphone);
            commonUser.setVerifyCode(verifyCode);
            commonUser.setToken(TokenUtil.createJwtToken(telphone));
            commonUserDao.save(commonUser);
            return true;
        } else {
            commonUserDao.updateTelphoneVerifyCode(telphone, verifyCode, token);
        }
        return false;
    }


    @Override
    public boolean save(CommonUser auth) {
        try {
            commonUserDao.save(auth);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 是否已经登陆
     *
     * @param openid
     * @return
     */
    @Override
    public boolean isLogin(String openid) {

        CommonUser auth = commonUserDao.findUserByOpenId(openid);
        if (ObjectUtils.isEmpty(auth)) {
            return false;
        }
        return true;
    }

    /**
     * 是否已经绑定手机
     *
     * @param telphone
     * @return
     */
    @Override
    public boolean isBind(String telphone) {
        CommonUser auth = commonUserDao.findCommonUserByUserTelphone(telphone);
        return auth.isIsbind();
    }

    @Override
    public String findVerifyCode(String telphone) {
        CommonUser user = commonUserDao.findCommonUserByUserTelphone(telphone);
        if (!ObjectUtils.isEmpty(user)) {
            return user.getVerifyCode();
        }
        return null;
    }

    @Override
    public CommonUser findMine(String token) {
        return commonUserDao.findCommonUserByTokenAndIsbind(token,true);
    }

    @Override
    public boolean unBind(String token) {
        try {
            commonUserDao.updateBindStatusByToken(token);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public CommonUser findMineById(Integer id) {
        return commonUserDao.findCommonUserById(id);
    }
}
