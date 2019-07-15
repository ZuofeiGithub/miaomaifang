package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.domain.Intermediary;
import com.huiketong.sumaifang.repository.IntermediaryDao;
import com.huiketong.sumaifang.service.IntermediaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IntermediaryServiceImpl implements IntermediaryService {
    @Autowired
    IntermediaryDao intermediaryDao;
    @Override
    public Intermediary findIntermediaryById(Integer id) {
        return intermediaryDao.findIntermediaryById(id);
    }
}
