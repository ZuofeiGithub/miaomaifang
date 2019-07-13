package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.HouseMarketProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseMarketProfileDao extends JpaRepository<HouseMarketProfile,Integer> {
}
