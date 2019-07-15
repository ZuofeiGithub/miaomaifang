package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity
public class HouseInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '房源信息标题'")
    String houseTitle;
    @Column(columnDefinition = "datetime COMMENT '上传时间'")
    Date createTime;
    @Column(columnDefinition = "double(10,2) default 0.00 COMMENT '房屋总价'")
    Double houseTotalPrice;
    @Column(columnDefinition = "double(10,2) default 0.00 COMMENT '房屋单价'")
    Double houseUnitPrice;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '几室几厅'")
    String houseLayout;
    @Column(columnDefinition = "double(10,2) default 0.00 COMMENT '房屋面积'")
    Double houseArea;

    @Column(columnDefinition = "varchar(255) default '1/5' COMMENT '房屋层级'")
    String houseTier;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '房屋地址'")
    String houseAddress;

    @Column(columnDefinition = "varchar(1024) default '' COMMENT '详细地址'")
    String houseDetailAddress;

    @Column(columnDefinition = "varchar(1024) default '' COMMENT '房屋标签'")
    String houseLabel;
    @Column(columnDefinition = "varchar(255) default '南北朝向' COMMENT '房屋朝向'")
    String houseOrientation;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '有无户口'")
    String residenceBooklet;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '什么税'")
    String houseTax;
    @Column(columnDefinition = "boolean default 0 COMMENT '两税是否承担'")
    boolean twoTaxesAssume;

    @Column(columnDefinition = "varchar(255) default '' COMMENT '房屋现状'")
    String houseActuality;
    @Column(columnDefinition = "varchar(1024) default '' COMMENT '卖房原因'")
    String sellHouseReason;

    @Column(columnDefinition = "boolean default 0 COMMENT '气源费是否已经缴纳'")
    boolean airSourceExpense;
    @Column(columnDefinition = "boolean default 0 COMMENT '维修基金是否已经缴纳'")
    boolean maintenanceFunds;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '看房类型'")
    String reviewHouseType;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '倒房时间'")
    String handHouseTime;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '装修情况'")
    String decorateCondition;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '产权类型'")
    String propertyRightsType;
    @Column(columnDefinition = "date COMMENT '建筑年代'")
    Date buildingAge;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '房屋用途'")
    String houseUse;
    @Column(columnDefinition = "double(10,2) default 0.00 COMMENT '佣金比例'")
    Double brokeragePercentage;
    @Column(columnDefinition = "double(10,2) default 0.00 COMMENT '佣金价格'")
    Double brokeragePrice;
    @Column(columnDefinition = "int(3) default 0 COMMENT '1.审核通过 2.待审核,3.审核未通过'")
    Integer assessor;
    @Column(columnDefinition = "datetime  COMMENT '售出时间'")
    Date workOffTime;
    @Column(columnDefinition = "varchar(1024) default '' COMMENT '用户token'")
    String token;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '房子所在的城市'")
    String houseCity;
    @Column(columnDefinition = "boolean default 0 COMMENT '是否成交'")
    boolean isdeal;
    @Column(columnDefinition = "int(255) default 0 COMMENT '浏览次数'")
    Integer visitNum;
    @Column(columnDefinition = "int(255) default 0 COMMENT '带看次数'")
    Integer seeNum;
    @Column(columnDefinition = "datetime comment '带看时间'")
    Date seeTime;
    @Column(columnDefinition = "int(11) default 0 COMMENT '房屋类型:1、房主售卖，2、官方直售'")
    Integer houseType;
    @Column(columnDefinition = "double(10,2) default 0.00 COMMENT '房屋期望价格'")
    Double expectPrice;
    @Column(columnDefinition = "varchar(255) default '' COMMENT '小区'")
    String district;
    @Column(columnDefinition = "int(11) default 0 COMMENT '呼叫次数'")
    Integer callNum;
    @Column(columnDefinition = "int(11) default 0 COMMENT '关注数'")
    Integer attentionNum;
    @Column(columnDefinition = "boolean default 0 comment '是否停售'")
    boolean SaleStop;
}
