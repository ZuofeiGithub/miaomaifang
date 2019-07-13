package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.domain.Areas;
import com.huiketong.sumaifang.repository.AreasDao;
import com.huiketong.sumaifang.service.AreasService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AreasServiceImpl implements AreasService {
    @Autowired
    AreasDao areasDao;
    @Override
    public List<Areas> findAreasByCityId(String cityId) {
        return areasDao.findAllByCityid(cityId);
    }
}
