package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.domain.Notification;
import com.huiketong.sumaifang.repository.NotificationDao;
import com.huiketong.sumaifang.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {
    @Autowired
    private NotificationDao notificationDao;
    @Override
    public List<Notification> findMyNotic(String token) {
        return notificationDao.findNotificationsByUserToken(token);
    }
}
