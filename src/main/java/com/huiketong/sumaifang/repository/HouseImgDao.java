package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.HouseImg;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HouseImgDao extends JpaRepository<HouseImg, Integer> {

    HouseImg findHouseImgByHouseId(Integer houseId);
}
