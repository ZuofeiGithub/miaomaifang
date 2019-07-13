package com.huiketong.sumaifang.domain;


import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

/**
 * 房屋市场概况
 */
@Data
@Entity
public class HouseMarketProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "double(4,2) default 0.00 COMMENT '全市均价'")
    Double averagePrice;
    @Column(columnDefinition = "int(11) default 0 COMMENT '当前月份'")
    Integer currentMonth;
    @Column(columnDefinition = "datetime COMMENT '当前时间'")
    Date curTime;
    @Column(columnDefinition = "int(11) default 0 comment '涨跌 1涨 2跌'")
    Integer rise;
    @Column(columnDefinition = "double(10,2) default 0.00 comment '涨幅'")
    Double increase;
    @Column(columnDefinition = "bigint default 0 comment '在售房源个数'")
    Long sellingHouses;
    @Column(columnDefinition = "double(10,2) default 0.00 comment '房子单价'")
    Double houseUnitPrice;
    @Column(columnDefinition = "varchar(255) default '' comment '区域名字'")
    String areaName;
}
