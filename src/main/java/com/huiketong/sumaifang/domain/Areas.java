package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * 区县信息表(Areas)实体类
 *
 * @author makejava
 * @since 2019-07-11 14:52:24
 */
@Entity
@Data
public class Areas implements Serializable {
    private static final long serialVersionUID = 779052995186506070L;
    //主键
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //区县编码
    private String areaid;
    //区县名称
    private String area;
    //所属城市编码
    private String cityid;

}