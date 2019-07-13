package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.data.HeadlineNewsData;
import com.huiketong.sumaifang.domain.Headline;
import com.huiketong.sumaifang.repository.HeadlineDao;
import com.huiketong.sumaifang.service.HeadlineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HeadlineServiceImpl implements HeadlineService {
    @Autowired
    HeadlineDao headlineDao;

    @Override
    public List<HeadlineNewsData> findHeadlineByCity(String city) {
        List<Headline> headlineList = headlineDao.findHeadlinesByCityname(city);
        List<HeadlineNewsData> dataList = new ArrayList<>();
        if (headlineList.size() > 0) {
            for (int i = 0; i < headlineList.size(); i++) {
                HeadlineNewsData data = new HeadlineNewsData();
                data.setMsg(headlineList.get(i).getHeadMsg());
                switch (headlineList.get(i).getHeadType()) {
                    case 1:
                        data.setType("成交");
                        break;
                    case 2:
                        data.setType("卖房");
                        break;
                    case 3:
                        data.setType("头条");
                        break;
                    default:
                        data.setType("其他");
                        break;
                }
                dataList.add(data);
            }
        }
        return dataList;
    }
}
