package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.HouseInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Repository
public interface HouseInfoDao extends JpaRepository<HouseInfo,Integer> {
    @Override
    <S extends HouseInfo> S save(S s);

    @Override
    List<HouseInfo> findAll();

    @Query(value = "select * from house_info where token = ?1 limit ?2,?3",nativeQuery = true)
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

    HouseInfo findHouseInfoByDistrict(String district);

   // @Query(value = "update house_info set ",nativeQuery = true)

    @Query(value = "update house_info set sale_stop = ?2 where id = ?1 ",nativeQuery = true)
    @Modifying
    @Transactional
    void updateSaleStopById(Integer id,Integer salestop);

    @Query(value = "update house_info set see_time = ?1 where id = ?2",nativeQuery = true)
    @Modifying
    @Transactional
    void updateSeeTimeById(Date seeTime,Integer id);


    @Query(value = "update house_info set house_total_price = ?1 where id=?2",nativeQuery = true)
    @Modifying
    @Transactional
    void updatePriceById(Double price,Integer houseId);

    @Query(value = "update house_info set house_layout = ?1,house_orientation = ?2,house_tier = ?3,house_area = ?4 where district like concat('%',?5,'%')",nativeQuery = true)
    @Modifying
    @Transactional
    void updateInfo(String community,String house_type, String toward, String floor, String area);


    @Query(value = "select avg(house_total_price) from house_info where house_city = ?1",nativeQuery = true)
    Double countHouseAvgPriceByHouseCity(String cityname);

    @Query(value = "select avg(house_total_price) from house_info where  area_id= ?1",nativeQuery = true)
    Double countHouseAvgPriceByAreaId(String areaId);

    @Query(value = "select * from house_info where house_city = ?1 and isdeal = 1",nativeQuery = true)
    List<HouseInfo> findHouseInfosByHouseCity(String cityName);

}
