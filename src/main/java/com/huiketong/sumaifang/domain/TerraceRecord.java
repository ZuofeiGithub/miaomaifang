package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * 平台统计
 */
@Data
@Entity
public class TerraceRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "int(11) default 0 COMMENT '房屋'")
    Integer HouseId;
    @Column(columnDefinition = "int(11) default 0 COMMENT '卖家'")
    Integer BuyerId;
    @Column(columnDefinition = "int(11) default 0 COMMENT '经纪人'")
    Integer AgentId;
    @Column(columnDefinition = "int(11) default 0 COMMENT '类型 1.带看,2.呼叫,3.关注,4.浏览'")
    Integer RecType;
    @Column(columnDefinition = "datetime comment '发生时间'")
    Date HappenedTime;
}
