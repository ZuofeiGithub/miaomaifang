package com.huiketong.sumaifang.data;


import lombok.Data;

/**
 * 最近成交房源信息
 */
@Data
public class RecentDealHouseData {
    String title;
    String house_img;
    String district;
    Integer dealdays;
    String house_layout;
    Integer house_total_price;
    Double house_area;
    String house_label;

}
