package com.huiketong.sumaifang.controller;

import com.aliyuncs.exceptions.ClientException;
import com.fasterxml.jackson.databind.ser.Serializers;
import com.google.gson.Gson;
import com.huiketong.sumaifang.data.HomeData;
import com.huiketong.sumaifang.data.LoginData;
import com.huiketong.sumaifang.domain.LoginAuth;
import com.huiketong.sumaifang.service.HouseInfoService;
import com.huiketong.sumaifang.service.LoginAuthService;
import com.huiketong.sumaifang.service.WXService;
import com.huiketong.sumaifang.utils.AlicomDysmsUtil;
import com.huiketong.sumaifang.utils.TokenUtil;
import com.huiketong.sumaifang.vo.BaseResp;
import com.huiketong.sumaifang.vo.WxErrorResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api")
public class SMFApiController {

    @Autowired
    LoginAuthService loginAuthService;
    @Autowired
    WXService wxService;
    @Autowired
    HouseInfoService houseInfoService;


    @GetMapping(value = "/homeinfo")
    public BaseResp homeInfo(String token){
        BaseResp resp = new BaseResp();
        HomeData data = new HomeData();



        resp.setCode("1").setMsg("获取首页信息成功").setData(data);
        return resp;
    }

    @PostMapping(value = "/upload_house_info")
    @ResponseBody
    public BaseResp uploadHouseInfo(String little_district,double house_area,Double expect_price,String token){
        BaseResp resp = new BaseResp();
        if(houseInfoService.uploadHouseInfo(little_district,house_area,expect_price,token)){
            resp.setCode("1").setMsg("发布成功");
        }else{
            resp.setCode("0").setMsg("发布失败");
        }
        return resp;
    }

    @PostMapping(value = "getverifycode")
    public BaseResp getVerifyCode(String telphone,String token) {
        BaseResp resp = new BaseResp();
        String code = AlicomDysmsUtil.getCode(); //验证码
        try {
            AlicomDysmsUtil.sendSms(telphone, code, "SMS_169898923");
            loginAuthService.saveUser(telphone, code, token);
            resp.setCode("0").setMsg("获取验证码成功");
        } catch (ClientException e) {
            e.printStackTrace();
            resp.setCode("300").setMsg("获取验证码失败");
        }
        return resp;
    }

    /**
     * @param telphone
     * @param verifyCode
     * @return
     */
    @PostMapping(value = "/login")
    public BaseResp login(String telphone, String verifyCode, String token) {
        BaseResp resp = new BaseResp();
        try{
            loginAuthService.login(telphone, verifyCode, token);
            resp.setCode("1").setMsg("登陆成功");
        }catch (Exception e){
            resp.setMsg("登陆失败").setCode("2");
        }
        return resp;
    }


    /**
     * 微信登陆
     *
     * @param code
     * @param nickName
     * @param gender
     * @param language
     * @param city
     * @param province
     * @param country
     * @param avatarUrl
     * @return
     */
    @PostMapping(value = "/wxlogin")
    public BaseResp wxlogin(String code, String nickName, Integer gender, String language, String city, String province, String country, String avatarUrl) {
        BaseResp resp = new BaseResp();
        LoginData data = new LoginData();
        WxErrorResp errorResp = new Gson().fromJson(wxService.login(code), WxErrorResp.class);
        if (!ObjectUtils.isEmpty(errorResp)) {
            if (errorResp.getErrcode() == 0) {
                boolean islogin = loginAuthService.isLogin(errorResp.getOpenid());
                boolean isbind = loginAuthService.isBind(errorResp.getOpenid());
                String token = TokenUtil.createJwtToken(errorResp.getOpenid());
                if (isbind) {
                    data.setIsbind("1");
                } else {
                    data.setIsbind("0");
                }
                if (islogin) {
                    data.setIslogin("1");
                    data.setToken(token);
                    resp.setMsg("登陆成功").setCode("1").setData(data);
                } else {

                    LoginAuth auth = new LoginAuth();
                    auth.setOpenid(errorResp.getOpenid());
                    auth.setToken(token);
                    auth.setNickName(nickName == null ? "" : nickName);
                    auth.setGender(String.valueOf(gender));
                    auth.setLanguage(language == null ? "" : language);
                    auth.setCity(city == null ? "" : city);
                    auth.setProvince(province == null ? "" : province);
                    auth.setCountry(country == null ? "" : country);
                    auth.setAvatarUrl(avatarUrl == null ? "" : avatarUrl);
                    loginAuthService.save(auth);
                    data.setIslogin(String.valueOf(1));
                    resp.setMsg("授权成功").setCode("1").setData(data);
                }
            } else {
                resp.setMsg(errorResp.getErrmsg()).setCode(String.valueOf(errorResp.getErrcode())).setData(data);
            }
        }
        return resp;
    }
}
