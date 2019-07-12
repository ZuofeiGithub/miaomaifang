package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 登陆验证
 */
@Entity
@Data
public class CommonUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "varchar(11) default '' COMMENT '手机号码'")
    String userTelphone;
    @Column(columnDefinition = "varchar(6) default '' COMMENT '六位验证码'")
    String verifyCode;
    @Column(columnDefinition = "varchar(1024) default '' COMMENT 'token'")
    String token;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '微信用户唯一凭证'")
    String openid;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '微信用户昵称'")
    String nickName;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '性别'")
    String gender;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '语言'")
    String language;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '城市'")
    String city;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '省份'")
    String province;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '国家'")
    String country;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '头像'")
    String avatarUrl;
    @Column(columnDefinition = "boolean default 0 comment '是否绑定'")
    boolean isbind;
}
