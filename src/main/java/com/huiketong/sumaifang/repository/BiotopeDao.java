package com.huiketong.sumaifang.repository;


import com.huiketong.sumaifang.domain.Biotope;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BiotopeDao extends JpaRepository<Biotope,Integer> {
    @Override
    <S extends Biotope> S save(S s);

    Biotope findBiotopeByName(String name);

    List<Biotope> findBiotopesByCityidAndNameLike(String cityId,String name);
}
