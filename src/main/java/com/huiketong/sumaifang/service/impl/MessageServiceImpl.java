package com.huiketong.sumaifang.service.impl;

import com.huiketong.sumaifang.domain.Message;
import com.huiketong.sumaifang.repository.MessageDao;
import com.huiketong.sumaifang.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageServiceImpl implements MessageService {
    @Autowired
    MessageDao messageDao;

    @Override
    public List<Message> findMsgList(String token) {
        return messageDao.findMessagesByToken(token);
    }
}
