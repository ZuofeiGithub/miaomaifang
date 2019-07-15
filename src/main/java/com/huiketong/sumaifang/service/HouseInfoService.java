package com.huiketong.sumaifang.service;

import com.huiketong.sumaifang.data.SameDealHouseData;
import com.huiketong.sumaifang.data.SameSellHouseData;
import com.huiketong.sumaifang.domain.HouseInfo;

import java.util.List;

public interface HouseInfoService {
    boolean uploadHouseInfo(String little_district,String city_name,Double house_area,Double expect_price,String telphone,String token);

    List<HouseInfo> getHouseInfoList();

    List<HouseInfo> findMyHouseList(String token,Integer page,Integer limit);

    List<HouseInfo> findLessThanForty();

    HouseInfo findMyHouseById(Integer house_id);

    Double getAveragePrice();

    List<SameSellHouseData> findSameSellHouse(Integer house_id);

    List<SameDealHouseData> findSameDealHouse(Integer house_id);

    HouseInfo findHouseByDistrict(String district);

    boolean stopSale(Integer houseId);

    boolean orderTable(Integer houseId,String seetime);

}
