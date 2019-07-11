package com.huiketong.sumaifang.data;

import lombok.Data;

/**
 * 我的房子
 */
@Data
public class MyHouseData {
    Integer house_id;
    //房子描述
    String title;
    //是否通过审核
    boolean ispass;
    //我的房屋图片
    String houseImg;
    //提交时间
    String submitTime;
    //房屋面积
    Double houseArea;
    //浏览次数
    Integer visitsNum;
    //带看次数
    Integer seeNum;
}
