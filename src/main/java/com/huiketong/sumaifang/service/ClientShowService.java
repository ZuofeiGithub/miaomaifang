package com.huiketong.sumaifang.service;

import com.huiketong.sumaifang.data.MarketData;

public interface ClientShowService {
    MarketData findMarketByCity(String city);
}
