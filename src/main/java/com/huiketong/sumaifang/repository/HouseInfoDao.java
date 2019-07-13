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

    @Query(value = "select * from house_info where token = 1? limit 2?,3?",nativeQuery = true)
    List<HouseInfo> findHouseInfosByTokenLimit(String token,Integer page,Integer limit);

    @Query(value = "select *  from house_info where house_total_price between 0 and 40",nativeQuery = true)
    List<HouseInfo> findHouseLessThanForty();


    @Query(value = "select avg(house_total_price)  from house_info where assessor = 1",nativeQuery = true)
    Double getAveragePrice();

    HouseInfo findHouseInfoById(Integer id);

    @Query(value = "select * from house_info where isdeal = 0 and assessor = 1 and district like concat('%',district,'%')",nativeQuery = true)
    List<HouseInfo> findSameSellHouse(String district);

    @Query(value = "select * from house_info where isdeal = 1 and district like concat('%',district,'%')",nativeQuery = true)
    List<HouseInfo> findSameDealHouse(String district);
}
