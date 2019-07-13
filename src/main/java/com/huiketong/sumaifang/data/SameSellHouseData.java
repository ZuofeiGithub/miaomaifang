package com.huiketong.sumaifang.data;

import lombok.Data;

/**
 * 同款售卖房源
 */
@Data
public class SameSellHouseData {
    String house_img;
    String house_layout;
    String house_area;
    String house_orientation;
    String house_total_price;
    String house_unit_price;
    Integer house_id;
}
