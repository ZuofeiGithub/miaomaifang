package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.domain.AreaStreet;
import com.huiketong.sumaifang.repository.AreaStreetDao;
import com.huiketong.sumaifang.service.AreaStreetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AreaStreetServiceImpl implements AreaStreetService {
    @Autowired
    AreaStreetDao areaStreetDao;


    @Override
    public List<AreaStreet> findStreetListByAreaId(String areaId) {
        return areaStreetDao.findAreaStreetsByAreaId(areaId);
    }
}
