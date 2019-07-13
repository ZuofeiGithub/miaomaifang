package com.huiketong.sumaifang.service;

import com.huiketong.sumaifang.domain.Cities;

import java.util.List;

public interface CitiesService {

    /**
     * 该城市是否开通业务
     * @param city
     * @return
     */
    boolean isOpen(String city);

    List<String> findOpenCities();

    Cities findCityByName(String cityname);

}
