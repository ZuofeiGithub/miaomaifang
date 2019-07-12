package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.domain.HouseImg;
import com.huiketong.sumaifang.repository.HouseImgDao;
import com.huiketong.sumaifang.service.HouseImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HouseImgServiceImpl implements HouseImgService {
    @Autowired
    HouseImgDao houseImgDao;

    @Override
    public List<HouseImg> findHouseImg(Integer house_id) {
        return houseImgDao.findHouseImgsByHouseId(house_id);
    }
}
