package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.domain.ChatIndex;
import com.huiketong.sumaifang.repository.ChatIndexDao;
import com.huiketong.sumaifang.service.ChatIndexService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatIndexServiceImpl implements ChatIndexService {
    @Autowired
    ChatIndexDao chatIndexDao;

    @Override
    public ChatIndex findChatMsgById(Integer id) {
        return chatIndexDao.findChatMessageById(id);
    }
}
