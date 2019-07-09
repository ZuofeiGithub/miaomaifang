package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.domain.SysUser;
import com.huiketong.sumaifang.repository.SysUserDao;
import com.huiketong.sumaifang.service.SysUserService;
import com.huiketong.sumaifang.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.Date;

@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    SysUserDao sysUserDao;
    @Override
    public boolean registerSysUser(String userName, String password) {
        try {
            SysUser user = new SysUser();
            user.setUserId(userName);
            user.setPassword(MD5Util.getEncryptedPwd(password));
            user.setRegisterTime(new Date());
            user.setUserType(1);
            sysUserDao.save(user);
            return true;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean login(String username, String password) {

        SysUser user = sysUserDao.findSysUserByUserId(username);
        if(!ObjectUtils.isEmpty(user)){
            try {
                if(MD5Util.validPassword(password,user.getPassword())){
                        return true;
                }
            } catch (NoSuchAlgorithmException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
}
