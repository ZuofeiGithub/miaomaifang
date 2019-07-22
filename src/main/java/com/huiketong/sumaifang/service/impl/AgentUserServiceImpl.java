package com.huiketong.sumaifang.service.impl;

import com.aliyuncs.exceptions.ClientException;
import com.huiketong.sumaifang.domain.AgentUser;
import com.huiketong.sumaifang.domain.Cities;
import com.huiketong.sumaifang.repository.AgentUserDao;
import com.huiketong.sumaifang.repository.CitiesDao;
import com.huiketong.sumaifang.service.AgentUserService;
import com.huiketong.sumaifang.utils.AlicomDysmsUtil;
import com.huiketong.sumaifang.utils.PhoneFormatCheckUtil;
import com.huiketong.sumaifang.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;

@Service
public class AgentUserServiceImpl implements AgentUserService {

    @Autowired
    AgentUserDao agentUserDao;
    @Autowired
    CitiesDao citiesDao;
    @Override
    public AgentUser findAgentById(Integer id) {
        return agentUserDao.findAgentUserById(id);
    }

    @Override
    public AgentUser findByOpenId(String openid) {
        return agentUserDao.findAgentUserByOpenid(openid);
    }

    @Override
    public String updateToken(String openid) {
        String token = TokenUtil.createJwtToken(openid);
        agentUserDao.updatToken(token, openid);
        return token;
    }

    @Override
    public String saveAgentInfo(String openid, String nickName, Integer gender, String language, String city, String province, String country, String avatarUrl) {
        AgentUser agentUser = new AgentUser();
        String token = TokenUtil.createJwtToken(openid);
        agentUser.setCertification(0);//未认证
        agentUser.setCityName(city == null ? "" : city);
        agentUser.setAvatarUrl(avatarUrl == null ? "" : city);
        agentUser.setCountry(country == null ? "" : country);
        agentUser.setLanguage(language == null ? "" : language);
        agentUser.setProvince(province == null ? "" : province);
        agentUser.setSex(gender == null ? 0 : gender);
        agentUser.setToken(token);
        agentUser.setIsbind(0);
        agentUserDao.save(agentUser);
        return token;
    }

    /**
     * @param city_name
     * @param user_name
     * @param user_telphone
     * @param tel_code
     * @param company
     * @param token
     */
    @Override
    public Integer register(String city_name, String user_name, String user_telphone, String tel_code, String company, String token) {
        AgentUser existUser = agentUserDao.findAgentUserByToken(token);
        Cities cities = citiesDao.findOpenCitys(city_name, 1);
        if(!PhoneFormatCheckUtil.isChinaPhoneLegal(user_telphone)){
            return 205;
        }
        AgentUser agentUser = agentUserDao.findRegisterUser(user_telphone, token);
        if (!ObjectUtils.isEmpty(agentUser)) {
            return 203;
        } else {
            if (!ObjectUtils.isEmpty(existUser)) { //如果存在用户
                if(tel_code.equals(existUser.getPhoneCode())) {
                    Date registerTime = new Date();
                    agentUserDao.updateUserInfo(city_name==null?existUser.getCityName():city_name , user_name == null?existUser.getUserName():user_name, user_telphone ==null?existUser.getUserPhone():user_telphone,
                            company ==null?existUser.getCompanyInfo():company, token, registerTime,1);
                }else{
                    return 204;
                }
            } else {
                return 202;
            }
            if (ObjectUtils.isEmpty(cities)) {
                return 201;
            }
            return 0;
        }
    }

    @Override
    public AgentUser findUserByTokenAndTelphone(String token,String telphone) {
        return agentUserDao.findRegisterUser(telphone,token);
    }

    @Override
    public void bindUser(String token,String phone) {
        agentUserDao.updateBindStatus(1,token,phone);
    }

    @Override
    public AgentUser findbindUserByToken(String token) {
        return agentUserDao.findAgentUserByTokenAndIsbind(token,1);
    }

    @Override
    public void updateUserInfo(String headimg, String wx_account, String telphone, String company, String stores, String introduce, String token) {
        agentUserDao.modifyUserInfo(headimg,wx_account,telphone,company,stores,introduce,token);
    }

    @Override
    public Integer modifyPwd(String telphone, String verify_code, String newpassword) {
        AgentUser agentUser = agentUserDao.findAgentUserByUserPhoneAndIsbind(telphone,1);
       if(!ObjectUtils.isEmpty(agentUser)){
           if(verify_code.equals(agentUser.getPhoneCode())){
               try {
                   agentUserDao.modifyPwd(newpassword,agentUser.getId());
                   return 0;
               }catch (Exception e){
                   return 1;
               }
           }else{
               return 2;
           }
       }else{
            return 3;
       }
    }

    @Override
    public Integer getVerifyCode(String telphone,String token) {
        AgentUser agentUser = agentUserDao.findRegisterUser(telphone,token);
        String code = AlicomDysmsUtil.getCode();
        if(!ObjectUtils.isEmpty(agentUser)){

            try {
                AlicomDysmsUtil.sendSms(telphone,code,"SMS_169898923");
                agentUserDao.updateVerifyCode(code,telphone);
                return 0;
            } catch (Exception e) {
                return 2;
            }
        }else{
            AgentUser agentUser1 = agentUserDao.findAgentUserByToken(token);
            if(!ObjectUtils.isEmpty(agentUser1)){
                agentUserDao.updateVerifyByToken(telphone,code,token);
                return 0;
            }else{
                return 1;//用户不存在
            }
        }
    }

    @Override
    public AgentUser findUserByPhoneAndIsBind(String telphone, Integer isbind) {
        return agentUserDao.findAgentUserByUserPhoneAndIsbind(telphone,isbind);
    }

    @Override
    public AgentUser findUserByToken(String token) {
        return agentUserDao.findAgentUserByToken(token);
    }
}
