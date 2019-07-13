package com.huiketong.sumaifang.service;

import com.huiketong.sumaifang.data.PriceTimeMachineData;

import java.util.List;

public interface HousePriceService {
    PriceTimeMachineData findHouseData(Integer houseId);
}
