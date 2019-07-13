package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.domain.ChatMessage;
import com.huiketong.sumaifang.repository.ChatMessageDao;
import com.huiketong.sumaifang.service.ChatMessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatMessageServiceImpl implements ChatMessageService {
    @Autowired
    ChatMessageDao chatMessageDao;

    @Override
    public ChatMessage findChatMsgById(Integer id) {
        return chatMessageDao.findChatMessageById(id);
    }
}
