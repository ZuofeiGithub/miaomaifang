package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
public class CustomAreas {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "varchar(255) default '' comment '区域名字'")
    Integer areaName;
}
