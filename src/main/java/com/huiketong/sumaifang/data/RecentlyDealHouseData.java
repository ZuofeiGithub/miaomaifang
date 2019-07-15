package com.huiketong.sumaifang.data;

import lombok.Data;

import java.util.List;

/**
 * @Author: ￣左飞￣
 * @Date: 2019/7/15 22:28
 * @Version 1.0
 */
@Data
public class RecentlyDealHouseData {

    List<HouseListData> house_list;

    @Data
    public static class HouseListData {
        String house_title_layout;
        Integer house_id;
        String house_community;
        String house_title;
        String house_layout;
        String house_area;
        String house_label;
        Double house_price;
        String house_deal_days;
        String house_img;
        String house_orientation;
        String deal_date;
        String house_unit_price;
        String house_layer;
    }
}
