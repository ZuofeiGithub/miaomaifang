package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageDao extends JpaRepository<Message,Integer> {

    List<Message> findMessagesByToken(String token);
}
