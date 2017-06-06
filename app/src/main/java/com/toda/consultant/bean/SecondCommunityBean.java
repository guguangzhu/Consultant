package com.toda.consultant.bean;

import java.io.Serializable;

/**
 * Created by yangwei on 2016/12/6.
 */

public class SecondCommunityBean implements Serializable{

    /**
     * secondSubdistrictId : 1
     * developer : 开发商
     * houseCorp : 物业管理公司
     * buildingName : 楼盘名称
     * averagePrice : 123.32
     * openTime : 2016-10-02 00:00:00
     * inTime : 2016-10-27 00:00:00
     * buidlingType : 多层,小高层
     * houseType : 住宅用地
     * buildingArea : 123.32
     * landArea : 123.32
     * greenPercent : 123.32
     * volumeRatio : 123.32
     * carNum : 11
     * peopleNum : 11
     * buildingDescription : <p><span style="font-family: 微软雅黑; font-size: 12px; line-height: 34px; text-align: right;">楼盘介绍</span></p>
     */

    private int secondSubdistrictId;
    private String developer;
    private String houseCorp;
    private String buildingName;
    private double averagePrice;
    private String openTime;
    private String inTime;
    private String buidlingType;
    private String houseType;
    private double buildingArea;
    private double landArea;
    private double greenPercent;
    private double volumeRatio;
    private int carNum;
    private int peopleNum;
    private String buildingDescription;

    public int getSecondSubdistrictId() {
        return secondSubdistrictId;
    }

    public void setSecondSubdistrictId(int secondSubdistrictId) {
        this.secondSubdistrictId = secondSubdistrictId;
    }

    public String getDeveloper() {
        return developer;
    }

    public void setDeveloper(String developer) {
        this.developer = developer;
    }

    public String getHouseCorp() {
        return houseCorp;
    }

    public void setHouseCorp(String houseCorp) {
        this.houseCorp = houseCorp;
    }

    public String getBuildingName() {
        return buildingName;
    }

    public void setBuildingName(String buildingName) {
        this.buildingName = buildingName;
    }

    public double getAveragePrice() {
        return averagePrice;
    }

    public void setAveragePrice(double averagePrice) {
        this.averagePrice = averagePrice;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getInTime() {
        return inTime;
    }

    public void setInTime(String inTime) {
        this.inTime = inTime;
    }

    public String getBuidlingType() {
        return buidlingType;
    }

    public void setBuidlingType(String buidlingType) {
        this.buidlingType = buidlingType;
    }

    public String getHouseType() {
        return houseType;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public double getBuildingArea() {
        return buildingArea;
    }

    public void setBuildingArea(double buildingArea) {
        this.buildingArea = buildingArea;
    }

    public double getLandArea() {
        return landArea;
    }

    public void setLandArea(double landArea) {
        this.landArea = landArea;
    }

    public double getGreenPercent() {
        return greenPercent;
    }

    public void setGreenPercent(double greenPercent) {
        this.greenPercent = greenPercent;
    }

    public double getVolumeRatio() {
        return volumeRatio;
    }

    public void setVolumeRatio(double volumeRatio) {
        this.volumeRatio = volumeRatio;
    }

    public int getCarNum() {
        return carNum;
    }

    public void setCarNum(int carNum) {
        this.carNum = carNum;
    }

    public int getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(int peopleNum) {
        this.peopleNum = peopleNum;
    }

    public String getBuildingDescription() {
        return buildingDescription;
    }

    public void setBuildingDescription(String buildingDescription) {
        this.buildingDescription = buildingDescription;
    }
}
