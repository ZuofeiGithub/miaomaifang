package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.repository.HouseMarketProfileDao;
import com.huiketong.sumaifang.service.HouseMarketProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HouseMarketProfileServiceImpl implements HouseMarketProfileService {
    @Autowired
    HouseMarketProfileDao houseMarketProfileDao;
}
