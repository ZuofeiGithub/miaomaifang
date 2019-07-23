package com.huiketong.sumaifang.controller;

import com.google.gson.Gson;
import com.huiketong.sumaifang.constant.TencentProperties;
import com.huiketong.sumaifang.constant.TencentUrl;
import com.huiketong.sumaifang.data.*;
import com.huiketong.sumaifang.domain.AgentUser;
import com.huiketong.sumaifang.domain.Attention;
import com.huiketong.sumaifang.domain.HouseImg;
import com.huiketong.sumaifang.domain.HouseInfo;
import com.huiketong.sumaifang.service.*;
import com.huiketong.sumaifang.utils.DateUtil;
import com.huiketong.sumaifang.utils.MD5Util;
import com.huiketong.sumaifang.utils.PinyinUtil;
import com.huiketong.sumaifang.vo.BaseResp;
import com.huiketong.sumaifang.vo.GeoCoderResp;
import com.huiketong.sumaifang.vo.LocationResp;
import com.huiketong.sumaifang.vo.WxErrorResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 经纪人端口
 */
@RestController
@RequestMapping(value = "/agent_api")
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
    @Autowired
    CitiesService citiesService;

    /**
     * 微信登陆(完成)
     * 1.先确定用户是否已经存在，如果存在就更新token 如果不存在就保存token
     * 2.如果存在判断有没有绑定手机号码，如果绑定isbind = 1，如果没绑定 isbind = 0
     *
     * @param code
     * @return
     */
    @PostMapping(value = "/wxlogin")
    public BaseResp wxLogin(String code,String token, String nickName, Integer gender, String language, String city, String province, String country, String avatarUrl) {


        BaseResp resp = new BaseResp();
        AgentUser agentUser = agentUserService.findUserByToken(token);
        if(!ObjectUtils.isEmpty(agentUser)){
            LoginData data  = new LoginData();
            data.setIsbind(agentUser.getIsbind() == 1?"1":"0");
            data.setToken(agentUser.getToken());
            resp.setCode("1").setMsg("用户已经存在").setData(data);
        }else{
            try {
                WxErrorResp errorResp = new Gson().fromJson(wxService.login(code), WxErrorResp.class);
                if (errorResp.getErrcode() == 0) {
                    LoginData data = new LoginData();
                    AgentUser agentUserExists = agentUserService.findByOpenId(errorResp.getOpenid());
                    if (!ObjectUtils.isEmpty(agentUserExists)) {
                        //String token1 = agentUserService.updateToken(agentUserExists.getOpenid());

                        data.setToken(token);
                        data.setIsbind(agentUserExists.getIsbind().toString());
                        resp.setCode("1").setMsg("微信登陆成功").setData(data);
                    } else {
                        String token1 = agentUserService.saveAgentInfo(errorResp.getOpenid(), nickName, gender, language, city, province, country, avatarUrl);
                        data.setToken(token1);
                        data.setIsbind("0");
                        resp.setCode("1").setMsg("微信登陆成功").setData(data);
                    }
                } else {
                    resp.setMsg(errorResp.getErrmsg()).setCode(String.valueOf(errorResp.getErrcode()));
                }
            } catch (ResourceAccessException e) {
                resp.setCode("0").setMsg("网络错误");
            }
        }

        return resp;
    }

    /**
     * 注册并登陆(完成)
     *
     * @param city_name
     * @param user_name
     * @param user_telphone
     * @param tel_code
     * @param company
     * @param token
     * @return
     */
    @PostMapping(value = "/register")
    public BaseResp register(String city_name, String user_name, String user_telphone, String tel_code, String company, String token) {
        BaseResp resp = new BaseResp();
        Integer code = agentUserService.register(city_name, user_name, user_telphone, tel_code, company, token);
        if (code == 201) {
            resp.setCode("201").setMsg("当前城市未开通服务");
        } else if (code == 202) {
            resp.setMsg("请先微信登陆").setCode("202");
        } else if (code == 203) {
            resp.setMsg("用户已经注册过,请直接登陆").setCode("203");
        } else if (code == 204) {
            resp.setMsg("验证码错误").setCode("204");
        } else if (code == 205) {
            resp.setCode("205").setMsg("请输入正确的手机号");
        } else {
            AgentRegisterData data = new AgentRegisterData();
            data.setToken(token);
            resp.setCode("1").setMsg("注册成功").setData(data);
        }

        return resp;
    }

    /**
     * 手机验证码登录(完成)
     *
     * @param token
     * @param telphone
     * @param tel_code
     * @return
     */
    @PostMapping(value = "/vclogin")
    public BaseResp vcLogin(String token, String telphone, String tel_code) {
        BaseResp resp = new BaseResp();
        AgentUser agentUser = agentUserService.findUserByTokenAndTelphone(token, telphone);
        if (!ObjectUtils.isEmpty(agentUser)) {
            if (agentUser.getPhoneCode().equals(tel_code)) {
                agentUserService.bindUser(token, telphone);
                AgentRegisterData data = new AgentRegisterData();
                data.setToken(token);
                resp.setMsg("登陆成功").setCode("1").setData(data);
            } else {
                resp.setMsg("验证码不正确").setCode("0");
            }

        } else {
            resp.setCode("0").setMsg("用户不存在");
        }
        return resp;
    }

    /**
     * 修改用户信息(完成)
     *
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
    public BaseResp modifyInfo(String token, String headimg, String wx_account, String telphone, String company, String stores, String introduce) {
        BaseResp resp = new BaseResp();
        AgentUser agentUser = agentUserService.findbindUserByToken(token);
        if (!ObjectUtils.isEmpty(agentUser)) {
            agentUserService.updateUserInfo(headimg == null || headimg == "" ? agentUser.getAvatarUrl() : headimg, wx_account == null || wx_account == "" ? agentUser.getWxaccount() : wx_account,
                    telphone == null || telphone == "" ? agentUser.getUserPhone() : telphone, company == null || company == "" ? agentUser.getCompanyInfo() : company,
                    stores == null || stores == "" ? agentUser.getStores() : stores, introduce == null || introduce == "" ? agentUser.getIntroduce() : introduce, token);
            resp.setCode("1").setMsg("修改信息成功");
        } else {
            resp.setMsg("用户未绑定").setCode("0");
        }
        return resp;
    }

    /**
     * 房屋详情(完成)
     *
     * @return(
     */
    @GetMapping(value = "/house_detail")
    public BaseResp houseDetail(Integer house_id, String token) {
        BaseResp resp = new BaseResp();
        AgentHouseDetailData data = new AgentHouseDetailData();
        HouseInfo houseInfo = houseInfoService.findMyHouseById(house_id);
        DateFormat dateFormat = new SimpleDateFormat("yyyy");

        AgentUser agentUser = agentUserService.findbindUserByToken(token);
        if (!ObjectUtils.isEmpty(agentUser)) {

            if (!ObjectUtils.isEmpty(houseInfo)) {
                //房屋额外数据
                AgentHouseDetailData.AdditionalDataBean dataBean = new AgentHouseDetailData.AdditionalDataBean();
                dataBean.setAddress(houseInfo.getHouseAddress() == null ? "" : houseInfo.getHouseAddress());
                dataBean.setAge(houseInfo.getBuildingAge() == null ? "" : dateFormat.format(houseInfo.getBuildingAge()));
                dataBean.setDecorate_condition(houseInfo.getDecorateCondition() == null ? "" : houseInfo.getDecorateCondition());
                dataBean.setDistrict(houseInfo.getDistrict() == null ? "" : houseInfo.getDistrict());
                dataBean.setHouse_tier(houseInfo.getHouseTier() == null ? "" : houseInfo.getHouseTier());
                dataBean.setHouse_num(houseInfo.getId().toString());
                dataBean.setProperty_rights_type(houseInfo.getPropertyRightsType() == null ? "" : houseInfo.getPropertyRightsType());
                dataBean.setResidence_booklet(houseInfo.getResidenceBooklet() == null ? "" : houseInfo.getResidenceBooklet());
                dataBean.setToward(houseInfo.getHouseOrientation() == null ? "" : houseInfo.getHouseOrientation());
                dataBean.setType(houseInfo.getHouseLayout() == null ? "" : houseInfo.getHouseLayout());
                dataBean.setUnitprice(houseInfo.getHouseUnitPrice() == null ? "0" : houseInfo.getHouseUnitPrice().toString());
                dataBean.setUse(houseInfo.getHouseUse() == null ? "" : houseInfo.getHouseUse());

                //好房推荐
                List<AgentHouseDetailData.GoodhousesBean> goodhousesBeanList = new ArrayList<>();
                List<HouseInfo> houseInfoList = houseInfoService.findRecommendHouse();
                if (houseInfoList.size() > 0) {
                    for (HouseInfo temHouseInfo : houseInfoList) {
                        AgentHouseDetailData.GoodhousesBean goodhousesBean = new AgentHouseDetailData.GoodhousesBean();
                        goodhousesBean.setArea(temHouseInfo.getHouseArea() == null ? "0" : temHouseInfo.getHouseArea().toString());
                        List<HouseImg> houseImgList = houseImgService.findHouseImg(temHouseInfo.getId());
                        if (houseImgList.size() > 0)
                            goodhousesBean.setImage(houseImgList.get(0).getImgurl() == null ? "" : houseImgList.get(0).getImgurl());
                        goodhousesBean.setLabel(temHouseInfo.getHouseLabel() == null ? "" : temHouseInfo.getHouseLabel());
                        goodhousesBean.setLayout(temHouseInfo.getHouseLayout() == null ? "" : temHouseInfo.getHouseLayout());
                        goodhousesBean.setTier(temHouseInfo.getHouseTier() == null ? "" : temHouseInfo.getHouseTier());
                        goodhousesBean.setTitle(temHouseInfo.getHouseTitle() == null ? "" : temHouseInfo.getHouseTitle());
                        goodhousesBean.setTotal_price(temHouseInfo.getHouseTotalPrice() == null ? "0" : temHouseInfo.getHouseTotalPrice().toString());
                        goodhousesBean.setUnit_price(temHouseInfo.getHouseUnitPrice() == null ? "0" : temHouseInfo.getHouseUnitPrice().toString());
                        goodhousesBeanList.add(goodhousesBean);
                    }
                }
                data.setGoodhouses(goodhousesBeanList);
                List<AgentHouseDetailData.GuessyourlikehousesBean> guessyourlikehousesBeans = new ArrayList<>();
                data.setGuessyourlikehouses(guessyourlikehousesBeans);
                data.setMatters_needing_attention("");
                data.setTake_look_at_trip("");
                AgentHouseDetailData.HouseDataBean houseDataBean = new AgentHouseDetailData.HouseDataBean();
                houseDataBean.setArea(houseInfo.getHouseArea() == null ? "" : houseInfo.getHouseArea().toString());
                houseDataBean.setLayout(houseInfo.getHouseLayout() == null ? "" : houseInfo.getHouseLayout());
                houseDataBean.setTotalprice(houseInfo.getHouseTotalPrice() == null ? "0" : houseInfo.getHouseTotalPrice().toString());
                data.setHouse_data(houseDataBean);
                boolean isatten = attentionService.isAttention(house_id, token);
                data.setIs_attention(isatten == true ? "1" : "0");
                data.setAdditional_data(dataBean);
                AgentHouseDetailData.HouseDescBean houseDescBean = new AgentHouseDetailData.HouseDescBean();
                houseDescBean.setType(houseInfo.getHouseLayout() == null ? "" : houseInfo.getHouseLayout().toString());
                houseDescBean.setLabel(houseInfo.getHouseLabel() == null ? "" : houseInfo.getHouseLabel());
                houseDescBean.setProvider(houseInfo.getHouseType() == null ? "官方直售" : "卖家提供");
                houseDescBean.setTitle(houseInfo.getHouseTitle() == null ? "" : houseInfo.getHouseTitle());
                data.setHouse_desc(houseDescBean);
                AgentHouseDetailData.TradeinfoBean tradeinfoBean = new AgentHouseDetailData.TradeinfoBean();
                tradeinfoBean.setActuality(houseInfo.getHouseActuality() == null ? "" : houseInfo.getHouseActuality());
                tradeinfoBean.setAir_source_expense(houseInfo.isAirSourceExpense() == true ? "1" : "0");
                tradeinfoBean.setHand_house_time(houseInfo.getHandHouseTime() == null ? "" : DateUtil.dateFormat(houseInfo.getHandHouseTime()));
                tradeinfoBean.setMaintenance_funds(houseInfo.isMaintenanceFunds() == true ? "1" : "0");
                tradeinfoBean.setReview_house_type(houseInfo.getReviewHouseType() == null ? "" : houseInfo.getReviewHouseType());
                tradeinfoBean.setSell_house_reason(houseInfo.getSellHouseReason() == null ? "" : houseInfo.getSellHouseReason());
                tradeinfoBean.setTaxtype(houseInfo.getHouseTax() == null ? "" : houseInfo.getHouseTax());
                tradeinfoBean.setTwo_taxes_assume(houseInfo.isTwoTaxesAssume() == true ? "1" : "0");
                data.setTradeinfo(tradeinfoBean);

                List<HouseImg> houseImgList = houseImgService.findHouseImg(house_id);
                if (houseImgList.size() > 0) {
                    List<String> house_img_url = new ArrayList<>();
                    for (HouseImg img : houseImgList) {
                        house_img_url.add(img.getImgurl());
                    }
                    data.setHouse_img_list(house_img_url);
                }
                data.setHouse_img_list(new ArrayList<>());
                data.setPlace_pic(new ArrayList<>());
                List<AgentHouseDetailData.SamehousesBean> samehousesBeanList = new ArrayList<>();
                List<HouseInfo> houseInfoList1 = houseInfoService.findHouseByDistrict(houseInfo.getDistrict());
                if (houseInfoList1.size() > 0) {
                    for (HouseInfo houseInfo1 : houseInfoList1) {
                        AgentHouseDetailData.SamehousesBean samehousesBean = new AgentHouseDetailData.SamehousesBean();
                        samehousesBean.setArea(houseInfo1.getHouseArea() == null ? "0" : houseInfo1.getHouseArea().toString());
                        samehousesBean.setImage("");
                        samehousesBean.setLabel(houseInfo1.getHouseLabel() == null ? "" : houseInfo1.getHouseLabel());
                        samehousesBean.setLayout(houseInfo1.getHouseLayout() == null ? "" : houseInfo1.getHouseLayout());
                        samehousesBean.setTier(houseInfo1.getHouseTier() == null ? "" : houseInfo1.getHouseTier());
                        samehousesBean.setTitle(houseInfo1.getHouseTitle() == null ? "" : houseInfo1.getHouseTitle());
                        samehousesBean.setTotal_price(houseInfo1.getHouseTotalPrice() == null ? "0" : houseInfo1.getHouseTotalPrice().toString());
                        samehousesBean.setUnit_price(houseInfo1.getHouseUnitPrice() == null ? "0" : houseInfo1.getHouseUnitPrice().toString());
                        samehousesBeanList.add(samehousesBean);
                    }
                }
                data.setSamehouses(samehousesBeanList);
                AgentHouseDetailData.LocationBean locationBean = new AgentHouseDetailData.LocationBean();

                RestTemplate restTemplate = new RestTemplate();
                Map<String, String> map = new HashMap<>();
                map.put("address", houseInfo.getHouseDetailAddress());
                map.put("key", TencentProperties.KEY);
                GeoCoderResp geoCoderResp = restTemplate.getForObject(TencentUrl.GEOCODERURL, GeoCoderResp.class, map);
                if (geoCoderResp.getStatus() == 0) {
                    locationBean.setLatitude(String.valueOf(geoCoderResp.getResult().getLocation().getLat()));
                    locationBean.setLongitude(String.valueOf(geoCoderResp.getResult().getLocation().getLng()));
                }
                data.setLocation(locationBean);
                resp.setMsg("获取房源信息成功").setCode("1").setData(data);
            } else {
                resp.setCode("1").setMsg("没有该房源信息").setData(null);
            }
        } else {
            resp.setCode("0").setMsg("用户未登陆");
        }
        return resp;
    }

    /**
     * 关注房源(完成)
     *
     * @param house_id
     * @param token
     * @return
     */
    @PostMapping(value = "/attention_house")
    public BaseResp attentionHouse(Integer house_id, String token) {
        BaseResp resp = new BaseResp();
        int result = attentionService.attenHouse(house_id, token);
        switch (result) {
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

    /**
     * 获取房源列表(完成)
     * @param token
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "/houselist")
    public BaseResp houseList(String token, Integer page, Integer limit) {
        BaseResp resp = new BaseResp();
        AgentUser agentUser = agentUserService.findbindUserByToken(token);
        if(!ObjectUtils.isEmpty(agentUser)) {
            AgentHouseListData data = new AgentHouseListData();
            List<AgentHouseListData.HouseListBean> houseListBeans = new ArrayList<>();
            List<AgentHouseListData.RecommendHouseBean> recommendHouseBeanList = new ArrayList<>();
            List<HouseInfo> recomHouseList = houseInfoService.findRecommendHouse();
            if (recomHouseList.size() > 0) {
                for (HouseInfo houseInfo : recomHouseList) {
                    AgentHouseListData.RecommendHouseBean recommendHouseBean = new AgentHouseListData.RecommendHouseBean();
                    recommendHouseBean.setArea(houseInfo.getHouseArea() == null ? "0" : houseInfo.getHouseArea().toString());
                    recommendHouseBean.setHouse_id(houseInfo.getId().toString());
                    recommendHouseBean.setLayout(houseInfo.getHouseLayout() == null ? "0" : houseInfo.getHouseLayout());
                    recommendHouseBean.setTitle(houseInfo.getHouseTitle() == null ? "" : houseInfo.getHouseTitle());
                    recommendHouseBean.setTotalprice(houseInfo.getHouseTotalPrice() == null ? "0" : houseInfo.getHouseTotalPrice().toString());
                    List<HouseImg> houseImgList = houseImgService.findHouseImg(houseInfo.getId());
                    if (houseImgList.size() > 0)
                        recommendHouseBean.setImage(houseImgList.get(0).getImgurl() == null ? "" : houseImgList.get(0).getImgurl());
                    recommendHouseBeanList.add(recommendHouseBean);
                }
            }

            List<HouseInfo> houseInfoList = houseInfoService.findIsSellHouse(agentUser.getCityName(),page,limit);
            if(houseInfoList.size() > 0){
                for(HouseInfo houseInfo:houseInfoList){
                    AgentHouseListData.HouseListBean houseListBean = new AgentHouseListData.HouseListBean();
                    houseListBean.setArea(houseInfo.getHouseArea() == null ? "0":houseInfo.getHouseArea().toString());
                    houseListBean.setFloor(houseInfo.getHouseTier() == null ? "":houseInfo.getHouseTier());
                    houseListBean.setHouse_id(houseInfo.getId().toString());
                    houseListBean.setLabel(houseInfo.getHouseLabel() == null ? "":houseInfo.getHouseLabel());
                    List<HouseImg> houseImgList = houseImgService.findHouseImg(houseInfo.getId());
                    if(houseImgList.size() > 0 )
                    houseListBean.setImg(houseImgList.get(0).getImgurl() == null ? "":houseImgList.get(0).getImgurl());
                    houseListBean.setTitle(houseInfo.getHouseTitle() == null ? "":houseInfo.getHouseTitle());
                    houseListBean.setTotal_price(houseInfo.getHouseTotalPrice() == null ? "0":houseInfo.getHouseTotalPrice().toString());
                    houseListBean.setToward(houseInfo.getHouseOrientation() == null ? "":houseInfo.getHouseOrientation());
                    houseListBean.setUnit_price(houseInfo.getHouseUnitPrice() == null ? "0":houseInfo.getHouseUnitPrice().toString());
                    houseListBean.setType(houseInfo.getHouseType().toString());
                    houseListBeans.add(houseListBean);
                }
            }
            data.setHouse_list(houseListBeans);
            data.setRecommend_house(recommendHouseBeanList);
            resp.setCode("1").setMsg("获取房源列表成功").setData(data);
        }else{
            resp.setCode("0").setMsg("用户未登陆");
        }
        return resp;
    }

    /**
     * 关注房源列表(完成)
     *
     * @return
     */
    @GetMapping(value = "/attention_house_list")
    public BaseResp attentionHouseList(String token) {
        BaseResp resp = new BaseResp();
        AgentUser agentUser = agentUserService.findbindUserByToken(token);
        if (!ObjectUtils.isEmpty(agentUser)) {
            List<Attention> attentions = attentionService.findAttentionList(token);
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (attentions.size() > 0) {
                List<AttentionHouseData> dataList = new ArrayList<>();
                for (Attention attention : attentions) {
                    AttentionHouseData data = new AttentionHouseData();
                    HouseInfo houseInfo = houseInfoService.findMyHouseById(attention.getHouseId());
                    if (!ObjectUtils.isEmpty(houseInfo)) {
                        data.setArea(houseInfo.getHouseArea().toString());
                        data.setAtten_time(dateFormat.format(attention.getAttenTime()));
                        data.setFloor(houseInfo.getHouseTier());
                        data.setIsselling(houseInfo.isSaleStop() == true ? "1" : "0");
                        data.setTitle(houseInfo.getHouseTitle());
                        List<HouseImg> houseImgList = houseImgService.findHouseImg(houseInfo.getId());
                        if (houseImgList.size() > 0) {
                            data.setImg(houseImgList.get(0).getImgurl() == null ? "" : houseImgList.get(0).getImgurl());
                        }
                    }
                    dataList.add(data);
                }
                resp.setCode("1").setData(dataList).setMsg("获取成功");
            } else {
                resp.setCode("1").setData(new ArrayList<>()).setMsg("获取成功");
            }
        } else {
            resp.setCode("0").setMsg("用户未登陆");
        }
        return resp;
    }

    /**
     * 忘记密码修改密码(完成)
     *
     * @param telphone
     * @param verify_code
     * @param newpassword
     * @return
     */
    @PostMapping(value = "/forget_pwd")
    public BaseResp forgetPwd(String telphone, String verify_code, String newpassword) {
        BaseResp resp = new BaseResp();
        Integer result = agentUserService.modifyPwd(telphone, verify_code, newpassword);
        switch (result) {
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
     *
     * @param telphone
     * @return
     */
    @PostMapping(value = "get_verifycode")
    public BaseResp getVerifyCode(String telphone,String token) {
        BaseResp resp = new BaseResp();
        Integer result = agentUserService.getVerifyCode(telphone,token);
        switch (result) {
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

    /**
     * 密码登陆(完成)
     *
     * @param telphone
     * @param password
     * @return
     * @throws UnsupportedEncodingException
     * @throws NoSuchAlgorithmException
     */
    @PostMapping(value = "/pdlogin")
    public BaseResp pdLogin(String telphone, String password) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        BaseResp resp = new BaseResp();
        AgentUser agentUser = agentUserService.findUserByPhoneAndIsBind(telphone, 1);
        if (!ObjectUtils.isEmpty(agentUser)) {
            if (MD5Util.validPassword(password, agentUser.getPassword())) {
                resp.setMsg("登陆成功").setCode("1");
            } else {
                resp.setMsg("密码错误").setCode("0");
            }
        } else {
            resp.setCode("0").setMsg("用户不存在");
        }
        return resp;
    }

    /**
     * 获取城市列表
     *
     * @return
     */
    @GetMapping(value = "/citylist")
    public BaseResp cityList() {
        BaseResp resp = new BaseResp();
        CityData data = new CityData();
        List<CityData.CityInitials> group = new ArrayList<>();
        CityData.DefaultCityBean defaultCityBean = new CityData.DefaultCityBean();
        defaultCityBean.setCity_name("南通市");
        defaultCityBean.setIs_open("1");
        data.setDefault_city(defaultCityBean);
        List<String> citiesList = citiesService.findOpenCities();
        for (int i = 'A'; i <= 'Z'; i++) {
            CityData.CityInitials cityInitials = getCityInitials(citiesList, (char) i + "");
            if (cityInitials != null) {
                group.add(cityInitials);
            }
        }
        data.setGroup(group);

        resp.setData(data).setMsg("获取城市列表成功").setCode("1");
        return resp;
    }


    private CityData.CityInitials getCityInitials(List<String> citiesList, String litte_letter) {
        List<String> cityname = new ArrayList<>();
        CityData.CityInitials cityInitials = new CityData.CityInitials();
        for (String city : citiesList) {
            String letter = PinyinUtil.getPinYinHeadChar(city).substring(0, 1).toUpperCase();
            if (letter.equals(litte_letter) && !ObjectUtils.isEmpty(city)) {
                cityInitials.setCity_initials(letter);
                cityname.add(city);
                cityInitials.setCity_name(cityname);
            }
        }
        if (cityInitials.getCity_name() != null) {
            return cityInitials;
        } else {
            return null;
        }
    }

    /**
     * 根据位置获取当前城市
     *
     * @param latitude
     * @param longitude
     * @return
     */
    @PostMapping(value = "/get_postion_city")
    public BaseResp getPostionCity(Double latitude, Double longitude) {
        BaseResp resp = new BaseResp();

        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<>();
        map.put("location", (latitude == null ? "39.984154" : latitude) + "," + (longitude == null ? "116.307490" : longitude));
        map.put("key", TencentProperties.KEY);
        map.put("get_poi", "0");
        LocationResp locationResp = restTemplate.getForObject(TencentUrl.LOCATIONURL, LocationResp.class, map);
        if (locationResp.getStatus() == 0) {
            PosCityData data = new PosCityData();
            String city = locationResp.getResult().getAddress_component().getCity();
            data.setCityname(city);
            boolean isopen = citiesService.isOpen(city);
            data.setIsopen(isopen == true ? "1" : "0");
            data.setDefault_cityname("南通市");
            resp.setMsg("获取地址成功").setCode("1").setData(data);
        } else {
            resp.setMsg("获取地址失败").setCode("0");
        }
        return resp;
    }

    @PostMapping(value = "/send_msg")
    public BaseResp sendMsg() {
        BaseResp resp = new BaseResp();
        return resp;
    }

    /**
     * 获取用户信息(完成)
     * @param token
     * @return
     */
    @GetMapping(value = "/userinfo")
    public BaseResp userInfo(String token) {
        BaseResp resp = new BaseResp();
        AgentUser agentUser = agentUserService.findbindUserByToken(token);
        if(!ObjectUtils.isEmpty(agentUser)){
            AgentUserInfoData data = new AgentUserInfoData();
            data.setAvatarUrl(agentUser.getAvatarUrl() == null ? "":agentUser.getAvatarUrl());
            data.setCompany(agentUser.getCompanyInfo() == null ? "":agentUser.getCompanyInfo());
            data.setIntroduce(agentUser.getIntroduce() == null ? "":agentUser.getIntroduce());
            data.setName(agentUser.getUserName() == null ? "":agentUser.getUserName());
            data.setShop(agentUser.getStores() == null ? "":agentUser.getStores());
            data.setTelphone(agentUser.getUserPhone() == null ? "":agentUser.getUserPhone());
            data.setWxaccount(agentUser.getWxaccount() == null ? "":agentUser.getWxaccount());
            resp.setCode("1").setMsg("获取用户信息成功").setData(data);
        }else{
            resp.setCode("用户未登陆").setCode("0");
        }
        return resp;
    }

    @GetMapping(value = "/msg_detail")
    public BaseResp msgDetail() {
        BaseResp resp = new BaseResp();
        return resp;
    }

    @GetMapping(value = "/msglist")
    public BaseResp msgList(String token) {
        BaseResp resp = new BaseResp();
        AgentUser agentUser = agentUserService.findbindUserByToken(token);
        if(!ObjectUtils.isEmpty(agentUser)) {
            List<MsgListData> msgListDataList = new ArrayList<>();
            resp.setCode("1").setMsg("获取消息列表成功").setData(msgListDataList);
        }else{
            resp.setMsg("用户未登陆").setCode("0");
        }
        return resp;
    }


    @PostMapping(value = "/call_private_phone")
    public BaseResp callPrivatePhone(String token ,Integer house_id){
        BaseResp resp = new BaseResp();
        AgentUser agentUser = agentUserService.findbindUserByToken(token);
        if(!ObjectUtils.isEmpty(agentUser)){
            PrivatePhoneData data = new PrivatePhoneData();
            data.setUser_phone(agentUser.getUserPhone());
            data.setExpiration_time("5");
            data.setBuyer_phone("15962847050");
            resp.setCode("1").setMsg("获取加密电话成功").setData(data);
        }else{
            resp.setCode("0").setMsg("用户未登陆");
        }
        return resp;
    }
}
