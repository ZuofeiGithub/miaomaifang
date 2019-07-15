package com.huiketong.sumaifang.data;

import lombok.Data;

import java.util.List;

/**
 * 估计结果数据
 */
@Data
public class EstimateData {
    String district;
    String house_layout;
    String house_area;
    String house_layer;
    String houser_total_price;
    String house_price_wave;
    List<AreaHistoryAverPriceBean> area_history_aver_price;
    List<CityHistoryAverPriceBean> city_history_aver_price;

    @Data
    public static class AreaHistoryAverPriceBean{
        String month;
        String price;
    }
    @Data
    public static class CityHistoryAverPriceBean{
        String month;
        String price;
    }
}
