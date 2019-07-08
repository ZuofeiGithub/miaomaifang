package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.*;

/**
 * 登陆验证
 */
@Entity
@Data
public class LoginAuth {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "varchar(11) default '' COMMENT '手机号码'")
    String userTelphone;
    @Column(columnDefinition = "varchar(6) default '' COMMENT '六位验证码'")
    String verifyCode;
    @Column(columnDefinition = "varchar(1024) default '' COMMENT 'token'")
    String token;
}
