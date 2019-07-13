package com.huiketong.sumaifang.service;

import com.huiketong.sumaifang.domain.Message;

import java.util.List;

public interface MessageService {
    List<Message> findMsgList(String token);
}
