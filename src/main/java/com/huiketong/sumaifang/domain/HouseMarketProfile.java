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
}
