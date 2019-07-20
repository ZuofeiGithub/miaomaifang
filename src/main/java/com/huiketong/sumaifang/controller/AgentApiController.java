package com.huiketong.sumaifang.controller;

import com.google.gson.Gson;
import com.huiketong.sumaifang.data.AgentRegisterData;
import com.huiketong.sumaifang.data.LoginData;
import com.huiketong.sumaifang.domain.AgentUser;
import com.huiketong.sumaifang.service.AgentUserService;
import com.huiketong.sumaifang.service.WXService;
import com.huiketong.sumaifang.vo.BaseResp;
import com.huiketong.sumaifang.vo.WxErrorResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

@RestController
@RequestMapping(value = "/agentapi")
public class AgentApiController {
    @Autowired
    WXService wxService;
    @Autowired
    AgentUserService agentUserService;

    /**
     * 微信登陆
     * 1.先确定用户是否已经存在，如果存在就更新token 如果不存在就保存token
     * 2.如果存在判断有没有绑定手机号码，如果绑定isbind = 1，如果没绑定 isbind = 0
     * @param code
     * @return
     */
    @PostMapping(value = "/wxlogin")
    public BaseResp wxLogin(String code,String nickName, Integer gender, String language, String city, String province, String country, String avatarUrl) {
        BaseResp resp = new BaseResp();
        try {
            WxErrorResp errorResp = new Gson().fromJson(wxService.login(code), WxErrorResp.class);
            if (errorResp.getErrcode() == 0) {
                LoginData data = new LoginData();
               AgentUser agentUserExists  =  agentUserService.findByOpenId(errorResp.getOpenid());
               if(!ObjectUtils.isEmpty(agentUserExists)){
                   String token = agentUserService.updateToken(agentUserExists.getOpenid());

                   data.setToken(token);
                   data.setIsbind(agentUserExists.getIsbind().toString());
                    resp.setCode("1").setMsg("微信登陆成功").setData(data);
               }else{
                   String token =  agentUserService.saveAgentInfo(errorResp.getOpenid(),nickName,gender,language,city,province,country,avatarUrl);
                   data.setToken(token);
                   data.setIsbind("0");
                   resp.setCode("1").setMsg("微信登陆成功").setData(data);
               }
            }else{
                resp.setMsg(errorResp.getErrmsg()).setCode(String.valueOf(errorResp.getErrcode()));
            }
        }catch (ResourceAccessException e){
            resp.setCode("0").setMsg("网络错误");
        }
        return resp;
    }

    /**
     * 注册并登陆
     * @param city_name
     * @param user_name
     * @param user_telphone
     * @param tel_code
     * @param company
     * @param token
     * @return
     */
    @PostMapping(value = "/register")
    public BaseResp register(String city_name,String user_name,String user_telphone,String tel_code,String company,String token){
        BaseResp resp = new BaseResp();
        Integer code = agentUserService.register(city_name,user_name,user_telphone,tel_code,company,token);
        if(code == 201){
            resp.setCode("201").setMsg("当前城市未开通服务");
        }else if(code == 202){
            resp.setMsg("请先微信登陆").setCode("202");
        }else if(code == 203){
            resp.setMsg("用户已经注册过,请直接登陆").setCode("203");
        }else if(code == 204){
            resp.setMsg("验证码错误").setCode("204");
        }else if(code == 205){
            resp.setCode("205").setMsg("请输入正确的手机号");
        }
        else{
            AgentRegisterData data = new AgentRegisterData();
            data.setToken(token);
            resp.setCode("1").setMsg("注册成功").setData(data);
        }

        return resp;
    }
    @PostMapping(value = "/vclogin")
    public BaseResp vcLogin(String token,String telphone,String tel_code){
        BaseResp resp = new BaseResp();
        AgentUser agentUser = agentUserService.findUserByTokenAndTelphone(token,telphone);
        if(!ObjectUtils.isEmpty(agentUser)){
            if(agentUser.getPhoneCode().equals(tel_code)){
                agentUserService.bindUser(token,telphone);
                AgentRegisterData data = new AgentRegisterData();
                data.setToken(token);
                resp.setMsg("登陆成功").setCode("1").setData(data);
            }else{
                resp.setMsg("验证码不正确").setCode("0");
            }

        }else{
           resp.setCode("0").setMsg("用户不存在");
        }
        return resp;
    }
    @PostMapping(value = "/modify_info")
    public BaseResp modifyInfo(String token,String headimg,String wx_account,String telphone,String company,String stores,String introduce){
        BaseResp resp = new BaseResp();
        AgentUser agentUser = agentUserService.findbindUserByToken(token);
        if(!ObjectUtils.isEmpty(agentUser)){
            agentUserService.updateUserInfo(headimg,wx_account,telphone,company,stores,introduce,token);
        }else{
            resp.setMsg("用户未绑定").setCode("0");
        }
        return resp;
    }
    @GetMapping(value = "/house_detail")
    public BaseResp houseDetail(){
        BaseResp resp = new BaseResp();
        return resp;
    }
    @PostMapping(value = "/attention_house")
    public BaseResp attentionHouse(){
        BaseResp resp = new BaseResp();
        return resp;
    }
    @GetMapping(value = "/houselist")
    public BaseResp houseList(){
        BaseResp resp = new BaseResp();
        return resp;
    }
    @PostMapping(value = "/attention_house_list")
    public BaseResp attentionHouseList(){
        BaseResp resp = new BaseResp();
        return resp;
    }

    @PostMapping(value = "/forget_pwd")
    public BaseResp forgetPwd(){
        BaseResp resp = new BaseResp();
        return resp;
    }

    @PostMapping(value = "/pdlogin")
    public BaseResp pdLogin(){
        BaseResp resp = new BaseResp();
        return resp;
    }
}
