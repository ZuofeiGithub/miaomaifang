package com.huiketong.sumaifang.repository;


import com.huiketong.sumaifang.domain.Biotope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BiotopeDao extends JpaRepository<Biotope,Integer> {
}
