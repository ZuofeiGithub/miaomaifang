package com.huiketong.sumaifang.service;

import com.huiketong.sumaifang.domain.Areas;

import java.util.List;

public interface AreasService {
    List<Areas> findAreasByCityId(String cityId);
}
