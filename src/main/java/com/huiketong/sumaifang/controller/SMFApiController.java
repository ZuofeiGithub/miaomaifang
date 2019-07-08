package com.huiketong.sumaifang.controller;

import com.aliyuncs.exceptions.ClientException;
import com.huiketong.sumaifang.domain.LoginAuth;
import com.huiketong.sumaifang.service.LoginAuthService;
import com.huiketong.sumaifang.utils.AlicomDysmsUtil;
import com.huiketong.sumaifang.utils.TokenUtil;
import com.huiketong.sumaifang.vo.BaseResp;
import jdk.nashorn.internal.parser.Token;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api")
public class SMFApiController {

    @Autowired
    LoginAuthService loginAuthService;
    /**
     * 用户注册
     * @return
     */
    @PostMapping(value = "/register")
    public boolean register(String city_name,String user_name,String user_type,String agree){

        return false;
    }


    @PostMapping(value = "getverifycode")
    public BaseResp getVerifyCode(String telphone){
        BaseResp resp = new BaseResp();
        String code = AlicomDysmsUtil.getCode(); //验证码
        try {
            AlicomDysmsUtil.sendSms(telphone,code,"SMS_169898923");
            loginAuthService.saveUser(telphone,code,TokenUtil.createJwtToken(telphone));
            resp.setCode("0").setMsg("获取验证码成功");
        } catch (ClientException e) {
            e.printStackTrace();
            resp.setCode("300").setMsg("获取验证码失败");
        }
        return resp;
    }

    /**
     *
      * @param telphone
     * @param verifyCode
     * @return
     */
    @PostMapping(value = "/login")
    public BaseResp login(String telphone,String verifyCode){
        BaseResp resp = new BaseResp();
        loginAuthService.login(telphone,verifyCode);
        return resp;
    }
}
