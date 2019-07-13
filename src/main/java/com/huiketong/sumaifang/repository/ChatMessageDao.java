package com.huiketong.sumaifang.repository;

import com.huiketong.sumaifang.domain.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatMessageDao extends JpaRepository<ChatMessage,Integer> {
    ChatMessage findChatMessageById(Integer Id);
}
