package com.huiketong.sumaifang.data;

import lombok.Data;

import java.util.List;

@Data
public class HouseDetailData {
    String latitude;
    String longitude;
    List<SameSellHouseData> samesellhouse;
    String house_address;
    String property_rights_type;
    String building_age;
    String house_use;
    String house_tier;
    String decorate_condition; //装修情况
    String house_orientation;
    String house_unit_price;
    String house_area;
    String house_layout;
    String house_price;
    String is_deal;
    String house_title_layout;
    String houselabel;
    List<String> houseimgs;
    List<SameDealHouseData> samedealhouse;
    PriceTimeMachineData price_time_machine;
}
