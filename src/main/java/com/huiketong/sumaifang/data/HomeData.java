package com.huiketong.sumaifang.data;

import lombok.Data;

import java.util.List;

/**
 * 主页信息
 */
@Data
public class HomeData {

    HomeCityData cityinfo ;
    List<BannerData> bannerlist;


    List<HeadlineNewsData> tipslist;

    MarketData market;
    List<OrganizationData> orglist;
    String buyer_num;
}
