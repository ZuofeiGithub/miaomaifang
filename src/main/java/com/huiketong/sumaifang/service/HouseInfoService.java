package com.huiketong.sumaifang.service;

import com.huiketong.sumaifang.domain.HouseInfo;

import java.util.List;

public interface HouseInfoService {
    boolean uploadHouseInfo(String little_district,Double house_area,Double expect_price,String telphone,String token);

    List<HouseInfo> getHouseInfoList();

    List<HouseInfo> findMyHouseList(String token,Integer page,Integer limit);

    List<HouseInfo> findLessThanForty();

    HouseInfo findMyHouseById(Integer house_id);

    Double getAveragePrice();

}
