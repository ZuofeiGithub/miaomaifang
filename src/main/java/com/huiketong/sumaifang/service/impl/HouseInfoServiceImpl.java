package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.domain.HouseInfo;
import com.huiketong.sumaifang.repository.HouseInfoDao;
import com.huiketong.sumaifang.service.HouseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class HouseInfoServiceImpl implements HouseInfoService {

    @Autowired
    HouseInfoDao houseInfoDao;

    @Override
    public boolean uploadHouseInfo(String little_district, Double house_area, Double expect_price,String token) {
        HouseInfo houseInfo = new HouseInfo();
        houseInfo.setHouseAddress(little_district);
        houseInfo.setHouseArea(house_area);
        houseInfo.setHouseTotalPrice(expect_price);
        houseInfo.setHouseUnitPrice((expect_price/house_area));
        houseInfo.setToken(token);
        houseInfo.setCreateTime(new Date());
        houseInfo.setAssessor(false);
        try {
            houseInfoDao.save(houseInfo);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public List<HouseInfo> getHouseInfoList() {
        return houseInfoDao.findAll();
    }
}
