package com.huiketong.sumaifang.controller;

import com.aliyuncs.exceptions.ClientException;
import com.google.gson.Gson;
import com.huiketong.sumaifang.constant.TencentProperties;
import com.huiketong.sumaifang.constant.TencentUrl;
import com.huiketong.sumaifang.data.*;
import com.huiketong.sumaifang.domain.Banner;
import com.huiketong.sumaifang.domain.CommonUser;
import com.huiketong.sumaifang.domain.HouseInfo;
import com.huiketong.sumaifang.repository.BannerDao;
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


    @GetMapping(value = "/homeinfo")
    public BaseResp homeInfo(String token,Double latitude,Double longitude){
        BaseResp resp = new BaseResp();
        HomeData data = new HomeData();


        //获取地址信息
        RestTemplate restTemplate = new RestTemplate();
        Map<String,String> map = new HashMap<>();
        map.put("location",(latitude==null ? "39.984154":latitude)+","+(longitude == null ? "116.307490":longitude));
        map.put("key", TencentProperties.KEY);
        map.put("get_poi","0");

        LocationResp locationResp =  restTemplate.getForObject(TencentUrl.LOCATIONURL, LocationResp.class,map);
        if(!ObjectUtils.isEmpty(locationResp)){
            String city = locationResp.getResult().getAddress_component().getCity();
            if(!ObjectUtils.isEmpty(city)){
                CityData cityData = new CityData();
                if(citiesService.isOpen(city)){
                    cityData.setIsopen(true);
                    cityData.setDefault_city("南通");
                }else{
                    cityData.setIsopen(false);
                    cityData.setDefault_city("南通");
                }
                data.setCityinfo(cityData);
            }
        }
        List<Banner> bannerList = bannerService.findAllBanner();
        List<BannerData> bannerDataList = new ArrayList<>();
        if(bannerList.size() > 0){
            for(int i = 0; i < bannerList.size();i++){
                BannerData bannerData = new BannerData();
                bannerData.setImgurl(bannerList.get(i).getImgurl());
                bannerData.setLinkurl(bannerList.get(i).getLinkurl());
                bannerDataList.add(bannerData);
            }
        }
        data.setBannerlist(bannerDataList);

       List<HouseInfo> houseInfoList = houseInfoService.findMyHouseList(token);
       List<MyHouseData> houseDataList = new ArrayList<>();
       if(houseInfoList.size() > 0){
           for(int i = 0 ; i < houseInfoList.size();i++){
               MyHouseData houseData = new MyHouseData();
               houseData.setTitle(houseInfoList.get(i).getHouseTitle());
               houseData.setHouseArea(houseInfoList.get(i).getHouseArea());
               houseData.setHouseImg(houseImgService.findHouseImg(houseInfoList.get(i).getId()).getImgurl());
               houseData.setIspass(houseInfoList.get(i).getAssessor());
               houseData.setSeeNum(houseInfoList.get(i).getSeeNum());
               houseData.setVisitsNum(houseInfoList.get(i).getVisitNum());
               houseData.setHouse_id(houseInfoList.get(i).getId());
               DateFormat df = new SimpleDateFormat("yyyy-MM-dd E a HH:mm:ss");
               houseData.setSubmitTime(df.format(houseInfoList.get(i).getCreateTime()));
               houseDataList.add(houseData);
           }
       }
        data.setMyhouselist(houseDataList);

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
            commonUserService.saveUser(telphone, code, token);
            resp.setCode("1").setMsg("获取验证码成功");
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
            commonUserService.login(telphone, verifyCode, token);
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
                boolean islogin = commonUserService.isLogin(errorResp.getOpenid());
                boolean isbind = commonUserService.isBind(errorResp.getOpenid());
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
