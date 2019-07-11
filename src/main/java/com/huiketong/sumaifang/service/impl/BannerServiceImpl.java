package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.domain.Banner;
import com.huiketong.sumaifang.repository.BannerDao;
import com.huiketong.sumaifang.service.BannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BannerServiceImpl implements BannerService {
    @Autowired
    BannerDao bannerDao;

    @Override
    public List<Banner> findAllBanner() {
        return bannerDao.findAll();
    }
}
