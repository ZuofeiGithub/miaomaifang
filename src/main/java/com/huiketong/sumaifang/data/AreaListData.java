package com.huiketong.sumaifang.data;

import lombok.Data;

import java.util.List;

public class AreaListData {

    /**
     * data : [{"area_name":"崇川区","child":["娜娜","搜索","ss"]},{"area_name":"港闸","child":["娜娜1","搜索2","ss3"]}]
     */
    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * area_name : 崇川区
         * child : ["娜娜","搜索","ss"]
         */

        private String area_name;
        private List<String> child;

        public String getArea_name() {
            return area_name;
        }

        public void setArea_name(String area_name) {
            this.area_name = area_name;
        }

        public List<String> getChild() {
            return child;
        }

        public void setChild(List<String> child) {
            this.child = child;
        }
    }
}
