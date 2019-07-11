package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class HouseImg {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "varchar(255) default '' comment '房屋实景图'")
    String imgurl;
    @Column(columnDefinition = "int(11) default 0 comment '房屋id'")
    Integer houseId;
    @Column(columnDefinition = "datetime comment '上传时间'")
    Date createTime;
}
