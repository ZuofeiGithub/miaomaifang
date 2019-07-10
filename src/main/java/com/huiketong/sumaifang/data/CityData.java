package com.huiketong.sumaifang.data;

import lombok.Data;

/**
 * 当前城市信息
 */
@Data
public class CityData {

    //默认城市
    String default_city;

    //是否开通业务
    boolean isopen;
}
