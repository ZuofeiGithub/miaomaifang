package com.huiketong.sumaifang.service;

import com.huiketong.sumaifang.data.HeadlineNewsData;

import java.util.List;

public interface HeadlineService {
    List<HeadlineNewsData> findHeadlineByCity(String city);
}
