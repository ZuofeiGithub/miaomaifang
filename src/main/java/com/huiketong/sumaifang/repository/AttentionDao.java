package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.Attention;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Author: ￣左飞￣
 * @Date: 2019/7/20 21:48
 * @Version 1.0
 */
@Repository
public interface AttentionDao extends JpaRepository<Attention,Integer> {
    Attention findAttentionByAgentIdAndHouseId(Integer agentId,Integer houseId);

    List<Attention> findAttentionsByAgentIdAndIsatten(Integer ageentId,Integer isatten);
}
