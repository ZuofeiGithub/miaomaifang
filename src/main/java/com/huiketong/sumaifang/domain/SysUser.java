package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class SysUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '登陆用户'")
    String userId;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '登陆密码'")
    String password;
    @Column(columnDefinition = "int(11) default 0 COMMENT '用户类型 1系统用户 2经纪人用户 其他无权限'")
    Integer userType;
    @Column(columnDefinition = "datetime COMMENT '注册时间'")
    Date registerTime;
}
