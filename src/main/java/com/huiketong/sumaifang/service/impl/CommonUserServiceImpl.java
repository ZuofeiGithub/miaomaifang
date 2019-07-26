package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.domain.CommonUser;
import com.huiketong.sumaifang.repository.CommonUserDao;
import com.huiketong.sumaifang.service.CommonUserService;
import com.huiketong.sumaifang.utils.MD5Util;
import com.huiketong.sumaifang.utils.TokenUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import sun.rmi.runtime.Log;

import java.util.List;


@Service
public class CommonUserServiceImpl implements CommonUserService {

    private Logger logger = LoggerFactory.getLogger(CommonUserServiceImpl.class);
    @Autowired
    CommonUserDao commonUserDao;

    @Override
    public Integer login(String userid, String code, String token, String nickname) {
        CommonUser commonUser = commonUserDao.findCommonUserByTokenAndIsbind(token,false);
        if (!ObjectUtils.isEmpty(commonUser)) {
            //commonUserDao.updateTelphoneVerifyCode(userid,code,token);
            if(commonUser.getVerifyCode() != null) {
                if (commonUser.getVerifyCode().equals(code)) {
                    if (!ObjectUtils.isEmpty(nickname))
                        commonUserDao.updateBindStatusByTelphoneAndToken(userid, token, nickname);
                    else {
                        commonUserDao.updateBindStatusByTelphoneAndToken(userid, token);
                    }
                    return 1;
                } else {
                    return 2;
                }
            }else{
                return 4;
            }
        } else {
            return 3;
        }
    }

    @Override
    public boolean saveUser(String telphone, String verifyCode, String token) {
        CommonUser commonUser = commonUserDao.findCommonUserByTokenAndIsbind(token, false);
        if (commonUser == null) {
            commonUser = new CommonUser();
            commonUser.setUserTelphone(telphone);
            commonUser.setVerifyCode(verifyCode);
            //commonUser.setToken(TokenUtil.createJwtToken(telphone));
            commonUser.setToken(MD5Util.MD5Encode(telphone,"utf8"));
            logger.error("用户不存在重新生成token");
            commonUserDao.save(commonUser);
            return true;
        } else {
            commonUserDao.updateTelphoneVerifyCode(telphone, verifyCode, token);
            return true;
        }
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
     * @param openid
     * @return
     */
    @Override
    public boolean isBind(String openid) {
        CommonUser commonUser = commonUserDao.findUserByOpenId(openid);
        if (!ObjectUtils.isEmpty(commonUser)) {
            CommonUser auth = commonUserDao.findCommonUserByOpenidAndUserTelphone(openid,commonUser.getUserTelphone());
            if (ObjectUtils.isEmpty(auth)) {
                return false;
            } else {
                return auth.isIsbind();
            }
        } else {
            return false;
        }
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
        return commonUserDao.findCommonUserByTokenAndIsbind(token, true);
    }


    @Override
    public boolean unBind(String token) {
        try {
            commonUserDao.updateBindStatusByToken(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public CommonUser findMineById(Integer id) {
        return commonUserDao.findCommonUserById(id);
    }

    @Override
    public boolean modifyNickName(String name, Integer id) {
        try {
            commonUserDao.updateNickName(name, id);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public CommonUser findUserByOpenId(String openid) {
        return commonUserDao.findUserByOpenId(openid);
    }

    @Override
    public CommonUser findUserByOpenIdAndTelphone(String openid, String telphone) {
        return commonUserDao.findCommonUserByOpenidAndUserTelphone(openid,telphone);
    }

    @Override
    public List<CommonUser> findBuyersOnCity(String cityname) {
        return commonUserDao.findCommonUsersOnCity(cityname);
    }
}
