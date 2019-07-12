package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.domain.CommonUser;
import com.huiketong.sumaifang.domain.HouseInfo;
import com.huiketong.sumaifang.repository.CommonUserDao;
import com.huiketong.sumaifang.repository.HouseInfoDao;
import com.huiketong.sumaifang.service.HouseInfoService;
import com.huiketong.sumaifang.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Date;
import java.util.List;

@Service
public class HouseInfoServiceImpl implements HouseInfoService {

    @Autowired
    HouseInfoDao houseInfoDao;
    @Autowired
    CommonUserDao commonUserDao;

    @Override
    public boolean uploadHouseInfo(String little_district, Double house_area, Double expect_price,String telphone,String token) {
        HouseInfo houseInfo = new HouseInfo();
        houseInfo.setHouseAddress(little_district);
        houseInfo.setHouseArea(house_area);
        houseInfo.setExpectPrice(expect_price);
        if(!ObjectUtils.isEmpty(token)) {
            houseInfo.setToken(token);
        }else{
            CommonUser commonUser = commonUserDao.findCommonUserByUserTelphone(telphone);
            if(!ObjectUtils.isEmpty(commonUser)) {
                houseInfo.setToken(TokenUtil.createJwtToken(commonUser.getOpenid()));
            }
        }
        houseInfo.setCreateTime(new Date());
        houseInfo.setAssessor(2);
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
    public List<HouseInfo> findMyHouseList(String token,Integer page,Integer limit) {
        return houseInfoDao.findHouseInfosByTokenLimit(token,page,limit);
    }

    @Override
    public HouseInfo findMyHouseById(Integer house_id) {
        return houseInfoDao.findHouseInfoById(house_id);
    }

    @Override
    public List<HouseInfo> findLessThanForty(){
        return houseInfoDao.findHouseLessThanForty();
    }

    @Override
    public Double getAveragePrice() {
        return houseInfoDao.getAveragePrice();
    }
}
