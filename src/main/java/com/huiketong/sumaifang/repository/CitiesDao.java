package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitiesDao extends JpaRepository<Cities,Integer> {

    /**
     * 根据城市名找到字段
     * @param city
     * @return
     */
    @Query(value = "select * from cities where city like concat('%',?1,'%')",nativeQuery = true)
    Cities findCitiesByCityName(String city);

    List<Cities> findAllByIsopen(boolean bopen);

    @Query(value = "select * from cities where city = ?1 and isopen = ?2",nativeQuery = true)
    Cities findOpenCitys(String cityName,Integer isOpen);
}
