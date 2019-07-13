package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class HousePrice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "datetime comment '报价日期'")
    Date offerTime;
    @Column(columnDefinition = "double(10,2) default 0.00 comment '报价金额'")
    Double offerPrice;
    @Column(columnDefinition = "int(11) default 0 comment '报价类型 1.报价,2.调价'")
    Integer offerType;
    @Column(columnDefinition = "int(11) default 0 comment '房屋id'")
    Integer houseId;
}
