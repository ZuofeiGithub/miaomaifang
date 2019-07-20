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
    @Column(columnDefinition = "varchar(1024) default '' comment '用户唯一标识'")
    String token;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '所属城市'")
    String cityName;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '姓名'")
    String userName;
    @Column(columnDefinition = "varchar(255) default '' comment '昵称'")
    String nickName;
    @Column(columnDefinition = "varchar(255) default '' comment '性别 1男 2女 0未知'")
    Integer sex;
    @Column(columnDefinition = "varchar(255) default '' comment '省份'")
    String province;
    @Column(columnDefinition = "varchar(255) default '' comment '国家'")
    String country;
    @Column(columnDefinition = "varchar(255) default '' comment '语言'")
    String language;
    @Column(columnDefinition = "varchar(11) default '' COMMENT '电话'")
    String userPhone;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '公司'")
    String companyInfo;
    @Column(columnDefinition = "datetime  COMMENT '注册时间'")
    Date registerTime;
    @Column(columnDefinition = "datetime  COMMENT '登陆时间'")
    Date loginTime;
    @Column(columnDefinition = "int(1) default 0 COMMENT '是否认证(0未认证,1已认证)'")
    Integer certification;
    @Column(columnDefinition = "int(11) default 0 comment '中介id'")
    Integer intermediaryId;
    @Column(columnDefinition = "varchar(255) default '' comment '经纪人头像'")
    String avatarUrl;
    @Column(columnDefinition = "varchar(1024) default '' comment 'openid'")
    String openid;
    @Column(columnDefinition = "int(3) default 0  comment '是否绑定 0 未绑定，1绑定'")
    Integer isbind;
    @Column(columnDefinition = "varchar(6) default '' comment '手机验证码'")
    String phoneCode;
    @Column(columnDefinition = "varchar(255) default '' comment '微信号'")
    String wxaccount;
    @Column(columnDefinition = "varchar(255) default '' comment '介绍'")
    String introduce;
}
