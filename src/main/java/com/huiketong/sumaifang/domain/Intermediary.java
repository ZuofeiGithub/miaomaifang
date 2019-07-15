package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class Intermediary {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "varchar(255) default '' comment '中介名字'")
    String name;
    @Column(columnDefinition = "varchar(255) default '' comment '中介电话'")
    String telphone;
    @Column(columnDefinition = "datetime comment '成立时间' ")
    Date establishTime;
}
