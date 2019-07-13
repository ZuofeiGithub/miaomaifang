package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.HousePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HousePriceDao extends JpaRepository<HousePrice,Integer> {
    List<HousePrice>  findHousePricesByHouseId(Integer houseId);
}
