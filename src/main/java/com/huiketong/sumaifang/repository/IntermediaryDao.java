package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.Intermediary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IntermediaryDao extends JpaRepository<Intermediary,Integer> {

    Intermediary findIntermediaryById(Integer id);
}
