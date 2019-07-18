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
    @Column(columnDefinition = "int(255) default '' comment '头条类型'")
    Integer headType;
    @Column(columnDefinition = "varchar(255) default '' comment '头条消息'")
    String headMsg;
    @Column(columnDefinition = "datetime comment '头条时间'")
    Date createTime;
    @Column(columnDefinition = "varchar(255) default '' comment '城市'")
    String cityname;
}
