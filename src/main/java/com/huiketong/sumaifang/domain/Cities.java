package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 城市信息表(Cities)实体类
 *
 * @author makejava
 * @since 2019-07-11 14:49:14
 */
@Entity
@Data
public class Cities implements Serializable {
    private static final long serialVersionUID = 419452161181218083L;
    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //城市编码
    private String cityid;
    //城市名称
    private String city;
    //所属省份编码
    private String provinceid;
    @Column(columnDefinition = "boolean default 0 comment '是否开通速买房业务'")
    private boolean isopen;
}