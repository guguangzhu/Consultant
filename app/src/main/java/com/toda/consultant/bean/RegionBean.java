package com.toda.consultant.bean;

import java.util.List;

/**
 * Created by guugangzhu on 2017/2/20.
 */

public class RegionBean {

    private List<ListBean> list;

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        /**
         * areaId : 820004
         * areaName : 西湖区
         * description :
         */

        private int areaId;
        private String areaName;
        private String description;

        public int getAreaId() {
            return areaId;
        }

        public void setAreaId(int areaId) {
            this.areaId = areaId;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }
    }
}
