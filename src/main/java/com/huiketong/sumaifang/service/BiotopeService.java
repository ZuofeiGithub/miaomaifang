package com.huiketong.sumaifang.service;

import com.huiketong.sumaifang.domain.Biotope;

import java.util.List;

public interface BiotopeService {
    boolean saveBiotopeInfo(String areaId,String name);

    List<Biotope>  findBiotopList(String cityId,String name);
}
