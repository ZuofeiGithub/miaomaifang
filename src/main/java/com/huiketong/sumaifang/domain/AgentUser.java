package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class AgentUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '所属城市'")
    String cityName;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '姓名'")
    String userName;
    @Column(columnDefinition = "varchar(11) default '' COMMENT '电话'")
    String userPhone;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '公司'")
    String companyInfo;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '唯一id'")
    String uuid;
    @Column(columnDefinition = "datetime  COMMENT '注册时间'")
    Date registerTime;
    @Column(columnDefinition = "datetime  COMMENT '登陆时间'")
    Date loginTime;
    @Column(columnDefinition = "int(1) default 0 COMMENT '是否认证(0未认证,1已认证)'")
    Integer certification;
}
