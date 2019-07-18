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
    String house_total_price;
    String house_price_wave;
    AreaHistoryAverPriceBean area_history_aver_price;
   CityHistoryAverPriceBean city_history_aver_price;

    @Data
    public static class AreaHistoryAverPriceBean{
        List<String> month;
        List<String> price;
    }
    @Data
    public static class CityHistoryAverPriceBean{
        List<String> month;
        List<String> price;
    }
}
