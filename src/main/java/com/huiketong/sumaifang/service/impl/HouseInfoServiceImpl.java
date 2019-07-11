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
        houseInfo.setExpectPrice(expect_price);
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

    @Override
    public List<HouseInfo> findMyHouseList(String token) {
        return houseInfoDao.findHouseInfosByToken(token);
    }

    @Override
    public List<HouseInfo> findLessThanForty(){
        return houseInfoDao.findHouseLessThanForty();
    }
}
