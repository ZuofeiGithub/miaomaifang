package com.huiketong.sumaifang.service;

import com.huiketong.sumaifang.domain.Attention;

import java.util.List;

/**
 * @Author: ￣左飞￣
 * @Date: 2019/7/20 21:48
 * @Version 1.0
 */
public interface AttentionService {
    Integer attenHouse(Integer houseId, String token);

    List<Attention> findAttentionList(String token);

    boolean isAttention(Integer house_id, String token);
}
