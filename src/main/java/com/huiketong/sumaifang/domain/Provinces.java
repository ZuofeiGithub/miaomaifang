package com.huiketong.sumaifang.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * (Provinces)实体类
 *
 * @author makejava
 * @since 2019-07-11 14:51:14
 */
@Data
@Entity
public class Provinces implements Serializable {
    private static final long serialVersionUID = -95320748010227150L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    private Integer provinceid;
    
    private String province;


}