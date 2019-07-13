package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    String createTime;
    String content;
    Integer senderId;
    Integer receiverId;
    @Column(columnDefinition = "int(11) default 0 comment '用户类型 1.卖家.2,经纪人'")
    Integer userType;
}
