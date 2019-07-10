package com.huiketong.sumaifang.data;

import lombok.Data;

/**
 * 我的房子
 */
@Data
public class MyHouseData {
    //房子描述
    String title;
    //是否通过审核
    boolean ispass;
    //我的房屋图片
    String houseImg;
}
