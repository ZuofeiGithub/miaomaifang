package com.huiketong.sumaifang.data;

import lombok.Data;

import java.util.List;

/**
 * 当前城市信息
 */
@Data
public class CityData {
    DefaultCityBean default_city;
    List<CityInitials> group;

    @Data
    public static class CityInitials{
        String city_initials;
        List<String> city_name;
    }
    @Data
    public  static class DefaultCityBean{
        String city_name;
        String is_open;
    }
}
