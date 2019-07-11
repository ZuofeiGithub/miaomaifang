package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationDao extends JpaRepository<Notification,Integer> {
}
