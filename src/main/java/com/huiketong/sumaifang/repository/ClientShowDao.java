package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.ClientShow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientShowDao extends JpaRepository<ClientShow,Integer> {
    ClientShow findClientShowByCityName(String cityName);
}
