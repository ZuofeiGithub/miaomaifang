package com.huiketong.sumaifang.service;

import com.huiketong.sumaifang.domain.AgentUser;

public interface AgentUserService {
    //用户注册
    boolean register(String user_name, String user_phone, String uuid, Integer user_type, String city_name, String company_info);

    long getAgentNum();

    AgentUser findAgentById(Integer id);
}
