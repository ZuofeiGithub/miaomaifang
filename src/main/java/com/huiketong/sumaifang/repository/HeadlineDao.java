package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.Headline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HeadlineDao extends JpaRepository<Headline,Integer> {

    List<Headline> findHeadlinesByCityname(String cityname);
}
