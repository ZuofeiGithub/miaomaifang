package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class HouseAgent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "int(11) default 0 comment '房屋id'")
    Integer houseId;
    @Column(columnDefinition = "int(11) default 0 comment '经济人id'")
    Integer agentId;
    @Column(columnDefinition = "datetime comment '联系时间'")
    Date linkTime;
    @Column(columnDefinition = "int(11) default 0 comment '联系类型1.带看,2.通话'")
    Integer linkType;
}
