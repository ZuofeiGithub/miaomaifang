package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.data.MarketData;
import com.huiketong.sumaifang.domain.ClientShow;
import com.huiketong.sumaifang.repository.ClientShowDao;
import com.huiketong.sumaifang.service.ClientShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

@Service
public class ClientShowServiceImpl implements ClientShowService {
    @Autowired
    ClientShowDao clientShowDao;
    @Override
    public MarketData findMarketByCity(String city) {
        ClientShow clientShow = clientShowDao.findClientShowByCityName(city);
        MarketData data = new MarketData();
        if(!ObjectUtils.isEmpty(clientShow)){

            data.setAgentNum(clientShow.getAgentNum().toString());
            data.setAveragePrice(clientShow.getAveragePrice().toString());
            data.setAverageWorkOffDays(clientShow.getSellDaysNum().toString());
            if(clientShow.getUpAndDown() == 1) {
                data.setIncrease(clientShow.getRise() + "%");
            }else if(clientShow.getUpAndDown() == 2){
                data.setIncrease("-"+clientShow.getRise()+"%");
            }else{
                data.setIncrease("0%");
            }
            data.setClientNum(clientShow.getBuyerNum().toString());
            data.setIntermediaryNum(clientShow.getIntermediaryAgentNum().toString());
        }
        return data;
    }
}
