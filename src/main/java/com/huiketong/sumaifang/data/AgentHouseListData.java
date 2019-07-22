package com.huiketong.sumaifang.data;

import java.util.List;

public class AgentHouseListData {
    private List<RecommendHouseBean> recommend_house;
    private List<HouseListBean> house_list;

    public List<RecommendHouseBean> getRecommend_house() {
        return recommend_house;
    }

    public void setRecommend_house(List<RecommendHouseBean> recommend_house) {
        this.recommend_house = recommend_house;
    }

    public List<HouseListBean> getHouse_list() {
        return house_list;
    }

    public void setHouse_list(List<HouseListBean> house_list) {
        this.house_list = house_list;
    }

    public static class RecommendHouseBean {
        /**
         * title : sunt sit laboris
         * image : ex
         * layout : ex
         * area : Lorem nostrud proident dolore ullamco
         * totalprice : irure eiusmod laborum esse
         * house_id : eiusmod
         */

        private String title;
        private String image;
        private String layout;
        private String area;
        private String totalprice;
        private String house_id;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getLayout() {
            return layout;
        }

        public void setLayout(String layout) {
            this.layout = layout;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getTotalprice() {
            return totalprice;
        }

        public void setTotalprice(String totalprice) {
            this.totalprice = totalprice;
        }

        public String getHouse_id() {
            return house_id;
        }

        public void setHouse_id(String house_id) {
            this.house_id = house_id;
        }
    }

    public static class HouseListBean {
        /**
         * img : et ut
         * title : deserunt aute nisi dolor Duis
         * area : anim sunt dolore cupidatat fugiat
         * toward : velit sit ad aliquip
         * floor : sed cupidatat laborum ipsum
         * type : in dolor ea minim
         * label : sed nulla
         * total_price : irure tempor cillum eu ad
         * unit_price : deserunt ipsum
         * house_id : anim
         */

        private String img;
        private String title;
        private String area;
        private String toward;
        private String floor;
        private String type;
        private String label;
        private String total_price;
        private String unit_price;
        private String house_id;

        public String getImg() {
            return img;
        }

        public void setImg(String img) {
            this.img = img;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getArea() {
            return area;
        }

        public void setArea(String area) {
            this.area = area;
        }

        public String getToward() {
            return toward;
        }

        public void setToward(String toward) {
            this.toward = toward;
        }

        public String getFloor() {
            return floor;
        }

        public void setFloor(String floor) {
            this.floor = floor;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getLabel() {
            return label;
        }

        public void setLabel(String label) {
            this.label = label;
        }

        public String getTotal_price() {
            return total_price;
        }

        public void setTotal_price(String total_price) {
            this.total_price = total_price;
        }

        public String getUnit_price() {
            return unit_price;
        }

        public void setUnit_price(String unit_price) {
            this.unit_price = unit_price;
        }

        public String getHouse_id() {
            return house_id;
        }

        public void setHouse_id(String house_id) {
            this.house_id = house_id;
        }
    }
}
