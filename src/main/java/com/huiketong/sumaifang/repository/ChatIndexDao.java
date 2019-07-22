package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.ChatIndex;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatIndexDao extends JpaRepository<ChatIndex,Integer> {
    ChatIndex findChatMessageById(Integer Id);
}
