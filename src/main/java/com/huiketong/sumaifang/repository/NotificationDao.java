package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NotificationDao extends JpaRepository<Notification,Integer> {

    List<Notification> findNotificationsByUserToken(String userToken);
}
