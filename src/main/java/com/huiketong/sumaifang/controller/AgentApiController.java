package com.huiketong.sumaifang.controller;

import com.google.gson.Gson;
import com.huiketong.sumaifang.data.AgentRegisterData;
import com.huiketong.sumaifang.data.AttentionHouseData;
import com.huiketong.sumaifang.data.LoginData;
import com.huiketong.sumaifang.domain.AgentUser;
import com.huiketong.sumaifang.domain.Attention;
import com.huiketong.sumaifang.domain.HouseImg;
import com.huiketong.sumaifang.domain.HouseInfo;
import com.huiketong.sumaifang.service.*;
import com.huiketong.sumaifang.vo.BaseResp;
import com.huiketong.sumaifang.vo.WxErrorResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 经纪人端口
 */
@RestController
@RequestMapping(value = "/agentapi")
public class AgentApiController {
    @Autowired
    WXService wxService;
    @Autowired
    AgentUserService agentUserService;
    @Autowired
    AttentionService attentionService;
    @Autowired
    HouseInfoService houseInfoService;
    @Autowired
    HouseImgService houseImgService;

    /**
     * 微信登陆(完成)
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
     * 注册并登陆(完成)
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

    /**
     * 手机验证码登录(完成)
     * @param token
     * @param telphone
     * @param tel_code
     * @return
     */
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

    /**
     * 修改用户信息(完成)
     * @param token
     * @param headimg
     * @param wx_account
     * @param telphone
     * @param company
     * @param stores
     * @param introduce
     * @return
     */
    @PostMapping(value = "/modify_info")
    public BaseResp modifyInfo(String token,String headimg,String wx_account,String telphone,String company,String stores,String introduce){
        BaseResp resp = new BaseResp();
        AgentUser agentUser = agentUserService.findbindUserByToken(token);
        if(!ObjectUtils.isEmpty(agentUser)){
            agentUserService.updateUserInfo(headimg == null||headimg == "" ? agentUser.getAvatarUrl():headimg,wx_account == null || wx_account == "" ? agentUser.getWxaccount():wx_account,
                    telphone == null || telphone == "" ? agentUser.getUserPhone():telphone,company == null || company == ""?agentUser.getCompanyInfo():company,
                    stores == null || stores == "" ? agentUser.getStores():stores,introduce == null || introduce == "" ? agentUser.getIntroduce():introduce, token);
            resp.setCode("1").setMsg("修改信息成功");
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

    /**
     * 关注房源(完成)
     * @param house_id
     * @param token
     * @return
     */
    @PostMapping(value = "/attention_house")
    public BaseResp attentionHouse(Integer house_id,String token){
        BaseResp resp = new BaseResp();
        int result = attentionService.attenHouse(house_id,token);
        switch (result){
            case 0:
                resp.setMsg("关注成功").setCode("1");
                break;
            case 1:
                resp.setMsg("关注失败").setCode("0");
                break;
            case 2:
                resp.setMsg("您已经关注过该房屋").setCode("0");
                break;
            case 3:
                resp.setMsg("该房源无效").setCode("0");
                break;
            case 4:
                resp.setMsg("无效用户").setCode("0");
                break;
            default:
                resp.setMsg("无效code").setCode("0");
                break;
        }
        return resp;
    }
    @GetMapping(value = "/houselist")
    public BaseResp houseList(){
        BaseResp resp = new BaseResp();
        return resp;
    }

    /**
     * 关注房源列表(完成)
     * @return
     */
    @GetMapping(value = "/attention_house_list")
    public BaseResp attentionHouseList(String token){
        BaseResp resp = new BaseResp();
        AgentUser agentUser = agentUserService.findbindUserByToken(token);
        if(!ObjectUtils.isEmpty(agentUser)){
            List<Attention> attentions = attentionService.findAttentionList(token);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(attentions.size() > 0){
                List<AttentionHouseData> dataList = new ArrayList<>();
                for(Attention attention:attentions) {
                    AttentionHouseData data = new AttentionHouseData();
                    HouseInfo houseInfo = houseInfoService.findMyHouseById(attention.getHouseId());
                    if(!ObjectUtils.isEmpty(houseInfo)){
                        data.setArea(houseInfo.getHouseArea().toString());
                        data.setAtten_time(dateFormat.format(attention.getAttenTime()));
                        data.setFloor(houseInfo.getHouseTier());
                        data.setIsselling(houseInfo.isSaleStop() == true?"1":"0");
                        data.setTitle(houseInfo.getHouseTitle());
                        List<HouseImg> houseImgList = houseImgService.findHouseImg(houseInfo.getId());
                        if(houseImgList.size() > 0){
                            data.setImg(houseImgList.get(0).getImgurl() == null ? "":houseImgList.get(0).getImgurl());
                        }
                    }
                    dataList.add(data);
                }
                resp.setCode("1").setData(dataList).setMsg("获取成功");
            }else{
                resp.setCode("1").setData(new ArrayList<>()).setMsg("获取成功");
            }
        }else{
            resp.setCode("0").setMsg("用户未登陆");
        }
        return resp;
    }

    /**
     * 忘记密码修改密码(完成)
     * @param telphone
     * @param verify_code
     * @param newpassword
     * @return
     */
    @PostMapping(value = "/forget_pwd")
    public BaseResp forgetPwd(String telphone,String verify_code,String newpassword){
        BaseResp resp = new BaseResp();
        Integer result = agentUserService.modifyPwd(telphone,verify_code,newpassword);
        switch (result){
            case 0:
                resp.setMsg("修改密码成功").setCode("1");
                break;
            case 1:
                resp.setMsg("修改失败").setCode("0");
                break;
            case 2:
                resp.setMsg("验证码不正确").setCode("0");
                break;
            case 3:
                resp.setCode("0").setMsg("用户不存在");
                break;
        }
        return resp;
    }

    /**
     * 获取手机验证码(完成)
     * @param telphone
     * @return
     */
    @PostMapping(value = "get_verifycode")
    public BaseResp getVerifyCode(String telphone){
        BaseResp resp = new BaseResp();
        Integer result = agentUserService.getVerifyCode(telphone);
        switch (result){
            case 0:
                resp.setCode("1").setMsg("获取验证码成功");
                break;
            case 1:
                resp.setCode("0").setMsg("该号码不存在");
                break;
            case 2:
                resp.setCode("0").setMsg("获取验证码失败");
                break;
        }
        return resp;
    }

    @PostMapping(value = "/pdlogin")
    public BaseResp pdLogin(){
        BaseResp resp = new BaseResp();
        return resp;
    }

    @GetMapping(value = "/citylist")
    public BaseResp cityList(){
        BaseResp resp = new BaseResp();
        return resp;
    }

    @PostMapping(value = "/get_postion_city")
    public BaseResp getPostionCity(){
        BaseResp resp = new BaseResp();
        return resp;
    }

    @PostMapping(value = "/send_msg")
    public BaseResp sendMsg(){
        BaseResp resp = new BaseResp();
        return resp;
    }

    @GetMapping(value = "/userinfo")
    public BaseResp userInfo(String token){
        BaseResp resp = new BaseResp();
        return resp;
    }

    @GetMapping(value = "/msg_detail")
    public BaseResp msgDetail(){
        BaseResp resp = new BaseResp();
        return resp;
    }

    @GetMapping(value = "/msglist")
    public BaseResp msgList(String token){
        BaseResp resp = new BaseResp();
        return resp;
    }
}
