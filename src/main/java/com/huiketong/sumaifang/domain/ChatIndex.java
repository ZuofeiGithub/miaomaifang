package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class ChatIndex {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "int(11) default 0 comment '经纪人id'")
    Integer AgentId;
    @Column(columnDefinition = "int(11) default 0 comment '卖家id'")
    Integer BuyerId;
}
