package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.domain.AgentUser;
import com.huiketong.sumaifang.domain.Attention;
import com.huiketong.sumaifang.domain.HouseInfo;
import com.huiketong.sumaifang.repository.AttentionDao;
import com.huiketong.sumaifang.service.AgentUserService;
import com.huiketong.sumaifang.service.AttentionService;
import com.huiketong.sumaifang.service.HouseInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: ￣左飞￣
 * @Date: 2019/7/20 21:49
 * @Version 1.0
 */
@Service
public class AttentionServiceImpl implements AttentionService {
    @Autowired
    AttentionDao attentionDao;
    @Autowired
    AgentUserService agentUserService;
    @Autowired
    HouseInfoService houseInfoService;

    @Override
    public Integer attenHouse(Integer houseId, String token) {
        AgentUser agentUser = agentUserService.findbindUserByToken(token);
        HouseInfo houseInfo = houseInfoService.findMyHouseById(houseId);
        if(!ObjectUtils.isEmpty(agentUser)){
            if(!ObjectUtils.isEmpty(houseInfo)){
                Attention existAttention = attentionDao.findAttentionByAgentIdAndHouseId(agentUser.getId(),houseInfo.getId());
                if(!ObjectUtils.isEmpty(existAttention)) {
                    Attention attention = new Attention();
                    attention.setAgentId(agentUser.getId());
                    attention.setHouseId(houseId);
                    attention.setIsatten(1); //关注
                    attention.setAttenTime(new Date());
                    try {
                        attentionDao.save(attention);
                        return 0;
                    } catch (Exception e) {
                        return 1;
                    }
                }else{
                    return 2;
                }

            }else{
                return 3;
            }
        }else{
            return 4;
        }
    }

    @Override
    public List<Attention> findAttentionList(String token) {
         AgentUser agentUser =  agentUserService.findbindUserByToken(token);
         if(!ObjectUtils.isEmpty(agentUser)){
             return attentionDao.findAttentionsByAgentIdAndIsatten(agentUser.getId(),1);
         }else {
             return new ArrayList<>();
         }
    }
}
