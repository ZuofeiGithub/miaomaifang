package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.data.SameDealHouseData;
import com.huiketong.sumaifang.data.SameSellHouseData;
import com.huiketong.sumaifang.domain.Cities;
import com.huiketong.sumaifang.domain.CommonUser;
import com.huiketong.sumaifang.domain.HouseImg;
import com.huiketong.sumaifang.domain.HouseInfo;
import com.huiketong.sumaifang.repository.*;
import com.huiketong.sumaifang.service.BiotopeService;
import com.huiketong.sumaifang.service.HouseInfoService;
import com.huiketong.sumaifang.utils.TokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class HouseInfoServiceImpl implements HouseInfoService {

    @Autowired
    HouseInfoDao houseInfoDao;
    @Autowired
    CommonUserDao commonUserDao;
    @Autowired
    HouseImgDao houseImgDao;
    @Autowired
    CitiesDao citiesDao;
    @Autowired
    BiotopeService biotopeService;

    @Override
    public boolean uploadHouseInfo(String little_district,String city_name, Double house_area, Double expect_price,String telphone,String token) {

        Cities cities = citiesDao.findCitiesByCityName(city_name);
        if(!ObjectUtils.isEmpty(cities)){
            biotopeService.saveBiotopeInfo(cities.getCityid(),little_district);
        }
        HouseInfo houseInfo = new HouseInfo();
        houseInfo.setDistrict(little_district);
        houseInfo.setHouseArea(house_area);
        houseInfo.setHouseCity(city_name);
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

    /**
     * 查找同小区在售房源
     * @param house_id
     * @return
     */
    @Override
    public List<SameSellHouseData> findSameSellHouse(Integer house_id) {
        List<SameSellHouseData> sellHouseDataList = new ArrayList<>();
        HouseInfo houseInfo =  houseInfoDao.findHouseInfoById(house_id);
        if(!ObjectUtils.isEmpty(houseInfo)){
            List<HouseInfo> houseInfoList = houseInfoDao.findSameSellHouse(houseInfo.getDistrict());
            if(houseInfoList.size() > 0){
                for(HouseInfo houseInfo1:houseInfoList){
                    SameSellHouseData data = new SameSellHouseData();
                    data.setHouse_area(houseInfo1.getHouseArea().toString());
                    data.setHouse_id(houseInfo1.getId());
                    List<HouseImg> houseImgList = houseImgDao.findHouseImgsByHouseId(house_id);
                    if(houseImgList.size() > 0) {
                        data.setHouse_img(houseImgList.get(0).getImgurl());
                    }
                    data.setHouse_layout(houseInfo1.getHouseLayout());
                    data.setHouse_orientation(houseInfo1.getHouseOrientation());
                    data.setHouse_total_price(houseInfo1.getHouseTotalPrice().toString());
                    data.setHouse_unit_price(houseInfo1.getHouseUnitPrice().toString());
                    sellHouseDataList.add(data);
                }
            }
        }
        return sellHouseDataList;
    }

    @Override
    public List<SameDealHouseData> findSameDealHouse(Integer house_id) {
        List<SameDealHouseData> sellHouseDataList = new ArrayList<>();
        HouseInfo houseInfo =  houseInfoDao.findHouseInfoById(house_id);
        if(!ObjectUtils.isEmpty(houseInfo)){
            List<HouseInfo> houseInfoList = houseInfoDao.findSameDealHouse(houseInfo.getDistrict());
            if(houseInfoList.size() > 0){
                for(HouseInfo houseInfo1:houseInfoList){
                    SameDealHouseData data = new SameDealHouseData();
                    data.setHouse_area(houseInfo1.getHouseArea().toString());
                    data.setHouse_id(houseInfo1.getId());
                    List<HouseImg> houseImgList = houseImgDao.findHouseImgsByHouseId(house_id);
                    if(houseImgList.size() > 0) {
                        data.setHouse_img(houseImgList.get(0).getImgurl());
                    }
                    data.setHouse_layout(houseInfo1.getHouseLayout());
                    data.setHouse_orientation(houseInfo1.getHouseOrientation());
                    data.setHouse_total_price(houseInfo1.getHouseTotalPrice().toString());
                    data.setHouse_unit_price(houseInfo1.getHouseUnitPrice().toString());
                    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM");
                    data.setWork_off_time(dateFormat.format(houseInfo1.getWorkOffTime()));
                    sellHouseDataList.add(data);
                }
            }
        }
        return sellHouseDataList;
    }

    @Override
    public HouseInfo findHouseByDistrict(String district) {
        return houseInfoDao.findHouseInfoByDistrict(district);
    }

    @Override
    public boolean stopSale(Integer houseId) {
        try {

           HouseInfo houseInfo = houseInfoDao.findHouseInfoById(houseId);
           if(!ObjectUtils.isEmpty(houseInfo)) {
               houseInfoDao.updateSaleStopById(houseId);
               return true;
           }else{
               return false;
           }
        }catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean orderTable(Integer houseId, String seetime) {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date time = dateFormat.parse(seetime);
            HouseInfo houseInfo = houseInfoDao.findHouseInfoById(houseId);
            if(!ObjectUtils.isEmpty(houseInfo)) {
                houseInfoDao.updateSeeTimeById(time, houseId);
                return true;
            }else {
                return false;
            }
        } catch (ParseException e) {
            return false;
        }

    }
}
