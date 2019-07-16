package com.huiketong.sumaifang.service;

import com.huiketong.sumaifang.domain.AreaStreet;

import java.util.List;

public interface AreaStreetService {

    List<AreaStreet> findStreetListByAreaId(String areaId);
}
