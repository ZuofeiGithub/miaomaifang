package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.domain.Cities;
import com.huiketong.sumaifang.repository.CitiesDao;
import com.huiketong.sumaifang.service.CitiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CitiesServiceImpl implements CitiesService {
    @Autowired
    CitiesDao citiesDao;

    @Override
    public boolean isOpen(String city) {
       Cities cities  = citiesDao.findCitiesByCity(city);
       if(!ObjectUtils.isEmpty(cities)){
           if(cities.isIsopen()){
               return true;
           }else{
               return false;
           }
       }
        return false;
    }

    /**
     * 返回所有开放的城市
     * @return
     */
    @Override
    public List<String> findOpenCities() {
        List<Cities> citiesList = citiesDao.findAllByIsopen(true);
        List<String> cities = new ArrayList<>();
        if(citiesList.size() > 0){
            for(int i = 0; i < citiesList.size();i++){
                cities.add(citiesList.get(i).getCity());
            }
        }
        return cities;
    }
}
