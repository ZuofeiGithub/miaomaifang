package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.AreaStreet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreaStreetDao extends JpaRepository<AreaStreet,Integer> {

    List<AreaStreet> findAreaStreetsByAreaId(String areaId);
}
