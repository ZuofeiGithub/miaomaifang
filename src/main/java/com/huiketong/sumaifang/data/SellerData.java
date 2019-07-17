package com.huiketong.sumaifang.data;

import lombok.Data;

import java.util.List;

@Data
public class SellerData {

    /**
     * news : [{"content":"动态内容","time":"07/5"},{"content":"动态内容","time":"07/5"}]
     * ispass : {"text":"未通过","value":"1"}
     * houseimgs : []
     * house_title : 房屋标题
     * house_price : 房屋价格
     * house_layout : 户型
     * house_area : 面积
     * district : 小区
     * property_rights_type : 产权
     * building_age : 年代
     * house_use : 用途
     * house_layer : 楼层
     * house_orientation : 朝向
     * house_unit_price : 单价
     * house_style : 装修
     * count : {"follow_num":"关注","browse_num":"浏览","see_num":"带看","call_num":"呼叫","data":{"call":[0,2,5,4,7,8,9],"follow":[1,2,5,4,3,8,9],"see":[0,2,3,4,7,4,9],"browse":[0,2,5,4,5,6,9]}}
     * samesellhouse : [{"house_unit_price":"单价","house_total_price":"总价","house_orientation":"朝向","house_area":"面积","house_layout":"户型","house_img":"https://sumaifang.oss-cn-hangzhou.aliyuncs.com/static/images/dynamic_banner_default.jpg"},{"house_unit_price":"单价","house_total_price":"总价","house_orientation":"朝向","house_area":"面积","house_layout":"户型","house_img":"https://sumaifang.oss-cn-hangzhou.aliyuncs.com/static/images/dynamic_banner_default.jpg"},{"house_unit_price":"单价","house_total_price":"总价","house_orientation":"朝向","house_area":"面积","house_layout":"户型","house_img":"https://sumaifang.oss-cn-hangzhou.aliyuncs.com/static/images/dynamic_banner_default.jpg"}]
     */

    private IspassBean ispass;
    private String house_title;
    private String house_price;
    private String house_layout;
    private String house_area;
    private String district;
    private String property_rights_type;
    private String building_age;
    private String house_use;
    private String house_layer;
    private String house_orientation;
    private String house_unit_price;
    private String house_style;
    private String house_id;
    private CountBean count;
    private List<NewsBean> news;
    private List<?> houseimgs;
    private List<SamesellhouseBean> samesellhouse;


    @Data
    public static class IspassBean {
        /**
         * text : 未通过
         * value : 1
         */

        private String text;
        private String value;
    }

    @Data
    public static class CountBean {
        /**
         * follow_num : 关注
         * browse_num : 浏览
         * see_num : 带看
         * call_num : 呼叫
         * data : {"call":[0,2,5,4,7,8,9],"follow":[1,2,5,4,3,8,9],"see":[0,2,3,4,7,4,9],"browse":[0,2,5,4,5,6,9]}
         */

        private String follow_num;
        private String browse_num;
        private String see_num;
        private String call_num;
        private DataBean data;


        @Data
        public static class DataBean {
            private List<Integer> call;
            private List<Integer> follow;
            private List<Integer> see;
            private List<Integer> browse;
        }
    }

    @Data
    public static class NewsBean {
        /**
         * content : 动态内容
         * time : 07/5
         */

        private String content;
        private String time;
    }

    @Data
    public static class SamesellhouseBean {
        /**
         * house_unit_price : 单价
         * house_total_price : 总价
         * house_orientation : 朝向
         * house_area : 面积
         * house_layout : 户型
         * house_img : https://sumaifang.oss-cn-hangzhou.aliyuncs.com/static/images/dynamic_banner_default.jpg
         */

        private String house_unit_price;
        private String house_total_price;
        private String house_orientation;
        private String house_area;
        private String house_layout;
        private String house_img;
    }

}
