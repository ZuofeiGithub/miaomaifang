package com.huiketong.sumaifang.controller;

import com.aliyuncs.exceptions.ClientException;
import com.google.gson.Gson;
import com.huiketong.sumaifang.constant.TencentProperties;
import com.huiketong.sumaifang.constant.TencentUrl;
import com.huiketong.sumaifang.data.*;
import com.huiketong.sumaifang.domain.*;
import com.huiketong.sumaifang.service.*;
import com.huiketong.sumaifang.utils.AlicomDysmsUtil;
import com.huiketong.sumaifang.utils.DateUtil;
import com.huiketong.sumaifang.utils.PinyinUtil;
import com.huiketong.sumaifang.utils.TokenUtil;
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
import org.springframework.web.client.RestTemplate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * 通用的用户接口
 */
@RestController
@RequestMapping(value = "/api")
public class UniversalApiController {

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
    @Autowired
    ClientShowService clientShowService;
    @Autowired
    HeadlineService headlineService;
    @Autowired
    NotificationService notificationService;
    @Autowired
    HousePriceService housePriceService;
    @Autowired
    MessageService messageService;
    @Autowired
    ChatMessageService chatMessageService;
    @Autowired
    AreasService areasService;
    @Autowired
    BiotopeService biotopeService;
    @Autowired
    HouseAgentService houseAgentService;
    @Autowired
    AreaStreetService areaStreetService;
    @Autowired
    TerraceRecordService terraceRecordService;

    /**
     * 首页信息(17)
     *
     * @param token
     * @param latitude
     * @param longitude
     * @return
     */
    @PostMapping(value = "/homeinfo")
    public BaseResp homeInfo(String token, String latitude, String longitude, String city_name) {
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
            HomeCityData cityData = new HomeCityData();
            if (locationResp.getStatus() == 0) {
                String city = locationResp.getResult().getAddress_component().getCity();
                if (ObjectUtils.isEmpty(city_name)) {
                    if (!ObjectUtils.isEmpty(city)) {

                        if (citiesService.isOpen(city)) {
                            cityData.setIsopen(1);
                            cityData.setCity_name(city);
                        } else {
                            cityData.setIsopen(0);
                            cityData.setCity_name("南通市");
                        }
                        data.setCityinfo(cityData);
                    }
                } else {
                    if (citiesService.isOpen(city_name)) {
                        cityData.setIsopen(1);
                        cityData.setCity_name(city_name);
                    } else {
                        cityData.setIsopen(0);
                        cityData.setCity_name("南通市");
                    }
                    data.setCityinfo(cityData);
                }
            } else {
                cityData.setIsopen(1);
                cityData.setCity_name("南通市");
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
        } else {
            for (int i = 0; i < 3; i++) {
                BannerData bannerData = new BannerData();
                bannerData.setImgurl("http://img2.imgtn.bdimg.com/it/u=1718395925,3485808025&fm=26&gp=0.jpg");
                bannerData.setLinkurl("http://img2.imgtn.bdimg.com/it/u=1718395925,3485808025&fm=26&gp=0.jpg");
                bannerDataList.add(bannerData);
            }
        }
        data.setBannerlist(bannerDataList);
        MarketData marketData = clientShowService.findMarketByCity(city_name);
        data.setMarket(marketData);
        data.setOrglist(new ArrayList<>());

        List<HeadlineNewsData> newsDataList = headlineService.findHeadlineByCity(city_name);
        data.setTipslist(newsDataList);

        resp.setCode("1").setMsg("获取首页信息成功").setData(data);
        return resp;
    }


    /**
     * 房屋估价结果(20)
     *
     * @param house_id
     * @return
     */
    @GetMapping(value = "estimates_result")
    public BaseResp estimatesResult(Integer house_id) throws ParseException {
        BaseResp resp = new BaseResp();
        EstimateData data = new EstimateData();
        HouseInfo houseInfo = houseInfoService.findMyHouseById(house_id);
        if (!ObjectUtils.isEmpty(houseInfo)) {
            java.text.DecimalFormat df =new java.text.DecimalFormat("#.00");
            data.setHouse_price_wave(houseInfo.getHouseTotalPrice() == null ? df.format(1.2*houseInfo.getHouseArea())+"" : houseInfo.getHouseTotalPrice().toString());
            data.setHouse_layout(houseInfo.getHouseLayout());
            data.setHouse_total_price(df.format(1.0d*houseInfo.getHouseArea().doubleValue()));
            data.setHouse_layer(houseInfo.getHouseTier());
            data.setHouse_area(houseInfo.getHouseArea().toString());
            data.setDistrict(houseInfo.getDistrict());
            double dig = houseInfo.getHouseArea().doubleValue();
            double min = dig*0.8;
            double max = dig*1.2;
            data.setHouse_price_wave(( df.format(min)+"万-"+df.format(max)+"万"));
            Double cityHousePrice = houseInfoService.countAvgCityHousePrice(houseInfo.getHouseCity());
            DateFormat dateFormat = new SimpleDateFormat("MM");
            String datetime = dateFormat.format(new Date());
            List<String> dategroup = new ArrayList<>();
            List<String> pricegroup = new ArrayList<>();
            EstimateData.AreaHistoryAverPriceBean areaHistoryAverPriceBean = new EstimateData.AreaHistoryAverPriceBean();
            for (int i = 0; i < 12; i++) {
                dategroup.add(datetime+"月");
                pricegroup.add("0"+i);
                areaHistoryAverPriceBean.setMonth(dategroup);
                areaHistoryAverPriceBean.setPrice(pricegroup);
                datetime = DateUtil.subMonth(datetime);
            }
            data.setArea_history_aver_price(areaHistoryAverPriceBean);

            DateFormat dateFormat1 = new SimpleDateFormat("MM");
            String datetime1 = dateFormat1.format(new Date());
            List<String> dategroup1 = new ArrayList<>();
            List<String> pricegroup1 = new ArrayList<>();
            EstimateData.CityHistoryAverPriceBean cityHistoryAverPriceBean1 = new EstimateData.CityHistoryAverPriceBean();
            for (int i = 0; i < 12; i++) {
                dategroup1.add(datetime1+"月");
                pricegroup1.add("0"+(i+30));
                cityHistoryAverPriceBean1.setMonth(dategroup1);
                cityHistoryAverPriceBean1.setPrice(pricegroup1);
                datetime1 = DateUtil.subMonth(datetime1);
            }
            data.setCity_history_aver_price(cityHistoryAverPriceBean1);
            resp.setMsg("获取评估结果成功").setCode("1").setData(data);
        } else {
            resp.setMsg("房屋信息不存在").setCode("0");
        }
        return resp;
    }


    /**
     * 带看经纪人列表
     *
     * @return
     */
    @GetMapping(value = "take_look_at_agent_list")
    public BaseResp takeLookAtAgentList(String token, Integer house_id, Integer page, Integer limit) {
        BaseResp resp = new BaseResp();
        CommonUser user = commonUserService.findMine(token);
        if (!ObjectUtils.isEmpty(user)) {
            List<TakeLookData> dataList = houseAgentService.findAgentsLook(house_id, page, limit);
            resp.setCode("1").setMsg("获取带看经纪人列表成功").setData(dataList);
        } else {
            resp.setCode("0").setMsg("用户未登陆");
        }
        return resp;
    }

    /**
     * 通话经纪人
     *
     * @return
     */
    @GetMapping(value = "take_phone_agent_list")
    public BaseResp takePhoneAgentList(String token, Integer house_id, Integer page, Integer limit) {
        BaseResp resp = new BaseResp();
        CommonUser user = commonUserService.findMine(token);
        if (!ObjectUtils.isEmpty(user)) {
            List<CallerData> dataList = houseAgentService.findAgentsCaller(house_id, page, limit);
            resp.setCode("1").setMsg("获取").setData(dataList);
        } else {
            resp.setMsg("用户未登陆").setCode("0");
        }
        return resp;
    }

    /**
     * 调价
     *
     * @return
     */
    @GetMapping(value = "adjust_price")
    public BaseResp adjustPrice(Integer house_id, String token, Double price) {
        BaseResp resp = new BaseResp();
        CommonUser user = commonUserService.findMine(token);
        if (!ObjectUtils.isEmpty(user)) {
            if (houseInfoService.adjustPrice(house_id, price)) {
                resp.setCode("1").setMsg("调价成功");
            } else {
                resp.setCode("0").setMsg("调价失败");
            }
        } else {
            resp.setCode("0").setMsg("用户没有绑定.");
        }
        return resp;
    }

    /**
     * 看房时间(4)
     *
     * @return
     */
    @PostMapping(value = "open_home")
    public BaseResp openHome(Integer houseid, String seetime, String token) {
        BaseResp resp = new BaseResp();
        CommonUser user = commonUserService.findMine(token);
        if (!ObjectUtils.isEmpty(user)) {
            if (houseInfoService.orderTable(houseid, seetime)) {
                resp.setCode("1").setMsg("预约成功");
            } else {
                resp.setCode("0").setMsg("预约失败");
            }

        } else {
            resp.setCode("0").setMsg("用户为登陆,请先登陆");
        }
        return resp;
    }

    /**
     * 停售或出售(25)
     *
     * @return
     */
    @PostMapping(value = "halt_sales")
    public BaseResp haltSales(Integer house_id, String token, Integer salestop) {
        BaseResp resp = new BaseResp();
        CommonUser user = commonUserService.findMine(token);
        if (ObjectUtils.isEmpty(user)) {
            resp.setMsg("用户未登陆,请先登陆").setCode("0");
        } else {
            if (houseInfoService.stopSale(house_id, salestop)) {
                resp.setMsg("下架成功").setCode("1");
            } else {
                resp.setMsg("下架失败").setCode("0");
            }
        }
        return resp;
    }

    /**
     * 退出登录(8)
     *
     * @param token
     * @return
     */
    @PostMapping(value = "logout")
    public BaseResp logout(String token) {
        BaseResp resp = new BaseResp();
        if (commonUserService.unBind(token)) {
            resp.setMsg("解绑成功").setCode("1");
        } else {
            resp.setMsg("解绑失败").setCode("0");
        }
        return resp;
    }


    /**
     * 上传房源信息(18)
     *
     * @param little_district
     * @param house_area
     * @param expect_price
     * @param token
     * @return
     */
    @PostMapping(value = "/upload_house")
    public BaseResp uploadHouseInfo(String little_district, String city_name, double house_area, Double expect_price, String token, String telphone, String verify_code) {
        BaseResp resp = new BaseResp();
        if (!ObjectUtils.isEmpty(telphone) && !ObjectUtils.isEmpty(verify_code) && !ObjectUtils.isEmpty(token)) {
            String verifyCode = commonUserService.findVerifyCode(telphone);
            if (!ObjectUtils.isEmpty(verifyCode)) {
                if (verifyCode.equals(verify_code)) {
                    int houseId = houseInfoService.uploadHouseInfo(little_district, city_name, house_area, expect_price, telphone, token);
                    if ( houseId != 0) {
                        UploadHouseData data = new UploadHouseData();
                        data.setHouse_id(String.valueOf(houseId));
                        notificationService.saveHouseInfo(1,"房主提交了卖房意向",token,houseId,new Date());
                        commonUserService.login(telphone,verify_code,token,"");
                        data.setToken(token);
                        resp.setCode("1").setMsg("发布成功").setData(data);
                    } else {
                        resp.setCode("0").setMsg("发布失败");
                    }
                }else{
                    resp.setCode("0").setMsg("验证码错误");
                }
            } else {
                resp.setCode("300").setMsg("该手机号码不存在");
            }
        } else if ((ObjectUtils.isEmpty(telphone)&&ObjectUtils.isEmpty(verify_code)) && !ObjectUtils.isEmpty(token)) {

            int houseId = houseInfoService.uploadHouseInfo(little_district, city_name, house_area, expect_price, telphone, token);
            if ( houseId != 0) {
                UploadHouseData data = new UploadHouseData();
                data.setToken(token);
                data.setHouse_id(String.valueOf(houseId));
                notificationService.saveHouseInfo(1,"房主提交了卖房意向",token,houseId,new Date());
                commonUserService.login(telphone,verify_code,token,"");
                resp.setCode("1").setMsg("发布成功").setData(data);
            } else {
                resp.setCode("0").setMsg("发布失败");
            }
        }
        return resp;
    }

    /**
     * 消息列表(5)
     *
     * @param token
     * @return
     */
    @GetMapping(value = "/messagelist")
    public BaseResp messageList(String token) {
        BaseResp resp = new BaseResp();
        CommonUser commonUser = commonUserService.findMine(token);
        if (!ObjectUtils.isEmpty(commonUser)) {
            List<Message> messageList = messageService.findMsgList(token);
            List<MessageData> messageDataList = new ArrayList<>();
            if (messageList.size() > 0) {
                for (Message message : messageList) {
                    if (message.getMsgType() == 1) {
                        List<Notification> notificationList = notificationService.findMyNotic(token);
                        if (notificationList.size() > 0) {
                            for (Notification notification : notificationList) {
                                MessageData data = new MessageData();
                                data.setMsgcontent(notification.getNotifyMsg());
                                DateFormat dateFormat = new SimpleDateFormat("MM/dd");
                                data.setMsgdate(dateFormat.format(notification.getNotifyTime()));
                                data.setMsgid(notification.getId().toString());
                                data.setMsgtype(String.valueOf(1));
                                data.setMsgtitle("通知");
                                messageDataList.add(data);
                            }
                        }
                    } else if (message.getMsgType() == 2) {
                        MessageData data = new MessageData();
                        ChatMessage chatMessage = chatMessageService.findChatMsgById(message.getMsgId());
                        if (chatMessage.getUserType() == 1) {
                            CommonUser commonUser1 = commonUserService.findMineById(chatMessage.getReceiverId());

                            data.setMsgtitle(commonUser1.getNickName());
                            data.setMsgtype(String.valueOf(2));
                            DateFormat dateFormat = new SimpleDateFormat("MM/dd");
                            data.setMsgdate(dateFormat.format(chatMessage.getCreateTime()));
                            data.setMsgcontent(chatMessage.getContent());
                            data.setMsgicon(commonUser1.getAvatarUrl());
                        } else if (chatMessage.getUserType() == 2) {
                            AgentUser agentUser = agentUserService.findAgentById(chatMessage.getSenderId());
                            data.setMsgtitle(agentUser.getUserName());
                            data.setMsgtype(String.valueOf(2));
                            DateFormat dateFormat = new SimpleDateFormat("MM/dd");
                            data.setMsgdate(dateFormat.format(chatMessage.getCreateTime()));
                            data.setMsgcontent(chatMessage.getContent());
                            data.setMsgicon(agentUser.getAvatarUrl());
                        }
                        messageDataList.add(data);
                    }
                }
            }
            resp.setCode("1").setMsg("获取消息列表成功").setData(messageDataList);
        } else {
            resp.setMsg("用户不存在").setCode("0");
        }

        return resp;
    }

    /**
     * 城市列表(6)
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
     * 区域列表(7)
     *
     * @return
     */
    @GetMapping(value = "/arealist")
    public BaseResp areaList(String city_name) {
        BaseResp resp = new BaseResp();
        Cities cities = citiesService.findCityByName(city_name);

//        if(!ObjectUtils.isEmpty(cities)){
//
//        }else{
//
//        }


        AreaListData data = new AreaListData();
        List<AreaListData.DataBean> dataBeanList = new ArrayList<>();

        if (!ObjectUtils.isEmpty(cities)) {
            List<Areas> areasList = areasService.findAreasByCityId(cities.getCityid());
            if (areasList.size() > 0) {
                for (Areas areas : areasList) {
                    getAreaList(dataBeanList, areas);
                }
            }
        } else {
            resp.setMsg("该城市不存在").setData("0");
        }
        resp.setMsg("dasd").setCode("1").setData(dataBeanList);
        return resp;
    }

    private void getAreaList(List<AreaListData.DataBean> dataBeanList, Areas area) {
        AreaListData.DataBean dataBean = new AreaListData.DataBean();
        dataBean.setArea_name(area.getArea());
        List<String> street = new ArrayList<>();
        dataBeanList.add(dataBean);
        List<AreaStreet> areaStreetList = areaStreetService.findStreetListByAreaId(area.getAreaid());
        if (areaStreetList.size() > 0) {
            for (AreaStreet areaStreet : areaStreetList) {
                street.add(areaStreet.getStreetName());
            }
        }
        dataBean.setChild(street);
        dataBeanList.add(dataBean);
    }

    /**
     * 我的房源(9)
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
                List<HouseImg> houseImgList = houseImgService.findHouseImg(houseInfoList.get(i).getId());
                if (houseImgList.size() > 0) {
                    for (int j = 0; j < houseImgList.size(); j++)
                        houseData.setHouseImg(houseImgList.get(j).getImgurl());
                } else {
                    houseData.setHouseImg("http://pic9.nipic.com/20100810/383152_151720000466_2.jpg");
                }

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

                houseData.setSeeNum(houseInfoList.get(i).getSeeNum() == null ? 0:houseInfoList.get(i).getSeeNum());
                houseData.setVisitsNum(houseInfoList.get(i).getVisitNum() == null ? 0:houseInfoList.get(i).getVisitNum());
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
     * 我的(12)
     *
     * @param token
     * @return
     */
    @GetMapping(value = "/mine")
    public BaseResp mine(String token) {
        BaseResp resp = new BaseResp();
        CommonUser user = commonUserService.findMine(token);
        if (!ObjectUtils.isEmpty(user)) {
            MineData data = new MineData();
            data.setNickname(user.getNickName());
            data.setTelphone(user.getUserTelphone());
            data.setHeadimg(user.getAvatarUrl());
            data.setAboutus_version("1.0.0");
            data.setCus_ser_tel("18051661999");
            resp.setMsg("获取我的信息成功").setCode("1").setData(data);
        } else {
            resp.setMsg("没有用户信息").setCode("0");
        }

        return resp;
    }

    /**
     * 市场行情详情页(价格趋势)(13)
     *
     * @param city_name
     * @return
     */
    @GetMapping(value = "market_detail")
    public BaseResp marketDetail(String city_name) throws ParseException {
        BaseResp resp = new BaseResp();
        MarketDetailData data = new MarketDetailData();
        if (citiesService.isOpen(city_name)) {
            Double averPrice = houseInfoService.countAvgCityHousePrice(city_name);
            if (averPrice != null) {
                data.setAverage_price(averPrice.toString());
            } else {
                data.setAverage_price("9000");
            }
            DateFormat dateFormat = new SimpleDateFormat("MM");
            data.setCurrent_month(dateFormat.format(new Date()));
            data.setIncrease("1");
            data.setAverage_price("9000");
            data.setSelling_houses("12456");
            MarketDetailData.PriceTrendBean priceTrendBean = new MarketDetailData.PriceTrendBean();
            String datetime = dateFormat.format(new Date());
            List<String> dategroup = new ArrayList<>();
            List<String> pricegroup = new ArrayList<>();
            for (int i = 12; i >= 0; i--) {
                dategroup.add(datetime);
                pricegroup.add(i+"");
                datetime = DateUtil.subMonth(datetime);
            }
            priceTrendBean.setMonth(dategroup);
            priceTrendBean.setUnitprice(pricegroup);
            data.setPrice_trend(priceTrendBean);
            List<MarketDetailData.AreaReferencePriceBean> beanList = new ArrayList<>();
            List<String> districtList  =  houseInfoService.findDistrictsByCity(city_name);
            if(districtList.size() > 0){
                for(String district:districtList){
                    MarketDetailData.AreaReferencePriceBean bean = new MarketDetailData.AreaReferencePriceBean();
                    bean.setAreaname(district == null?"":district);
                    bean.setAverage_price("1000");
                    bean.setIncrease(1);
                    beanList.add(bean);
                }
            }
            data.setArea_reference_price(beanList);
            resp.setCode("1").setMsg("市场行情").setData(data);
        } else {
            resp.setMsg("该城市未开通服务").setCode("0");
        }
        return resp;
    }

    /**
     * 卖房动态(14)
     *
     * @param token
     * @param house_id
     * @return
     */
    @PostMapping(value = "/sellers")
    public BaseResp sellers(String token, Integer house_id) {
        BaseResp resp = new BaseResp();
        CommonUser commonUser = commonUserService.findMine(token);
        if (!ObjectUtils.isEmpty(commonUser)) {
            HouseInfo houseInfo = houseInfoService.findMyHouseById(house_id);
            if (!ObjectUtils.isEmpty(houseInfo)) {
                SellerData data = new SellerData();
                data.setDistrict(houseInfo.getDistrict() == null ? "":houseInfo.getDistrict());
                SellerData.IspassBean ispassBean = new SellerData.IspassBean();
                ispassBean.setValue(houseInfo.getAssessor().toString());
                if(houseInfo.getAssessor() == 1) {
                    ispassBean.setText("审核通过");
                }else if(houseInfo.getAssessor() == 2){
                    ispassBean.setText("待审核");
                }else if(houseInfo.getAssessor() == 3){
                    ispassBean.setText("审核未通过");
                }
                data.setIspass(ispassBean);
                DateFormat format = new SimpleDateFormat("yyyy");
                if (!ObjectUtils.isEmpty(houseInfo.getBuildingAge()))
                    data.setBuilding_age(format.format(houseInfo.getBuildingAge()));
                data.setHouse_use(houseInfo.getHouseUse() == null ? "":houseInfo.getHouseUse());
                data.setProperty_rights_type(houseInfo.getPropertyRightsType() == null ? "":houseInfo.getPropertyRightsType());
                data.setHouse_title(houseInfo.getHouseTitle() == null ? "":houseInfo.getHouseTitle());
                data.setHouse_style(houseInfo.getHouseLayout() == null ? "":houseInfo.getHouseLayout());
                data.setHouse_id(houseInfo.getId().toString());
                data.setHouse_orientation(houseInfo.getHouseOrientation() == null ? "":houseInfo.getHouseOrientation());
                if (!ObjectUtils.isEmpty(houseInfo.getHouseTotalPrice()))
                    data.setHouse_price(houseInfo.getHouseTotalPrice().toString() == null ? "":houseInfo.getHouseTotalPrice().toString());
                else
                    data.setHouse_price("0");
                data.setHouse_layer(houseInfo.getHouseTier() == null ? "":houseInfo.getHouseTier());
                data.setHouse_area(houseInfo.getHouseArea().toString());
                if (!ObjectUtils.isEmpty(houseInfo.getHouseUnitPrice()))
                    data.setHouse_unit_price(houseInfo.getHouseUnitPrice().toString());
                else
                    data.setHouse_unit_price("0");
                data.setSalestop(String.valueOf(houseInfo.isSaleStop()));
                data.setService_telphone("18051661999");
                List<String> houseImgs = new ArrayList<>();
                List<HouseImg> houseImgList = houseImgService.findHouseImg(house_id);
                if(houseImgList.size() > 0) {
                    for(HouseImg houseImg:houseImgList) {
                        houseImgs.add(houseImg.getImgurl());
                        data.setHouseimgs(houseImgs);
                    }
                }else{
                    houseImgs.add("https://sumaifang.oss-cn-hangzhou.aliyuncs.com/static/images/dynamic_banner_default.jpg");
                    data.setHouseimgs(houseImgs);
                }
                SellerData.CountBean countBean = new SellerData.CountBean();

                countBean.setBrowse_num(houseInfo.getVisitNum() == null ? "0" : houseInfo.getVisitNum().toString());
                countBean.setCall_num(houseInfo.getCallNum() == null ? "0" : houseInfo.getCallNum().toString());
                countBean.setFollow_num(houseInfo.getAttentionNum() == null ? "0" : houseInfo.getAttentionNum().toString());
                countBean.setSee_num(houseInfo.getSeeNum() == null ? "0" : houseInfo.getSeeNum().toString());
                SellerData.CountBean.DataBean dataBean = new SellerData.CountBean.DataBean();
                List<Integer> browseList = new ArrayList<>();
                List<Integer> callList = new ArrayList<>();
                List<Integer> followList = new ArrayList<>();
                List<Integer> seeList = new ArrayList<>();
                for (int i = 0; i < 7; i++) {
                    int browsecount = terraceRecordService.getVisitNums(i);
                    int callcount = terraceRecordService.getCallNums(i);
                    int followcount = terraceRecordService.getFollowNums(i);
                    int seecount = terraceRecordService.getSeeNums(i);
                    browseList.add(browsecount);
                    callList.add(callcount);
                    followList.add(followcount);
                    seeList.add(seecount);
                }
                dataBean.setBrowse(browseList);
                dataBean.setCall(callList);
                dataBean.setFollow(followList);
                dataBean.setSee(seeList);
                countBean.setData(dataBean);
                data.setCount(countBean);
                List<Notification> notificationList = notificationService.findMyNotic(token);
                DateFormat dateFormat = new SimpleDateFormat("MM/dd");
                if(notificationList.size() > 0){
                    List<SellerData.NewsBean> beanList = new ArrayList<>();
                    for(Notification notification : notificationList){
                        SellerData.NewsBean bean = new SellerData.NewsBean();
                        bean.setTime(dateFormat.format(notification.getNotifyTime()));
                        bean.setContent(notification.getSummarizeMsg());
                        beanList.add(bean);
                    }
                    data.setNews(beanList);
                }

                resp.setMsg("获取卖房动态成功").setCode("1").setData(data);
            } else {
                resp.setCode("1").setMsg("房屋信息不存在").setData(null);
            }
        } else {
            resp.setCode("0").setMsg("用户未登陆");
        }
        return resp;
    }


    /**
     * 获取小区列表(1)
     *
     * @param cityname
     * @param districtname
     * @return
     */
    @GetMapping("/getdistrictlist")
    public BaseResp getDistrictList(String cityname, String districtname) {
        BaseResp resp = new BaseResp();
        Cities cities = citiesService.findCityByName(cityname);
        DistrictData data = new DistrictData();
        if (!ObjectUtils.isEmpty(cities)) {
            List<Biotope> biotopeList = biotopeService.findBiotopList(cities.getCityid(), districtname);

            List<String> distri = new ArrayList<>();
            if (biotopeList.size() > 0) {

                for (Biotope biotope : biotopeList) {
                    distri.add(biotope.getName());
                }
                data.setDistrict_name(distri);
            } else {
                data.setDistrict_name(new ArrayList<>());
            }
            resp.setCode("1").setMsg("获取区域列表成功").setData(data);
        } else {
            data.setDistrict_name(new ArrayList<>());
            resp.setCode("1").setMsg("获取失败").setData(data);
        }
        return resp;
    }

    /**
     * 房屋估价(2)
     *
     * @return
     */
    @PostMapping(value = "housevaluation")
    public BaseResp houseValuation(String community, String house_type, String toward, String floor, Double area) {
        BaseResp resp = new BaseResp();
        Integer id = houseInfoService.houseValuation(community, house_type, toward, floor, area);
        if (id != 0) {
            HouseValuationData data = new HouseValuationData();
            data.setHouse_id(String.valueOf(id));
            resp.setMsg("房屋评价成功").setCode("1").setData(data);
        } else {
            resp.setMsg("房屋评价失败").setCode("0");
        }
        return resp;
    }

    /**
     * 发送消息(3)
     *
     * @return
     */
    @PostMapping(value = "sendmsg")
    public BaseResp sendMsg() {
        BaseResp resp = new BaseResp();
        return resp;
    }

    /**
     * 消息详情列表(4)
     *
     * @return
     */
    @GetMapping(value = "msg_detail")
    public BaseResp msgDetail() {
        BaseResp resp = new BaseResp();
        return resp;
    }


    /**
     *
     */

    /**
     * 房屋详情(15)
     *
     * @param house_id
     * @return
     */
    @PostMapping(value = "/house_detail")
    public BaseResp houseDetail(Integer house_id) {
        BaseResp resp = new BaseResp();
        HouseDetailData data = new HouseDetailData();
        HouseInfo houseInfo = houseInfoService.findMyHouseById(house_id);
        if (!ObjectUtils.isEmpty(houseInfo)) {

            //获取地址坐标
            RestTemplate restTemplate = new RestTemplate();
            Map<String, String> map = new HashMap<>();
            map.put("address", houseInfo.getHouseDetailAddress());
            map.put("key", TencentProperties.KEY);
            GeoCoderResp geoCoderResp = restTemplate.getForObject(TencentUrl.GEOCODERURL, GeoCoderResp.class, map);
            if (geoCoderResp.getStatus() == 0) {
                data.setLatitude(String.valueOf(geoCoderResp.getResult().getLocation().getLat()));
                data.setLongitude(String.valueOf(geoCoderResp.getResult().getLocation().getLng()));
            }
            PriceTimeMachineData priceTimeMachineData = housePriceService.findHouseData(house_id);
            data.setPrice_time_machine(priceTimeMachineData);
            List<SameSellHouseData> sameSellHouseDataList = houseInfoService.findSameSellHouse(house_id);
            data.setSamesellhouse(sameSellHouseDataList);

            List<SameDealHouseData> sameDealHouseDataList = houseInfoService.findSameDealHouse(house_id);
            data.setSamedealhouse(sameDealHouseDataList);
        } else {
            resp.setCode("0").setMsg("没有房屋信息");
        }
        return resp;
    }

    /**
     * 最近成交(16)
     *
     * @return
     */
    @GetMapping(value = "recently_deal_house")
    public BaseResp recentlyDealHouse(String city_name, String street_name, Double minprice, Double maxprice, Integer choose_price, Integer choose_house_style, Integer choose_house_area, String district_name) {
        BaseResp resp = new BaseResp();
        List<HouseInfo> houseInfoList = houseInfoService.findHouseInfoOnCity(city_name);
        List<RecentDealHouseData> dataList = new ArrayList<>();
        if (houseInfoList.size() > 0) {

            DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");
            for (HouseInfo houseInfo : houseInfoList) {
                RecentDealHouseData data = new RecentDealHouseData();
                if (!ObjectUtils.isEmpty(houseInfo.getWorkOffTime()))
                    data.setDeal_date(dateFormat.format(houseInfo.getWorkOffTime()));
                data.setHouse_title(houseInfo.getHouseTitle());
                data.setHouse_title_layout(houseInfo.getHouseTitle() + houseInfo.getHouseLayout());
                data.setHouse_orientation(houseInfo.getHouseOrientation());
                data.setHouse_layer(houseInfo.getHouseTier());
                data.setHouse_id(houseInfo.getId().toString());
                data.setHouse_community(houseInfo.getDistrict());
                data.setHouse_deal_days(String.valueOf(DateUtil.getDiscrepantDays(houseInfo.getWorkOffTime(), new Date())));
                data.setHouse_layout(houseInfo.getHouseLayout());
                if (!ObjectUtils.isEmpty(houseInfo.getHouseTotalPrice()))
                    data.setHouse_price(houseInfo.getHouseTotalPrice().toString());
                else data.setHouse_price("0");
                List<HouseImg> imgs = houseImgService.findHouseImg(houseInfo.getId());
                if (imgs.size() > 0) {
                    data.setHouse_img(imgs.get(0).getImgurl() == null ? "http://www.jjg630.com/uploads/tem_file/5d2700bc5c76e.png" : imgs.get(0).getImgurl());
                } else {
                    data.setHouse_img("http://www.jjg630.com/uploads/tem_file/5d2700bc5c76e.png");
                }
                data.setHouse_label(houseInfo.getHouseLabel() == null ? "高大" : houseInfo.getHouseLabel());
                data.setHouse_unit_price(houseInfo.getHouseUnitPrice() == null ? "120" : houseInfo.getHouseUnitPrice().toString());
                data.setHouse_area(houseInfo.getHouseArea().toString());
                dataList.add(data);
            }
            resp.setCode("1").setMsg("获取最近成交房屋信息成功").setData(dataList);
        } else {
            resp.setCode("1").setData(new ArrayList<>()).setMsg("没有数据");
        }
        return resp;
    }

    /**
     * 获取验证码(10)
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
     * 手机绑定(11)
     *
     * @param telphone
     * @param verifyCode
     * @return
     */
    @PostMapping(value = "/login")
    public BaseResp login(String telphone, String verifyCode, String token) {
        BaseResp resp = new BaseResp();
        try {
            DateFormat format = new SimpleDateFormat("FMyyyyMMddmmss");
            String nickname = format.format(new Date());
            if (commonUserService.login(telphone, verifyCode, token, nickname)) {
                TokenData data = new TokenData();
                data.setToken(token);
                resp.setCode("1").setMsg("绑定成功").setData(data);
            } else {
                resp.setCode("0").setMsg("绑定失败");
            }
        } catch (Exception e) {
            resp.setMsg("绑定失败").setCode("2");
        }
        return resp;
    }

    @PostMapping(value = "/modify_nickname")
    public BaseResp modifyNickName(String token, String newname) {
        BaseResp resp = new BaseResp();
        CommonUser user = commonUserService.findMine(token);
        if (!ObjectUtils.isEmpty(user)) {
            if (commonUserService.modifyNickName(newname, user.getId())) {
                resp.setCode("1").setMsg("修改昵称成功");
            } else {
                resp.setCode("0").setMsg("修改昵称失败");
            }

        } else {
            resp.setCode("0").setMsg("用户未登陆");
        }
        return resp;
    }

    /**
     * 微信登陆(19)
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
                CommonUser commonUser = commonUserService.findUserByOpenId(errorResp.getOpenid());
                boolean islogin = commonUserService.isLogin(errorResp.getOpenid());
                boolean isbind = commonUserService.isBind(errorResp.getOpenid());
                String token = TokenUtil.createJwtToken(errorResp.getOpenid());
                if (!ObjectUtils.isEmpty(commonUser)) {
                    if (isbind) {
                        data.setIsbind("1");
                    } else {
                        data.setIsbind("0");
                    }
                    if (islogin) {
                        data.setToken(commonUser.getToken());
                        resp.setMsg("登陆成功").setCode("1").setData(data);
                    }
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
                    data.setToken(token);
                    data.setIsbind("0");
                    resp.setMsg("授权成功").setCode("1").setData(data);
                }

            } else {
                resp.setMsg(errorResp.getErrmsg()).setCode(String.valueOf(errorResp.getErrcode())).setData(data);
            }
        }
        return resp;
    }


}
