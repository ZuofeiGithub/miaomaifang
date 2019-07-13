package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationDao extends JpaRepository<Notification,Integer> {

    List<Notification> findNotificationsByUserToken(String userToken);
}
