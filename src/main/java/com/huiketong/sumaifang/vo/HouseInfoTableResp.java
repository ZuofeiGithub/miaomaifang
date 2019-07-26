package com.huiketong.sumaifang.vo;

import java.util.List;

public class HouseInfoTableResp {

    /**
     * code : 0
     * msg :
     * count : 1000
     * data : [{},{}]
     */

    private int code;
    private String msg;
    private int count;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        Integer id;
        String houseAddress;
        Double houseArea;
        Double housePrice;
        Integer assessor;
        boolean salestop;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public String getHouseAddress() {
            return houseAddress;
        }

        public void setHouseAddress(String houseAddress) {
            this.houseAddress = houseAddress;
        }

        public Double getHouseArea() {
            return houseArea;
        }

        public void setHouseArea(Double houseArea) {
            this.houseArea = houseArea;
        }

        public Double getHousePrice() {
            return housePrice;
        }

        public void setHousePrice(Double housePrice) {
            this.housePrice = housePrice;
        }

        public Integer getAssessor() {
            return assessor;
        }

        public void setAssessor(Integer assessor) {
            this.assessor = assessor;
        }

        public boolean isSalestop() {
            return salestop;
        }

        public void setSalestop(boolean salestop) {
            this.salestop = salestop;
        }
    }
}
