package com.huiketong.sumaifang.controller;

import com.aliyuncs.exceptions.ClientException;
import com.google.gson.Gson;
import com.huiketong.sumaifang.constant.TencentProperties;
import com.huiketong.sumaifang.constant.TencentUrl;
import com.huiketong.sumaifang.data.*;
import com.huiketong.sumaifang.domain.Banner;
import com.huiketong.sumaifang.domain.CommonUser;
import com.huiketong.sumaifang.domain.HouseImg;
import com.huiketong.sumaifang.domain.HouseInfo;
import com.huiketong.sumaifang.service.*;
import com.huiketong.sumaifang.utils.AlicomDysmsUtil;
import com.huiketong.sumaifang.utils.TokenUtil;
import com.huiketong.sumaifang.vo.BaseResp;
import com.huiketong.sumaifang.vo.LocationResp;
import com.huiketong.sumaifang.vo.WxErrorResp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/api")
public class SMFApiController {

    @Autowired
    CommonUserService commonUserService;
    @Autowired
    WXService wxService;
    @Autowired
    HouseInfoService houseInfoService;

    @Autowired
    CitiesService citiesService;
    @Autowired
    BannerService bannerService;
    @Autowired
    HouseImgService houseImgService;
    @Autowired
    AgentUserService agentUserService;


    /**
     * 主页信息
     *
     * @param token
     * @param latitude
     * @param longitude
     * @return
     */
    @GetMapping(value = "/homeinfo")
    public BaseResp homeInfo(String token, Double latitude, Double longitude) {
        BaseResp resp = new BaseResp();
        HomeData data = new HomeData();


        //获取地址信息
        RestTemplate restTemplate = new RestTemplate();
        Map<String, String> map = new HashMap<>();
        map.put("location", (latitude == null ? "39.984154" : latitude) + "," + (longitude == null ? "116.307490" : longitude));
        map.put("key", TencentProperties.KEY);
        map.put("get_poi", "0");

        LocationResp locationResp = restTemplate.getForObject(TencentUrl.LOCATIONURL, LocationResp.class, map);
        if (!ObjectUtils.isEmpty(locationResp)) {
            String city = locationResp.getResult().getAddress_component().getCity();
            if (!ObjectUtils.isEmpty(city)) {
                CityData cityData = new CityData();
                if (citiesService.isOpen(city)) {
                    cityData.setIsopen(true);
                    cityData.setDefault_city("南通");
                } else {
                    cityData.setIsopen(false);
                    cityData.setDefault_city("南通");
                }
                data.setCityinfo(cityData);
            }
        }
        List<Banner> bannerList = bannerService.findAllBanner();
        List<BannerData> bannerDataList = new ArrayList<>();
        if (bannerList.size() > 0) {
            for (int i = 0; i < bannerList.size(); i++) {
                BannerData bannerData = new BannerData();
                bannerData.setImgurl(bannerList.get(i).getImgurl());
                bannerData.setLinkurl(bannerList.get(i).getLinkurl());
                bannerDataList.add(bannerData);
            }
        }
        data.setBannerlist(bannerDataList);
        MarketData marketData = new MarketData();
        marketData.setAgentNum(agentUserService.getAgentNum());
        marketData.setAveragePrice(houseInfoService.getAveragePrice());
        marketData.setAverageWorkOffDays(10);
        marketData.setClientNum(3123123);
        marketData.setIntermediaryNum(312310);
        marketData.setIncrease(-0.12d);

        data.setMarket(marketData);
        data.setOrglist(new ArrayList<>());

        resp.setCode("1").setMsg("获取首页信息成功").setData(data);
        return resp;
    }

    /**
     * @param little_district
     * @param house_area
     * @param expect_price
     * @param token
     * @return
     */
    @PostMapping(value = "/upload_house_info")
    @ResponseBody
    public BaseResp uploadHouseInfo(String little_district, double house_area, Double expect_price, String token, String telphone, String verify_code) {
        BaseResp resp = new BaseResp();
        if (!ObjectUtils.isEmpty(telphone) && !ObjectUtils.isEmpty(verify_code) && ObjectUtils.isEmpty(token)) {
            String verifyCode = commonUserService.findVerifyCode(telphone);
            if (!ObjectUtils.isEmpty(verifyCode)) {
                if (verifyCode.equals(verify_code)) {
                    if (houseInfoService.uploadHouseInfo(little_district, house_area, expect_price, telphone, token)) {
                        resp.setCode("1").setMsg("发布成功");
                    } else {
                        resp.setCode("0").setMsg("发布失败");
                    }
                }
            } else {
                resp.setCode("300").setMsg("该手机号码不存在");
            }
        } else if ((ObjectUtils.isEmpty(telphone) || ObjectUtils.isEmpty(verify_code)) && ObjectUtils.isEmpty(token)) {
            resp.setCode("200").setMsg("请先获取验证码");
        } else {
            if (houseInfoService.uploadHouseInfo(little_district, house_area, expect_price, telphone, token)) {
                resp.setCode("1").setMsg("发布成功");
            } else {
                resp.setCode("0").setMsg("发布失败");
            }
        }
        return resp;
    }

    /**
     * 我的房源
     *
     * @param token
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "/myhouses")
    public BaseResp myHouse(String token, Integer page, Integer limit) {
        BaseResp resp = new BaseResp();

        List<HouseInfo> houseInfoList = houseInfoService.findMyHouseList(token, page, limit);
        List<MyHouseData> houseDataList = new ArrayList<>();
        if (houseInfoList.size() > 0) {
            for (int i = 0; i < houseInfoList.size(); i++) {
                MyHouseData houseData = new MyHouseData();
                houseData.setTitle(houseInfoList.get(i).getHouseTitle());
                houseData.setHouseArea(houseInfoList.get(i).getHouseArea());
                houseData.setHouseImg(houseImgService.findHouseImg(houseInfoList.get(i).getId()).get(i).getImgurl());

                switch (houseInfoList.get(i).getAssessor()) {
                    case 1:
                        houseData.setIspass("通过审核");
                        break;
                    case 2:
                        houseData.setIspass("待审核");
                        break;
                    case 3:
                        houseData.setIspass("审核未通过");
                        break;
                }

                houseData.setSeeNum(houseInfoList.get(i).getSeeNum());
                houseData.setVisitsNum(houseInfoList.get(i).getVisitNum());
                houseData.setHouse_id(houseInfoList.get(i).getId());
                DateFormat df = new SimpleDateFormat("yyyy-MM-dd E a HH:mm:ss");
                houseData.setSubmitTime(df.format(houseInfoList.get(i).getCreateTime()));
                houseData.setExpect_price(houseInfoList.get(i).getExpectPrice());
                houseDataList.add(houseData);
            }
        }
        resp.setData(houseDataList).setCode("1").setMsg("获取我的房源信息成功");
        return resp;
    }

    /**
     * 我的
     *
     * @param token
     * @return
     */
    @PostMapping(value = "/mine")
    public BaseResp mine(String token) {
        BaseResp resp = new BaseResp();
        CommonUser user = commonUserService.findMine(token);
        if (!ObjectUtils.isEmpty(user)) {
            MineData data = new MineData();
            data.setNickname(user.getNickName());
            data.setTelphone(user.getUserTelphone());
            data.setHeadimg(user.getAvatarUrl());
            data.setAboutus_version("1.0.0");
            data.setCus_ser_tel("000000");
            resp.setMsg("获取我的信息成功").setCode("1").setData(data);
        } else {
            resp.setMsg("没有用户信息").setCode("0");
        }

        return resp;
    }

    @PostMapping(value = "/sellers")
    public BaseResp sellers(String token, Integer house_id) {
        BaseResp resp = new BaseResp();
        DateFormat df = new SimpleDateFormat("yyyy");
        SellerData data = new SellerData();
        if (ObjectUtils.isEmpty(house_id)) {
            List<HouseInfo> houseInfoList = houseInfoService.findMyHouseList(token, 1, 100);
            if (houseInfoList.size() > 0) {
                data.setBuilding_age(df.format(houseInfoList.get(0).getBuildingAge()));
                data.setDistrict(houseInfoList.get(0).getDistrict());
                data.setHouse_area(houseInfoList.get(0).getHouseArea());
                data.setHouse_layer(houseInfoList.get(0).getHouseTier());
                data.setHouse_layout(houseInfoList.get(0).getHouseLayout());
                data.setHouse_orientation(houseInfoList.get(0).getHouseOrientation());
                data.setHouse_id(houseInfoList.get(0).getId());
                data.setHouse_price(houseInfoList.get(0).getHouseTotalPrice());
                data.setHouse_style(houseInfoList.get(0).getDecorateCondition());
                data.setHouse_title(houseInfoList.get(0).getHouseTitle());
                data.setHouse_unit_price(houseInfoList.get(0).getHouseUnitPrice());
                data.setProperty_rights_type(houseInfoList.get(0).getPropertyRightsType());
                data.setHouse_use(houseInfoList.get(0).getHouseUse());
                List<HouseImg> houseImgList = houseImgService.findHouseImg(houseInfoList.get(0).getId());
                if (houseImgList.size() > 0) {
                    data.setHouseimgs(houseImgList.get(0).getImgurl());
                }
                IsPassData isPassData = new IsPassData();
                isPassData.setValue(houseInfoList.get(0).getAssessor());
                switch (houseInfoList.get(0).getAssessor()) {
                    case 1:
                        isPassData.setText("通过审核");
                        break;
                    case 2:
                        isPassData.setText("待审核");
                        break;
                    case 3:
                        isPassData.setText("审核未通过");
                        break;
                    default:
                        break;
                }
                data.setIspass(isPassData);
                NewsData newsData = new NewsData();
                DateFormat df1 = new SimpleDateFormat("yyyy");
                //newsData.setTime(df1.format());
                data.setNews(newsData);
                CountData countData = new CountData();
                countData.setBrowse_num(houseInfoList.get(0).getVisitNum());
                countData.setCall_num(houseInfoList.get(0).getCallNum());
                countData.setFollow_num(houseInfoList.get(0).getAttentionNum());
                countData.setSee_num(houseInfoList.get(0).getSeeNum());
                data.setCount(countData);

            }
        } else {
            houseInfoService.findMyHouseById(house_id);
        }

        resp.setCode("0").setMsg("获取卖房动态成功").setData(data);
        return resp;
    }

    /**
     * 获取验证码
     *
     * @param telphone
     * @param token
     * @return
     */
    @PostMapping(value = "getverifycode")
    public BaseResp getVerifyCode(String telphone, String token) {
        BaseResp resp = new BaseResp();
        String code = AlicomDysmsUtil.getCode(); //验证码
        try {
            AlicomDysmsUtil.sendSms(telphone, code, "SMS_169898923");
            commonUserService.saveUser(telphone, code, token);
            resp.setCode("1").setMsg("获取验证码成功");
        } catch (ClientException e) {
            e.printStackTrace();
            resp.setCode("300").setMsg("获取验证码失败");
        }
        return resp;
    }

    /**
     * 手机号登陆
     *
     * @param telphone
     * @param verifyCode
     * @return
     */
    @PostMapping(value = "/login")
    public BaseResp login(String telphone, String verifyCode, String token) {
        BaseResp resp = new BaseResp();
        try {
            commonUserService.login(telphone, verifyCode, token);
            TokenData data = new TokenData();
            data.setToken(token);
            resp.setCode("1").setMsg("登陆成功").setData(data);
        } catch (Exception e) {
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
                boolean islogin = commonUserService.isLogin(errorResp.getOpenid());
                boolean isbind = commonUserService.isBind(errorResp.getOpenid());
                String token = TokenUtil.createJwtToken(errorResp.getOpenid());
                if (isbind) {
                    data.setIsbind("1");
                } else {
                    data.setIsbind("0");
                }
                if (islogin) {
                    data.setToken(token);
                    resp.setMsg("登陆成功").setCode("1").setData(data);
                } else {

                    CommonUser auth = new CommonUser();
                    auth.setOpenid(errorResp.getOpenid());
                    auth.setToken(token);
                    auth.setNickName(nickName == null ? "" : nickName);
                    auth.setGender(String.valueOf(gender));
                    auth.setLanguage(language == null ? "" : language);
                    auth.setCity(city == null ? "" : city);
                    auth.setProvince(province == null ? "" : province);
                    auth.setCountry(country == null ? "" : country);
                    auth.setAvatarUrl(avatarUrl == null ? "" : avatarUrl);
                    commonUserService.save(auth);
                    resp.setMsg("授权成功").setCode("1").setData(data);
                }
            } else {
                resp.setMsg(errorResp.getErrmsg()).setCode(String.valueOf(errorResp.getErrcode())).setData(data);
            }
        }
        return resp;
    }


}
