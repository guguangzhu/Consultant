package com.toda.consultant.bean;

import java.io.Serializable;
import java.util.List;

/**
 * 二手房源实体
 * Created by guugangzhu on 2017/2/10.
 */

public class SecondHouseListBean {


    private int numPerPage;
    private int start;
    private int pageNum;
    private int totalPageCount;
    private int totalCount;
    private String orderField;
    private String orderDirection;
    private String token;
    private List<ListBean> list;

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(int totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public String getOrderField() {
        return orderField;
    }

    public void setOrderField(String orderField) {
        this.orderField = orderField;
    }

    public String getOrderDirection() {
        return orderDirection;
    }

    public void setOrderDirection(String orderDirection) {
        this.orderDirection = orderDirection;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean implements Serializable{
        /**
         * secondHousePersonalId : 16
         * proName : 浙江省
         * cityName : 杭州市
         * areaName :
         * buildingName : æ¥¼çåç§°
         * houseAddress : å·ä½ä½ç½®
         * roomType : å§å®¤
         * hallType : å®¢å
         * washroomType : åæ
         * floorNum : ç¬¬å å±
         * totalFloor : å±å å±
         * direction : æå
         * houseArea : 1111
         * designStandard : è£ä¿®æ å
         * liftType : ææ çµæ¢¯
         * houseMoney : 5555
         * realname : å§å
         * sex : æ§å«
         * tel : çµè¯
         * isChecked : 0
         * isRelated : 0
         * step1Status : 0
         * step2Status : 0
         * step3Status : 0
         */

        private int secondHousePersonalId;
        private String proName;
        private String cityName;
        private String areaName;
        private String buildingName;
        private String houseAddress;
        private String roomType;
        private String hallType;
        private String washroomType;
        private String floorNum;
        private String totalFloor;
        private String direction;
        private int houseArea;
        private String designStandard;
        private String liftType;
        private int houseMoney;
        private String realname;
        private String sex;
        private String tel;
        private int isChecked;
        private int isRelated;
        private int step1Status;
        private int step2Status;
        private int step3Status;

        public int getSecondHousePersonalId() {
            return secondHousePersonalId;
        }

        public void setSecondHousePersonalId(int secondHousePersonalId) {
            this.secondHousePersonalId = secondHousePersonalId;
        }

        public String getProName() {
            return proName;
        }

        public void setProName(String proName) {
            this.proName = proName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getBuildingName() {
            return buildingName;
        }

        public void setBuildingName(String buildingName) {
            this.buildingName = buildingName;
        }

        public String getHouseAddress() {
            return houseAddress;
        }

        public void setHouseAddress(String houseAddress) {
            this.houseAddress = houseAddress;
        }

        public String getRoomType() {
            return roomType;
        }

        public void setRoomType(String roomType) {
            this.roomType = roomType;
        }

        public String getHallType() {
            return hallType;
        }

        public void setHallType(String hallType) {
            this.hallType = hallType;
        }

        public String getWashroomType() {
            return washroomType;
        }

        public void setWashroomType(String washroomType) {
            this.washroomType = washroomType;
        }

        public String getFloorNum() {
            return floorNum;
        }

        public void setFloorNum(String floorNum) {
            this.floorNum = floorNum;
        }

        public String getTotalFloor() {
            return totalFloor;
        }

        public void setTotalFloor(String totalFloor) {
            this.totalFloor = totalFloor;
        }

        public String getDirection() {
            return direction;
        }

        public void setDirection(String direction) {
            this.direction = direction;
        }

        public int getHouseArea() {
            return houseArea;
        }

        public void setHouseArea(int houseArea) {
            this.houseArea = houseArea;
        }

        public String getDesignStandard() {
            return designStandard;
        }

        public void setDesignStandard(String designStandard) {
            this.designStandard = designStandard;
        }

        public String getLiftType() {
            return liftType;
        }

        public void setLiftType(String liftType) {
            this.liftType = liftType;
        }

        public int getHouseMoney() {
            return houseMoney;
        }

        public void setHouseMoney(int houseMoney) {
            this.houseMoney = houseMoney;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getTel() {
            return tel;
        }

        public void setTel(String tel) {
            this.tel = tel;
        }

        public int getIsChecked() {
            return isChecked;
        }

        public void setIsChecked(int isChecked) {
            this.isChecked = isChecked;
        }

        public int getIsRelated() {
            return isRelated;
        }

        public void setIsRelated(int isRelated) {
            this.isRelated = isRelated;
        }

        public int getStep1Status() {
            return step1Status;
        }

        public void setStep1Status(int step1Status) {
            this.step1Status = step1Status;
        }

        public int getStep2Status() {
            return step2Status;
        }

        public void setStep2Status(int step2Status) {
            this.step2Status = step2Status;
        }

        public int getStep3Status() {
            return step3Status;
        }

        public void setStep3Status(int step3Status) {
            this.step3Status = step3Status;
        }
    }
}
