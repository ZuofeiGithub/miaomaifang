package com.huiketong.sumaifang.data;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

import java.util.List;

@Data
public class SellerData {
//    SameSellHouseData samesellhouse;
//    Integer house_id;
//    Double house_area;
//    String house_layout;
//    Double house_price;
//    String house_title;
//    String houseimgs;
//    IsPassData ispass;
//    List<NewsData> news;
//    String house_style;
//    Double house_unit_price;
//    String house_orientation;
//    String house_layer;
//    String house_use;
//    String building_age;
//    String property_rights_type;
//    String district;
//    CountData count;
//    String salestop;
//    String service_telphone;


    /**
     * code : 1
     * msg :
     * data : {"news":[{"content":"动态内容","time":"07/5"},{"content":"动态内容","time":"07/5"}],"ispass":{"text":"未通过","value":"1"},"houseimgs":[],"house_title":"房屋标题","house_price":"房屋价格","house_layout":"户型","house_area":"面积","district":"小区","property_rights_type":"产权","building_age":"年代","house_use":"用途","house_layer":"楼层","house_orientation":"朝向","house_unit_price":"单价","house_style":"装修","count":{"follow_num":"关注","browse_num":"浏览","see_num":"带看","call_num":"呼叫","data":{"call":[0,2,5,4,7,8,9],"follow":[1,2,5,4,3,8,9],"see":[0,2,3,4,7,4,9],"browse":[0,2,5,4,5,6,9]}},"samesellhouse":[{"house_unit_price":"单价","house_total_price":"总价","house_orientation":"朝向","house_area":"面积","house_layout":"户型","house_img":"https://sumaifang.oss-cn-hangzhou.aliyuncs.com/static/images/dynamic_banner_default.jpg"},{"house_unit_price":"单价","house_total_price":"总价","house_orientation":"朝向","house_area":"面积","house_layout":"户型","house_img":"https://sumaifang.oss-cn-hangzhou.aliyuncs.com/static/images/dynamic_banner_default.jpg"},{"house_unit_price":"单价","house_total_price":"总价","house_orientation":"朝向","house_area":"面积","house_layout":"户型","house_img":"https://sumaifang.oss-cn-hangzhou.aliyuncs.com/static/images/dynamic_banner_default.jpg"}]}
     */

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

    @SerializedName("ispass")
    private IspassBean ispass;
    @SerializedName("house_title")
    private String house_title;
    @SerializedName("house_price")
    private String house_price;
    @SerializedName("house_layout")
    private String house_layout;
    @SerializedName("house_area")
    private String house_area;
    @SerializedName("district")
    private String district;
    @SerializedName("property_rights_type")
    private String property_rights_type;
    @SerializedName("building_age")
    private String building_age;
    @SerializedName("house_use")
    private String house_use;
    @SerializedName("house_layer")
    private String house_layer;
    @SerializedName("house_orientation")
    private String house_orientation;
    @SerializedName("house_unit_price")
    private String house_unit_price;
    @SerializedName("house_style")
    private String house_style;
    @SerializedName("count")
    private CountBean count;
    @SerializedName("news")
    private List<NewsData> news;
    @SerializedName("houseimgs")
    private List<?> houseimgs;
    @SerializedName("samesellhouse")
    private List<SameSellHouseData> samesellhouse;

    public IspassBean getIspassX() {
        return ispass;
    }

    public void setIspassX(IspassBean ispassX) {
        this.ispass = ispassX;
    }

    public String getHouse_titleX() {
        return house_title;
    }

    public void setHouse_titleX(String house_titleX) {
        this.house_title = house_titleX;
    }

    public String getHouse_priceX() {
        return house_price;
    }

    public void setHouse_priceX(String house_priceX) {
        this.house_price = house_priceX;
    }

    public String getHouse_layoutX() {
        return house_layout;
    }

    public void setHouse_layoutX(String house_layoutX) {
        this.house_layout = house_layoutX;
    }

    public String getHouse_areaX() {
        return house_area;
    }

    public void setHouse_areaX(String house_areaX) {
        this.house_area = house_areaX;
    }

    public String getDistrictX() {
        return district;
    }

    public void setDistrictX(String districtX) {
        this.district = districtX;
    }

    public String getProperty_rights_typeX() {
        return property_rights_type;
    }

    public void setProperty_rights_typeX(String property_rights_typeX) {
        this.property_rights_type = property_rights_typeX;
    }

    public String getBuilding_ageX() {
        return building_age;
    }

    public void setBuilding_ageX(String building_ageX) {
        this.building_age = building_ageX;
    }

    public String getHouse_useX() {
        return house_use;
    }

    public void setHouse_useX(String house_useX) {
        this.house_use = house_useX;
    }

    public String getHouse_layerX() {
        return house_layer;
    }

    public void setHouse_layerX(String house_layerX) {
        this.house_layer = house_layerX;
    }

    public String getHouse_orientationX() {
        return house_orientation;
    }

    public void setHouse_orientationX(String house_orientationX) {
        this.house_orientation = house_orientationX;
    }

    public String getHouse_unit_priceX() {
        return house_unit_price;
    }

    public void setHouse_unit_priceX(String house_unit_priceX) {
        this.house_unit_price = house_unit_priceX;
    }

    public String getHouse_styleX() {
        return house_style;
    }

    public void setHouse_styleX(String house_styleX) {
        this.house_style = house_styleX;
    }

    public CountBean getCountX() {
        return count;
    }

    public void setCountX(CountBean countX) {
        this.count = countX;
    }

    public List<NewsData> getNewsX() {
        return news;
    }

    public void setNewsX(List<NewsData> newsX) {
        this.news = newsX;
    }

    public List<?> getHouseimgsX() {
        return houseimgs;
    }

    public void setHouseimgsX(List<?> houseimgsX) {
        this.houseimgs = houseimgsX;
    }

    public List<SameSellHouseData> getSamesellhouseX() {
        return samesellhouse;
    }

    public void setSamesellhouseX(List<SameSellHouseData> samesellhouseX) {
        this.samesellhouse  = samesellhouseX;
    }

    public static class IspassBean {
        /**
         * text : 未通过
         * value : 1
         */

        private String text;
        private String value;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

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

        public String getFollow_num() {
            return follow_num;
        }

        public void setFollow_num(String follow_num) {
            this.follow_num = follow_num;
        }

        public String getBrowse_num() {
            return browse_num;
        }

        public void setBrowse_num(String browse_num) {
            this.browse_num = browse_num;
        }

        public String getSee_num() {
            return see_num;
        }

        public void setSee_num(String see_num) {
            this.see_num = see_num;
        }

        public String getCall_num() {
            return call_num;
        }

        public void setCall_num(String call_num) {
            this.call_num = call_num;
        }

        public DataBean getData() {
            return data;
        }

        public void setData(DataBean data) {
            this.data = data;
        }

        public static class DataBean {
            private List<Integer> call;
            private List<Integer> follow;
            private List<Integer> see;
            private List<Integer> browse;

            public List<Integer> getCall() {
                return call;
            }

            public void setCall(List<Integer> call) {
                this.call = call;
            }

            public List<Integer> getFollow() {
                return follow;
            }

            public void setFollow(List<Integer> follow) {
                this.follow = follow;
            }

            public List<Integer> getSee() {
                return see;
            }

            public void setSee(List<Integer> see) {
                this.see = see;
            }

            public List<Integer> getBrowse() {
                return browse;
            }

            public void setBrowse(List<Integer> browse) {
                this.browse = browse;
            }
        }
    }
}
