package com.huiketong.sumaifang.service;

import com.huiketong.sumaifang.domain.HouseImg;

import java.util.List;

public interface HouseImgService {
    List<HouseImg> findHouseImg(Integer house_id);
}
