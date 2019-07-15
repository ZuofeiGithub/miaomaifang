package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.HouseAgent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HouseAgentDao extends JpaRepository<HouseAgent,Integer> {

    /**
     * 根据房屋id 和联系类型找到经纪人
     * @param houseid
     * @param type
     * @return
     */
    @Query(value = "select * from house_agent where house_id = ?1 and link_type = ?2 limit ?3,?4",nativeQuery = true)
    List<HouseAgent> findHouseAgentsByHouseIdAndLinkTypeLimit(Integer houseid,Integer type,Integer page,Integer limit);
}
