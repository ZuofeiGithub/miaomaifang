package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.repository.TerraceRecordDao;
import com.huiketong.sumaifang.service.TerraceRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TerraceRecordServiceImpl implements TerraceRecordService {
    @Autowired
    TerraceRecordDao terraceRecordDao;

    @Override
    public int getSeeNums(int day) {
      return terraceRecordDao.findCurrentDaySeeNums(day);
    }

    @Override
    public int getCallNums(int day) {
        return terraceRecordDao.findCurrentDayCallNums(day);
    }

    @Override
    public int getFollowNums(int day) {
        return terraceRecordDao.findCurrentDayFollowNums(day);
    }

    @Override
    public int getVisitNums(int day) {
        return terraceRecordDao.findCurrentDayVisitNums(day);
    }
}
