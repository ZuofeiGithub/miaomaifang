package com.huiketong.sumaifang.domain;


import lombok.Data;

import javax.persistence.*;

/**
 * 小区表
 */
@Entity
@Data
public class Biotope {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "varchar(6) default '' comment '所属区id'")
    String areaid;
    @Column(columnDefinition = "varchar(255) default '' comment '小区名字'")
    String name;

}
