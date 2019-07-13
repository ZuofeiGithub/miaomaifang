package com.huiketong.sumaifang.domain;


import lombok.Data;

import javax.persistence.*;

/**
 * 客户端显示的城市数据
 */
@Data
@Entity
public class ClientShow {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "int(255) default 0 comment '平均售出天数'")
    Integer sellDaysNum;
    @Column(columnDefinition = "int(255) default 0 comment '全城中介个数'")
    Integer intermediaryAgentNum;
    @Column(columnDefinition = "int(255) default 0 comment '全城储备买家'")
    Integer buyerNum;
    @Column(columnDefinition = "int(255) default 0 comment '全城经纪人数量'")
    Integer agentNum;
    @Column(columnDefinition = "double(10,2) default 0.00 comment '全市均价'")
    Double averagePrice;
    @Column(columnDefinition = "double(10,2) default 0.00 comment '环比上月增长'")
    Double rise;
    @Column(columnDefinition = "varchar(1024) default '' comment '城市名字'")
    String cityName;
    @Column(columnDefinition = "int(3) default 0 comment '涨跌'")
    Integer upAndDown;
}
