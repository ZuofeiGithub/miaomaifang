package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.Areas;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AreasDao extends JpaRepository<Areas,Integer> {
     List<Areas> findAllByCityid(String cityId);
}
