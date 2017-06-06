package com.toda.consultant.util;

import android.app.Activity;
import android.text.TextUtils;

import com.toda.consultant.util.cache.ShareData;


/**
 * Created by Zhao Haibin on 2016/3/15.
 */
public class UserUtils {
    public static String sessionId = "";
//    public static String sessionId = "83eaf625858c2fa599be5e63f820cae5";
    private static String userName="";
    private static String realName="";
    private static String userId="";
    private static String imToken="";

    public static String getSessionId() {
        if (TextUtils.isEmpty(sessionId)) {
            sessionId = ShareData.getShareStringData(Ikeys.KEY_SESSION);
        }
        return sessionId;
    }

    public static void setSessionId(String sessionId){
        UserUtils.sessionId=sessionId;
        ShareData.setShareStringData(Ikeys.KEY_SESSION,sessionId);
    }

    public static void setUserName(String userName){
        UserUtils.userName=userName;
        ShareData.setShareStringData(Ikeys.KEY_USER_NAME,userName);
    }

    public static String getUserName(){
        if (TextUtils.isEmpty(userName)) {
            userName = ShareData.getShareStringData(Ikeys.KEY_USER_NAME);
        }
        return userName;
    }

    public static void setRealName(String realName){
        UserUtils.realName=realName;
        ShareData.setShareStringData(Ikeys.KEY_REAL_NAME,realName);
    }

    public static String getRealName(){
        if (TextUtils.isEmpty(realName)) {
            realName = ShareData.getShareStringData(Ikeys.KEY_REAL_NAME);
        }
        return realName;
    }

    public static void setUserId(String userId){
        UserUtils.userId=userId;
        ShareData.setShareStringData(Ikeys.KEY_USER_ID,realName);
    }

    public static String getUserId(){
        if (TextUtils.isEmpty(userId)) {
            userId = ShareData.getShareStringData(Ikeys.KEY_USER_ID);
        }
        return userId;
    }


    public static void setImToken(String imToken){
        UserUtils.imToken=imToken;
        ShareData.setShareStringData(Ikeys.KEY_IM_TOKEN,imToken);
    }

    public static String getImToken(){
        if (TextUtils.isEmpty(imToken)) {
            imToken = ShareData.getShareStringData(Ikeys.KEY_IM_TOKEN);
        }
        return imToken;
    }



    public static void loginOut(Activity context) {
        setSessionId("");
        setUserName("");
        setRealName("");
        setUserId("");
        setImToken("");
        ShareData.setShareStringData(Ikeys.KEY_GESTURE_PATTERN, "");
    }

    public static void saveLoginData(String session,String userId, String userName, String realName,String imToken) {
        setSessionId(session);
        setUserName(userName);
        setRealName(realName);
        setUserId(userId);
        setImToken(imToken);
    }



    /***
     * 只判断是否登录
     * @return
     */
    public static boolean isLogin(){
        return !TextUtils.isEmpty(getSessionId());
    }

}
