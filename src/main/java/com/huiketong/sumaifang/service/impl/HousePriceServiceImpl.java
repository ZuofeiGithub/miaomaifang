package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.data.PriceTimeMachineData;
import com.huiketong.sumaifang.domain.HousePrice;
import com.huiketong.sumaifang.repository.HousePriceDao;
import com.huiketong.sumaifang.service.HousePriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class HousePriceServiceImpl implements HousePriceService {
    @Autowired
    HousePriceDao housePriceDao;


    @Override
    public PriceTimeMachineData findHouseData(Integer houseId) {
        List<HousePrice> housePriceList = housePriceDao.findHousePricesByHouseId(houseId);
        List<PriceTimeMachineData.MessageBean> beanList = new ArrayList<>();
        List<String> timelist = new ArrayList<>();
        List<String> pricelist = new ArrayList<>();
        PriceTimeMachineData data = new PriceTimeMachineData();
        if(housePriceList.size() > 0){
            for(HousePrice housePrice:housePriceList){


                DateFormat dateFormat = new SimpleDateFormat("MM/dd");
                if(!ObjectUtils.isEmpty(housePrice.getOfferTime()))
                timelist.add(dateFormat.format(housePrice.getOfferTime()));


                pricelist.add(housePrice.getOfferPrice() == null ? "0":housePrice.getOfferPrice().toString());
                data.setDatetimelist(timelist);
                data.setPricelist(pricelist);
                PriceTimeMachineData.MessageBean bean = new PriceTimeMachineData.MessageBean();
                bean.setMsgtime(dateFormat.format(housePrice.getOfferTime()));
                if(housePrice.getOfferType() == 1) {
                    bean.setMsg("报价" + housePrice.getOfferPrice() == null ? "0" :housePrice.getOfferPrice().toString() + "万元");
                }else if(housePrice.getOfferType() == 2){
                    bean.setMsg("调价"+housePrice.getOfferPrice() == null ? "0":housePrice.getOfferPrice().toString()+"万元");
                }
                beanList.add(bean);
                data.setMessages(beanList);
            }
        }
        return data;
    }
}
