package com.toda.consultant.model;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;


import com.toda.consultant.util.GsonUtil;
import com.toda.consultant.util.HouseUtil;
import com.toda.consultant.util.LogUtils;
import com.toda.consultant.util.StringUtils;

import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.ConnectException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Zhao Haibin on 2016/1/27.
 */
public class HouseHandler extends BaseHandler implements Callback {
    private static final int REFRESH = 0x001;
    private Type type;
    protected static Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case REFRESH:
                    Map map = (Map) msg.obj;
                    ResponseListener listener = (ResponseListener) map.get("listener");
                    int tag = (int) map.get("tag");
                    ResultData data = (ResultData) map.get("data");
                    Call call= (Call) map.get("call");
                    listener.onRefresh(call,tag,data);
            }
        }
    };

    public HouseHandler(Context context, Type type) {
        super(context);
        this.type = type;

    }

    @Override
    public void onFailure(Call call, IOException e) {
        LogUtils.e(e);
        if (isCancel()) {
            dissDialog();
            return;
        }
        ResultData data = new ResultData();
        data.setCode(getErrCode(e));
        onData(data);
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        if (isCancel()) {
            dissDialog();
            return;
        }
        String resultStr = response.body().string();
        LogUtils.e("加密数据="+resultStr);
//        String realResult= AESCrypt.getInstance().decrypt(resultStr);
        ResultData data = new ResultData();
        try {
//            if(StringUtils.isEmpty(resultStr)){
//                showSessionErrDialog();
//                return;
//            }
            JSONObject resJson = new JSONObject(resultStr);
            String resStr=resJson.optString("response");

//            String realResult= AESCrypt.getInstance().decrypt(URLDecoder.decode(resStr,"utf-8"));
//            LogUtils.e("解密数据="+realResult);
//            JSONObject json = new JSONObject(realResult);
            String code = resJson.optString("errorCode");
            String msg=resJson.optString("errorMsg");
            if(ErrorCode.SESSION_ERR.equals(code)&&!StringUtils.isEmpty(msg)&&msg.contains("token")){
                showSessionErrDialog();
                return;
            }
            data.setCode(TextUtils.isEmpty(code) ? "" : code);
            data.setMsg(msg);
            if(TextUtils.isEmpty(data.getMsg())||data.getMsg().equals("null")){
                data.setMsg("");
            }
            if (type != null&&resJson.has("data")&&!resJson.getString("data").equals("[]")) {
                data.setData(GsonUtil.fromJson(resJson.getString("data"), type));
            }
        } catch (Exception e) {
            e.printStackTrace();
            data.setCode(ErrorCode.JSON_ERR);
        }
        onData(data);
    }

    protected void onData(final ResultData data) {
        dissDialog();
        if(context==null){
            return;
        }
        if((context instanceof Activity)&&((Activity) context).isFinishing()){
            return;
        }
        if (listener != null) {
            Map map = new HashMap();
            map.put("listener", listener);
            map.put("tag", tag);
            map.put("data", data);
            map.put("call",call);
            handler.sendMessage(handler.obtainMessage(REFRESH, map));
        }
    }

    /***
     * 获取错误码
     *
     * @param e
     * @return
     */
    public String getErrCode(Throwable e) {
        String code = "";
        if (e instanceof SocketTimeoutException) {
            //服务器连接超时
            code = ErrorCode.SOCKET_TIME_OUT;
        } else if (e instanceof UnknownHostException) {
            //域名解析错误
            code = ErrorCode.UNKNOW_HOST_ERR;
        } else if (e instanceof ConnectException) {
            code = ErrorCode.CONNECT_ERR;
        } else if (e instanceof SocketException) {
            //网络连接失败
            code = ErrorCode.CONNECT_ERR;
        } else {
            code = ErrorCode.SYSTEM_ERR;
        }
        return code;
    }

    private void showSessionErrDialog(){
        handler.post(new Runnable() {
            @Override
            public void run() {
                HouseUtil.showLoginErrDialog(context);
            }
        });

    }

}
