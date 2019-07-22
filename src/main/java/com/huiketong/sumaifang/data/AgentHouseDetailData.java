package com.huiketong.sumaifang.data;

import java.util.List;

public class AgentHouseDetailData {


        /**
         * house_img_list : ["proident ci","aliqua in","veniam voluptate","irure laborum proident","deserunt sit"]
         * house_desc : {"title":"reprehenderit","type":"eu consequ","provider":"Duis","label":"sunt"}
         * house_data : {"totalprice":"non aliqua","layout":"mollit","area":"magna"}
         * additional_data : {"unitprice":"cupidatat cillum irure","toward":"et","decorate_condition":"in culpa sint","house_tier":"proident voluptate laborum aute","use":"aute dolor in","age":"ad enim fugiat","property_rights_type":"ipsum consectetur ex dolor id","district":"ut tempor in enim aute","type":"consectetur","residence_booklet":"enim","address":"aliquip","house_num":"occaecat in sed aute fugiat"}
         * tradeinfo : {"taxtype":"quis esse consectetur d","actuality":"ut","two_taxes_assume":"sunt","sell_house_reason":"ex","air_source_expense":"eiusmod proident Excepteur occaecat in","maintenance_funds":"Excepteur adipisicing elit","review_house_type":"cupidatat","hand_house_time":"eiusmod cupidatat laboris in elit"}
         * location : {"longitude":"incididunt in dolore","latitude":"irure velit fugiat amet ullamco"}
         * place_pic : ["aute anim"]
         * matters_needing_attention : eiusmod irure ipsum aliquip
         * take_look_at_trip : ut et officia amet
         * samehouses : [{"image":"nulla occaecat ut","title":"Duis qui sint reprehenderit consequat","tier":"exercitation sint ea nostrud","layout":"sed quis","area":"sed sunt labore","label":"non incididunt","total_price":"reprehenderit","unit_price":"non laboris qui ut"},{"image":"do","title":"consequat officia quis","tier":"do Lorem occaecat Excepteur","layout":"exer","area":"ut commodo in","label":"incididunt Duis","total_price":"consectetur laborum in","unit_price":"non consectetur qui tempor anim"},{"image":"in nisi id cillum dolor","title":"proident esse labore","tier":"in dolore ullamco","layout":"voluptate in","area":"ad","label":"occaecat","total_price":"laboris ipsum deserunt magna","unit_price":"anim in in dolore sed"},{"image":"incididunt","title":"cupidatat eu","tier":"pariatur amet exercitation","layout":"ut labore","area":"laborum anim consectetur","label":"tempor irure ","total_price":"commodo eu velit eiusmod incididunt","unit_price":"Ut amet"},{"image":"adipisicing","title":"magna","tier":"Excepteur aute sed dolor","layout":"et","area":"cupidatat aute sit commodo magna","label":"dolor irure magna tempor","total_price":"exercitation laborum sit","unit_price":"magna"}]
         * goodhouses : [{"image":"deserunt eiusmod reprehenderit","title":"in proident velit exercitation consequat","tier":"tempor aute ex","layout":"in","area":"aute veniam aliqua labore exercitation","label":"Excepteur labo","unit_price":"Ut Excepteur","total_price":"irure nisi magna officia"},{"image":"nulla enim proident","title":"ea","tier":"e","layout":"exercitat","area":"ex","label":"esse Lorem reprehenderit","unit_price":"quis","total_price":"pariatur aute ex magna ullamco"},{"image":"dolor proident nisi enim","title":"id tempor dolore","tier":"cupidatat","layout":"magna elit proident ut Ut","area":"Lorem elit sed","label":"ut","unit_price":"occaeca","total_price":"id nostrud Excepteur"},{"image":"sint commodo pariatur anim","title":"ut dol","tier":"veniam exercitation laborum ut dolor","layout":"ullamco aute labore","area":"nostrud","label":"eiusmod quis voluptate","unit_price":"ullamco qui","total_price":"enim in sed dolor eu"}]
         * guessyourlikehouses : [{"image":"in non","layout":"amet reprehenderit","area":"cupidatat ad","toward":"ex","total_price":"incididunt dolor reprehenderit sunt dolore","unit_price":"id anim amet labore tempor"},{"image":"cillum amet labore sint","layout":"proident dolore dolor consectetur non","area":"labore commodo aute ut","toward":"reprehenderit voluptate amet cupidatat dolore","total_price":"mollit adipis","unit_price":"nulla ullamco Lorem dolore aute"}]
         * is_attention : irure ea ut laborum et
         */

        private HouseDescBean house_desc;
        private HouseDataBean house_data;
        private AdditionalDataBean additional_data;
        private TradeinfoBean tradeinfo;
        private LocationBean location;
        private String matters_needing_attention;
        private String take_look_at_trip;
        private String is_attention;
        private List<String> house_img_list;
        private List<String> place_pic;
        private List<SamehousesBean> samehouses;
        private List<GoodhousesBean> goodhouses;
        private List<GuessyourlikehousesBean> guessyourlikehouses;

        public HouseDescBean getHouse_desc() {
            return house_desc;
        }

        public void setHouse_desc(HouseDescBean house_desc) {
            this.house_desc = house_desc;
        }

        public HouseDataBean getHouse_data() {
            return house_data;
        }

        public void setHouse_data(HouseDataBean house_data) {
            this.house_data = house_data;
        }

        public AdditionalDataBean getAdditional_data() {
            return additional_data;
        }

        public void setAdditional_data(AdditionalDataBean additional_data) {
            this.additional_data = additional_data;
        }

        public TradeinfoBean getTradeinfo() {
            return tradeinfo;
        }

        public void setTradeinfo(TradeinfoBean tradeinfo) {
            this.tradeinfo = tradeinfo;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getMatters_needing_attention() {
            return matters_needing_attention;
        }

        public void setMatters_needing_attention(String matters_needing_attention) {
            this.matters_needing_attention = matters_needing_attention;
        }

        public String getTake_look_at_trip() {
            return take_look_at_trip;
        }

        public void setTake_look_at_trip(String take_look_at_trip) {
            this.take_look_at_trip = take_look_at_trip;
        }

        public String getIs_attention() {
            return is_attention;
        }

        public void setIs_attention(String is_attention) {
            this.is_attention = is_attention;
        }

        public List<String> getHouse_img_list() {
            return house_img_list;
        }

        public void setHouse_img_list(List<String> house_img_list) {
            this.house_img_list = house_img_list;
        }

        public List<String> getPlace_pic() {
            return place_pic;
        }

        public void setPlace_pic(List<String> place_pic) {
            this.place_pic = place_pic;
        }

        public List<SamehousesBean> getSamehouses() {
            return samehouses;
        }

        public void setSamehouses(List<SamehousesBean> samehouses) {
            this.samehouses = samehouses;
        }

        public List<GoodhousesBean> getGoodhouses() {
            return goodhouses;
        }

        public void setGoodhouses(List<GoodhousesBean> goodhouses) {
            this.goodhouses = goodhouses;
        }

        public List<GuessyourlikehousesBean> getGuessyourlikehouses() {
            return guessyourlikehouses;
        }

        public void setGuessyourlikehouses(List<GuessyourlikehousesBean> guessyourlikehouses) {
            this.guessyourlikehouses = guessyourlikehouses;
        }

        public static class HouseDescBean {
            /**
             * title : reprehenderit
             * type : eu consequ
             * provider : Duis
             * label : sunt
             */

            private String title;
            private String type;
            private String provider;
            private String label;

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getProvider() {
                return provider;
            }

            public void setProvider(String provider) {
                this.provider = provider;
            }

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }
        }

        public static class HouseDataBean {
            /**
             * totalprice : non aliqua
             * layout : mollit
             * area : magna
             */

            private String totalprice;
            private String layout;
            private String area;

            public String getTotalprice() {
                return totalprice;
            }

            public void setTotalprice(String totalprice) {
                this.totalprice = totalprice;
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
        }

        public static class AdditionalDataBean {
            /**
             * unitprice : cupidatat cillum irure
             * toward : et
             * decorate_condition : in culpa sint
             * house_tier : proident voluptate laborum aute
             * use : aute dolor in
             * age : ad enim fugiat
             * property_rights_type : ipsum consectetur ex dolor id
             * district : ut tempor in enim aute
             * type : consectetur
             * residence_booklet : enim
             * address : aliquip
             * house_num : occaecat in sed aute fugiat
             */

            private String unitprice;
            private String toward;
            private String decorate_condition;
            private String house_tier;
            private String use;
            private String age;
            private String property_rights_type;
            private String district;
            private String type;
            private String residence_booklet;
            private String address;
            private String house_num;

            public String getUnitprice() {
                return unitprice;
            }

            public void setUnitprice(String unitprice) {
                this.unitprice = unitprice;
            }

            public String getToward() {
                return toward;
            }

            public void setToward(String toward) {
                this.toward = toward;
            }

            public String getDecorate_condition() {
                return decorate_condition;
            }

            public void setDecorate_condition(String decorate_condition) {
                this.decorate_condition = decorate_condition;
            }

            public String getHouse_tier() {
                return house_tier;
            }

            public void setHouse_tier(String house_tier) {
                this.house_tier = house_tier;
            }

            public String getUse() {
                return use;
            }

            public void setUse(String use) {
                this.use = use;
            }

            public String getAge() {
                return age;
            }

            public void setAge(String age) {
                this.age = age;
            }

            public String getProperty_rights_type() {
                return property_rights_type;
            }

            public void setProperty_rights_type(String property_rights_type) {
                this.property_rights_type = property_rights_type;
            }

            public String getDistrict() {
                return district;
            }

            public void setDistrict(String district) {
                this.district = district;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public String getResidence_booklet() {
                return residence_booklet;
            }

            public void setResidence_booklet(String residence_booklet) {
                this.residence_booklet = residence_booklet;
            }

            public String getAddress() {
                return address;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public String getHouse_num() {
                return house_num;
            }

            public void setHouse_num(String house_num) {
                this.house_num = house_num;
            }
        }

        public static class TradeinfoBean {
            /**
             * taxtype : quis esse consectetur d
             * actuality : ut
             * two_taxes_assume : sunt
             * sell_house_reason : ex
             * air_source_expense : eiusmod proident Excepteur occaecat in
             * maintenance_funds : Excepteur adipisicing elit
             * review_house_type : cupidatat
             * hand_house_time : eiusmod cupidatat laboris in elit
             */

            private String taxtype;
            private String actuality;
            private String two_taxes_assume;
            private String sell_house_reason;
            private String air_source_expense;
            private String maintenance_funds;
            private String review_house_type;
            private String hand_house_time;

            public String getTaxtype() {
                return taxtype;
            }

            public void setTaxtype(String taxtype) {
                this.taxtype = taxtype;
            }

            public String getActuality() {
                return actuality;
            }

            public void setActuality(String actuality) {
                this.actuality = actuality;
            }

            public String getTwo_taxes_assume() {
                return two_taxes_assume;
            }

            public void setTwo_taxes_assume(String two_taxes_assume) {
                this.two_taxes_assume = two_taxes_assume;
            }

            public String getSell_house_reason() {
                return sell_house_reason;
            }

            public void setSell_house_reason(String sell_house_reason) {
                this.sell_house_reason = sell_house_reason;
            }

            public String getAir_source_expense() {
                return air_source_expense;
            }

            public void setAir_source_expense(String air_source_expense) {
                this.air_source_expense = air_source_expense;
            }

            public String getMaintenance_funds() {
                return maintenance_funds;
            }

            public void setMaintenance_funds(String maintenance_funds) {
                this.maintenance_funds = maintenance_funds;
            }

            public String getReview_house_type() {
                return review_house_type;
            }

            public void setReview_house_type(String review_house_type) {
                this.review_house_type = review_house_type;
            }

            public String getHand_house_time() {
                return hand_house_time;
            }

            public void setHand_house_time(String hand_house_time) {
                this.hand_house_time = hand_house_time;
            }
        }

        public static class LocationBean {
            /**
             * longitude : incididunt in dolore
             * latitude : irure velit fugiat amet ullamco
             */

            private String longitude;
            private String latitude;

            public String getLongitude() {
                return longitude;
            }

            public void setLongitude(String longitude) {
                this.longitude = longitude;
            }

            public String getLatitude() {
                return latitude;
            }

            public void setLatitude(String latitude) {
                this.latitude = latitude;
            }
        }

        public static class SamehousesBean {
            /**
             * image : nulla occaecat ut
             * title : Duis qui sint reprehenderit consequat
             * tier : exercitation sint ea nostrud
             * layout : sed quis
             * area : sed sunt labore
             * label : non incididunt
             * total_price : reprehenderit
             * unit_price : non laboris qui ut
             */

            private String image;
            private String title;
            private String tier;
            private String layout;
            private String area;
            private String label;
            private String total_price;
            private String unit_price;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTier() {
                return tier;
            }

            public void setTier(String tier) {
                this.tier = tier;
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
        }

        public static class GoodhousesBean {
            /**
             * image : deserunt eiusmod reprehenderit
             * title : in proident velit exercitation consequat
             * tier : tempor aute ex
             * layout : in
             * area : aute veniam aliqua labore exercitation
             * label : Excepteur labo
             * unit_price : Ut Excepteur
             * total_price : irure nisi magna officia
             */

            private String image;
            private String title;
            private String tier;
            private String layout;
            private String area;
            private String label;
            private String unit_price;
            private String total_price;

            public String getImage() {
                return image;
            }

            public void setImage(String image) {
                this.image = image;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }

            public String getTier() {
                return tier;
            }

            public void setTier(String tier) {
                this.tier = tier;
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

            public String getLabel() {
                return label;
            }

            public void setLabel(String label) {
                this.label = label;
            }

            public String getUnit_price() {
                return unit_price;
            }

            public void setUnit_price(String unit_price) {
                this.unit_price = unit_price;
            }

            public String getTotal_price() {
                return total_price;
            }

            public void setTotal_price(String total_price) {
                this.total_price = total_price;
            }
        }

        public static class GuessyourlikehousesBean {
            /**
             * image : in non
             * layout : amet reprehenderit
             * area : cupidatat ad
             * toward : ex
             * total_price : incididunt dolor reprehenderit sunt dolore
             * unit_price : id anim amet labore tempor
             */

            private String image;
            private String layout;
            private String area;
            private String toward;
            private String total_price;
            private String unit_price;

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

            public String getToward() {
                return toward;
            }

            public void setToward(String toward) {
                this.toward = toward;
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
        }
}
