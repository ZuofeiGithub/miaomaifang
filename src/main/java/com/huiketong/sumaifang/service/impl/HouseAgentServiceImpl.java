package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.data.CallerData;
import com.huiketong.sumaifang.data.TakeLookData;
import com.huiketong.sumaifang.domain.AgentUser;
import com.huiketong.sumaifang.domain.HouseAgent;
import com.huiketong.sumaifang.domain.HouseInfo;
import com.huiketong.sumaifang.domain.Intermediary;
import com.huiketong.sumaifang.repository.AgentUserDao;
import com.huiketong.sumaifang.repository.HouseAgentDao;
import com.huiketong.sumaifang.repository.HouseInfoDao;
import com.huiketong.sumaifang.service.HouseAgentService;
import com.huiketong.sumaifang.service.IntermediaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class HouseAgentServiceImpl implements HouseAgentService {
    @Autowired
    HouseAgentDao houseAgentDao;
    @Autowired
    AgentUserDao agentUserDao;
    @Autowired
    HouseInfoDao houseInfoDao;
    @Autowired
    IntermediaryService intermediaryService;
    @Override
    public List<CallerData> findAgentsCaller(Integer houseId,Integer page,Integer limit) {
        List<HouseAgent> houseAgentList = houseAgentDao.findHouseAgentsByHouseIdAndLinkTypeLimit(houseId,2,(page-1)*limit,limit);
        if(houseAgentList.size() > 0){
           List<CallerData> dataList = new ArrayList<>();
           for (HouseAgent houseAgent :houseAgentList){
                AgentUser agentUser = agentUserDao.findAgentUserById(houseAgent.getAgentId());
               HouseInfo houseInfo = houseInfoDao.findHouseInfoById(houseAgent.getHouseId());
                if(!ObjectUtils.isEmpty(agentUser)&&!ObjectUtils.isEmpty(houseInfo)) {
                    CallerData data = new CallerData();
                    data.setAgent_icon(agentUser.getAvatarUrl());
                    data.setAgent_name(agentUser.getUserName());
                    data.setCall_num(houseInfo.getCallNum().toString());
                    Intermediary intermediary = intermediaryService.findIntermediaryById(agentUser.getId());
                    if (ObjectUtils.isEmpty(intermediary)) {
                        data.setIntermediary(intermediary.getName());
                    } else {
                        data.setIntermediary("不属于中介机构");
                    }
                    if (agentUser.getCertification() == 1) {
                        data.setIsapprove("已认证");
                    } else if (agentUser.getCertification() == 0) {
                        data.setIsapprove("未认证");
                    }
                    dataList.add(data);
                }
           }
           return dataList;
        }
        return new ArrayList<>();
    }

    @Override
    public List<TakeLookData> findAgentsLook(Integer houseId,Integer page,Integer limit) {
        List<HouseAgent> houseAgentList = houseAgentDao.findHouseAgentsByHouseIdAndLinkTypeLimit(houseId,1,(page-1)*limit,limit);
        if(houseAgentList.size() > 0){
            List<TakeLookData> dataList = new ArrayList<>();
            for(HouseAgent houseAgent:houseAgentList){
                AgentUser agentUser = agentUserDao.findAgentUserById(houseAgent.getAgentId());
                HouseInfo houseInfo = houseInfoDao.findHouseInfoById(houseAgent.getHouseId());
                if(!ObjectUtils.isEmpty(agentUser)&&!ObjectUtils.isEmpty(houseInfo)){
                    TakeLookData data = new TakeLookData();
                    data.setAgent_icon(agentUser.getAvatarUrl());
                    data.setAgent_name(agentUser.getUserName());
                    Intermediary intermediary = intermediaryService.findIntermediaryById(agentUser.getId());
                    if(!ObjectUtils.isEmpty(intermediary)) {
                        data.setIntermediary(intermediary.getName());
                    }else{
                        data.setIntermediary("不属于中介机构");
                    }
                    if(agentUser.getCertification() == 1){
                        data.setIsapprove("已认证");
                    }else{
                        data.setIsapprove("未认证");
                    }
                    data.setSee_num(houseInfo.getSeeNum().toString());
                    dataList.add(data);
                    return dataList;
                }
            }
        }
        return new ArrayList<>();
    }
}
