package com.huiketong.sumaifang.data;

import lombok.Data;

import java.util.List;

/**
 * 主页信息
 */
@Data
public class HomeData {

    CityData cityinfo ;
    List<BannerData> bannerlist;

    List<MyHouseData> myhouselist;

    List<HeadlineNewsData> tipslist;

    MarketData market;
    List<OrganizationData> orglist;
}
