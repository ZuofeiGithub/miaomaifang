package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.HouseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseInfoDao extends JpaRepository<HouseInfo,Integer> {
    @Override
    <S extends HouseInfo> S save(S s);

    @Override
    List<HouseInfo> findAll();

    List<HouseInfo> findHouseInfosByToken(String token);

    @Query(value = "select *  from house_info where house_total_price between 0 and 40",nativeQuery = true)
    List<HouseInfo> findHouseLessThanForty();
}
