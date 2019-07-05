package com.huiketong.miaomaifang.service.impl;

import com.huiketong.miaomaifang.domain.User;
import com.huiketong.miaomaifang.repository.UserDao;
import com.huiketong.miaomaifang.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Resource
    UserDao userDao;
    private User newUser;

    /**
     * 注册成功返回true,注册失败返回false
     * @param user_name
     * @param user_phone
     * @param uuid
     * @param user_type
     * @param city_name
     * @param company_info
     * @return
     */
    @Override
    public boolean register(String user_name, String user_phone, String uuid, Integer user_type, String city_name, String company_info) {
        User olduser = userDao.findUserByUuid(uuid);
        if (!ObjectUtils.isEmpty(olduser)) {
            return false;
        }
        initUserData(user_name, user_phone, uuid, user_type, city_name, company_info);
        try {
            userDao.save(newUser);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 初始化新用户
     * @param user_name
     * @param user_phone
     * @param uuid
     * @param user_type
     * @param city_name
     * @param company_info
     */
    private void initUserData(String user_name, String user_phone, String uuid, Integer user_type, String city_name, String company_info) {
        if(newUser  == null) {
            newUser = new User();
        }
        newUser.setUserName(user_name);
        newUser.setUserPhone(user_phone);
        newUser.setUuid(uuid);
        newUser.setUserType(user_type);
        newUser.setCityName(city_name);
        newUser.setRegisterTime(new Date());
        newUser.setCompanyInfo(company_info);
        newUser.setCertification(0);
    }
}
