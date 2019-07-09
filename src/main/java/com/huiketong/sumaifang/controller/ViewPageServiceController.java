package com.huiketong.sumaifang.controller;

import com.huiketong.sumaifang.service.SysUserService;
import com.huiketong.sumaifang.utils.RandomValidateCodeUtil;
import com.huiketong.sumaifang.vo.BaseResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping(value = "back")
public class ViewPageServiceController {

    @Autowired
    SysUserService sysUserService;
    @GetMapping(value = "/get_verify_code")
    public void getVerifyCode(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("image/jpeg");
        response.setHeader("Pragma", "No-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expire", 0);
        RandomValidateCodeUtil randomValidateCode = new RandomValidateCodeUtil();
        randomValidateCode.getRandcode(request, response);//输出验证码图片方法
    }

    @PostMapping(value = "login")
    @ResponseBody
    public BaseResp login(HttpSession session,String userName,String password,String code){
        BaseResp resp = new BaseResp();
       String verify_code = (String) session.getAttribute(RandomValidateCodeUtil.RANDOMCODEKEY);
       if(verify_code.toLowerCase().equals(code.toLowerCase())){
           if(sysUserService.login(userName,password)) {
               resp.setCode("0").setMsg("登陆成功").setData(userName);
           }else{
               resp.setCode("1").setMsg("密码错误");
           }
       }else{
            resp.setCode("1").setMsg("验证码不正确");
       }


      return resp;
    }

    @PostMapping(value = "register")
    @ResponseBody
    public BaseResp register(String userName,String password){
        BaseResp resp = new BaseResp();
        boolean success  = sysUserService.registerSysUser(userName,password);
        if(success){
            resp.setCode("0").setMsg("注册成功");
        }else {
            resp.setCode("1").setMsg("注册失败");
        }
        return resp;
    }
}
