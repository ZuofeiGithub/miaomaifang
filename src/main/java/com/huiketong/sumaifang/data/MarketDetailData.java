package com.huiketong.sumaifang.data;

import lombok.Data;

import java.util.List;

@Data
public class MarketDetailData {


        /**
         * price_trend : {"month":["8月","9月","10月","11月","12月"],"unitprice":["1132","1231","12354","41564","4564"]}
         * selling_houses : 1564
         * increase : -2
         * average_price : 15245
         * current_month : 7
         * area_reference_price : [{"areaname":"区域名字","average_price":"1542","increase":0},{"areaname":"区域名字","average_price":"1542","increase":1},{"areaname":"区域名字","average_price":"1542","increase":-26}]
         */

        private PriceTrendBean price_trend;
        private String selling_houses;
        private String increase;
        private String average_price;
        private String current_month;
        private List<AreaReferencePriceBean> area_reference_price;


        @Data
        public static class PriceTrendBean {
            private List<String> month;
            private List<String> unitprice;

        }

        @Data
        public static class AreaReferencePriceBean {
            /**
             * areaname : 区域名字
             * average_price : 1542
             * increase : 0
             */

            private String areaname;
            private String average_price;
            private int increase;

        }
}
