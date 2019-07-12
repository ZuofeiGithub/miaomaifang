package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.HouseImg;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HouseImgDao extends JpaRepository<HouseImg, Integer> {

    List<HouseImg> findHouseImgsByHouseId(Integer houseId);
}
