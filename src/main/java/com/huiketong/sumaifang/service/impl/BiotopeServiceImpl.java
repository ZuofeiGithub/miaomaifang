package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.domain.Biotope;
import com.huiketong.sumaifang.repository.BiotopeDao;
import com.huiketong.sumaifang.service.BiotopeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class BiotopeServiceImpl implements BiotopeService {

    @Autowired
    BiotopeDao biotopeDao;
    @Override
    public boolean saveBiotopeInfo(String areaId,String name) {
        Biotope exist = biotopeDao.findBiotopeByName(name);
        if(ObjectUtils.isEmpty(exist)) {
            Biotope biotope = new Biotope();
            biotope.setAreaid(areaId);
            biotope.setName(name);
            try {
                biotopeDao.save(biotope);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    @Override
    public List<Biotope> findBiotopList(String cityId, String name) {
        return biotopeDao.findBiotopesByCityidAndNameLike(cityId,"%"+name+"%");

    }

    @Override
    public List<Biotope> findBiotopList(String cityId) {
        return biotopeDao.findAllByCityid(cityId);
    }


}
