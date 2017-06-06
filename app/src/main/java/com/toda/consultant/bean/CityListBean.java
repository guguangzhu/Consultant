package com.toda.consultant.bean;

import java.util.List;

/**
 * Created by yangwei on 2016/11/23.
 */

public class CityListBean {


    private List<ProListBean> proList;

    public List<ProListBean> getProList() {
        return proList;
    }

    public void setProList(List<ProListBean> proList) {
        this.proList = proList;
    }

    public static class ProListBean {
        /**
         * proId : 820002
         * proName : 浙江省
         * description :
         * cityList : [{"cityId":820003,"cityName":"杭州市","description":""}]
         */

        private String proId;
        private String proName;
        private String description;
        private List<CityListBean1> cityList;

        public String getProId() {
            return proId;
        }

        public void setProId(String proId) {
            this.proId = proId;
        }

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public List<CityListBean1> getCityList() {
            return cityList;
        }

        public void setCityList(List<CityListBean1> cityList) {
            this.cityList = cityList;
        }

        public static class CityListBean1 {
            /**
             * cityId : 820003
             * cityName : 杭州市
             * description :
             */

            private int cityId;
            private String cityName;
            private String description;

            public int getCityId() {
                return cityId;
            }

            public void setCityId(int cityId) {
                this.cityId = cityId;
            }

            public String getCityName() {
                return cityName;
            }

            public void setCityName(String cityName) {
                this.cityName = cityName;
            }

            public String getDescription() {
                return description;
            }

            public void setDescription(String description) {
                this.description = description;
            }
        }
    }
}
