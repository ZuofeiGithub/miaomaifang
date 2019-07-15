package com.huiketong.sumaifang.service;

import com.huiketong.sumaifang.data.CallerData;
import com.huiketong.sumaifang.data.TakeLookData;

import java.util.List;

public interface HouseAgentService {

    List<CallerData> findAgentsCaller(Integer houseId,Integer page,Integer limit);

    List<TakeLookData> findAgentsLook(Integer houseId,Integer page,Integer limit);
}
