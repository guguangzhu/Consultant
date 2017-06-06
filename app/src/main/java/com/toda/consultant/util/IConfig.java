package com.toda.consultant.util;

/**
 * Created by Zhao Haibin on 2016/2/22.
 */
public class IConfig {

    //本地环境
    public static final String URL = "http://112.74.40.248:8080/api/";
    //项目存储图片根目录
    public static String WATCH_HOUSE_FILE = "/Broker";
    // 缓存图片
    public static final String IMAGE_CACHE = "/imagecache";

    //登录
    public static final String URL_LOGIN = URL + "operator/login.do";
    //获取用户信息
    public static final String URL_GET_USER_INFO = URL + "operator/queryOperatorInfoByToken.do";
    //二手房源
    public static final String URL_SECOND_HOUSE_LIST = URL + "secondHousePersonal/queryAllSecondHousePersonalPage.do";
    //二手房源初步资料
    public static final String URL_SECOND_HOUSE_PRELIMINARY_INFO = URL + "secondHousePersonal/querySecondHousePersonalInfo.do";
    //获取城市列表
    public static final String URL_GET_CITY_LIST = URL + "area/queryAreaList.do";
    //获取区域列表
    public static final String URL_GET_RETION_BY_CITY = URL + "area/queryAreaListByCityId.do";
    //完善的二手房详情
    public static final String URL_GET_SECOND_ALL_DETAIL = URL + "secondHouse/querySecondHouseInfo.do";
    //完善的二手房小区详情
    public static final String URL_GET_SECOND_COMMUNITY = URL + "secondSubdistrict/querySecondSubdistrictInfo.do";
    //完善的二手房小区详情
    public static final String URL_GET_HOUSE_LIST = URL + "secondHouse/querySecondHousePageByOperatorToken.do";
    //完善户型信息
    public static final String URL_COMMIT_HOUSE_INFO = URL + "secondHouse/saveSecondHouse.do";
    //完善小区信息
    public static final String URL_COMMIT_ESTATE_INFO = URL + "secondSubdistrict/saveSecondSubdistrict.do";
    //完善周边信息
    public static final String URL_COMMIT_SURROUNDING_INFO = URL + "secondHouse/saveHouseAdditional.do";
    //修改头像
    public static final String URL_CHANGE_LOGO = URL + "operator/uploadLogo.do";
    //客户列表
    public static final String URL_CLIENT_LIST = URL + "user/queryUserPageByOperatorToken.do";

}
