package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.Cities;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CitiesDao extends JpaRepository<Cities,Integer> {

    /**
     * 更加城市名找到字段
     * @param city
     * @return
     */
    Cities findCitiesByCity(String city);

    List<Cities> findAllByIsopen(boolean bopen);
}
