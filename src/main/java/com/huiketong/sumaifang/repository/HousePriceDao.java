package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.HousePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HousePriceDao extends JpaRepository<HousePrice,Integer> {
    List<HousePrice>  findHousePricesByHouseId(Integer houseId);


    /**
     * 查找最近半年的房屋价格
     * @param houseId
     * @return
     */
    @Query(value = "select * from  house_price where period_diff(date_format(now() , '%Y%m') , date_format(`add_time`, '%Y%m')) < 6;",nativeQuery = true)
    List<HousePrice> findHistoryPricesByHouseId(Integer houseId);
}
