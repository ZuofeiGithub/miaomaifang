package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class Banner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "varchar(255) default '' comment '图片地址'")
    String imgurl;
    @Column(columnDefinition = "varchar(255) default '' comment '跳转地址'")
    String linkurl;
}
