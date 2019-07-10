package com.huiketong.sumaifang.service;

import com.huiketong.sumaifang.domain.HouseInfo;

import java.util.List;

public interface HouseInfoService {
    boolean uploadHouseInfo(String little_district,Double house_area,Double expect_price,String token);

    List<HouseInfo> getHouseInfoList();
}
