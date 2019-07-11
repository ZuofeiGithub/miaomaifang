package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 头条
 */
@Data
@Entity
public class Headline {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "varchar(255) default '' comment '头条类型'")
    String headType;
    @Column(columnDefinition = "varchar(1024) default '' comment '头条消息'")
    String headMsg;
    @Column(columnDefinition = "datetime comment '头条时间'")
    Date createTime;
}
