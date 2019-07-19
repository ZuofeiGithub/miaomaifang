package com.huiketong.sumaifang.service;

import com.huiketong.sumaifang.domain.Notification;

import java.util.Date;
import java.util.List;

public interface NotificationService {
    List<Notification> findMyNotic(String token);

    void saveHouseInfo(int type, String msg, String token, int houseId, Date date);
}
