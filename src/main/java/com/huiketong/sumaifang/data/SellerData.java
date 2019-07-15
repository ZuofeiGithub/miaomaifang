package com.huiketong.sumaifang.data;

import lombok.Data;

import java.util.List;

@Data
public class SellerData {
    SameSellHouseData samesellhouse;
    Integer house_id;
    Double house_area;
    String house_layout;
    Double house_price;
    String house_title;
    String houseimgs;
    IsPassData ispass;
    List<NewsData> news;
    String house_style;
    Double house_unit_price;
    String house_orientation;
    String house_layer;
    String house_use;
    String building_age;
    String property_rights_type;
    String district;
    CountData count;
    String salestop;
    String service_telphone;

}
