package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BannerDao extends JpaRepository<Banner,Integer> {
    @Override
    List<Banner> findAll();
}
