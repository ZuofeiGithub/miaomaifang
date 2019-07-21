package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @Author: ￣左飞￣
 * @Date: 2019/7/20 21:45
 * @Version 1.0
 * 关注表
 */
@Data
@Entity
public class Attention {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "int(11) default 0 comment '经纪人id'")
    Integer agentId;
    @Column(columnDefinition = "int(11) default 0 comment '房屋id'")
    Integer houseId;
    @Column(columnDefinition = "datetime comment '关注时间'")
    Date attenTime;
    @Column(columnDefinition = "int(3) default 0 comment '是否关注'")
    Integer isatten;
}
