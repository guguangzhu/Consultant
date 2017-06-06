package com.toda.consultant.model;

import android.content.Context;


import com.toda.consultant.util.DeviceUtils;
import com.toda.consultant.util.GsonUtil;
import com.toda.consultant.util.LogUtils;
import com.toda.consultant.util.StringUtils;
import com.toda.consultant.util.UserUtils;
import com.toda.consultant.util.crypt.AESCrypt;
import com.toda.consultant.util.crypt.MD5;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.ConnectionPool;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

/**
 * 网络管理
 * Created by Zhao Haibin on 2016/1/26.
 */
public class NetManager {
    private static final String TOKEN_KEY = "eB*aeD1F6a$7e5ea^";
    private static OkHttpClient client;
    private OkHttpClient.Builder mBuilder;
    private static Map<String, String> UA;
    private int connectTime = 15;
    private int readTime = 20;
    private int writeTime = 20;

    public NetManager() {
        if (client == null) {
            synchronized (NetManager.class) {
                if (client == null) {
                    OkHttpClient.Builder builder = new OkHttpClient.Builder()
                            .retryOnConnectionFailure(false)
                            .connectTimeout(connectTime, TimeUnit.SECONDS)
                            .readTimeout(readTime, TimeUnit.SECONDS)
                            .writeTimeout(writeTime, TimeUnit.SECONDS).connectionPool(new ConnectionPool(5, 1, TimeUnit.MINUTES));
                    try {
                        // Create a trust manager that does not validate certificate chains
                        final TrustManager[] trustAllCerts = new TrustManager[]{
                                new X509TrustManager() {
                                    @Override
                                    public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                                    }

                                    @Override
                                    public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) {
                                    }

                                    @Override
                                    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                                        return new java.security.cert.X509Certificate[]{};
                                    }
                                }
                        };

                        // Install the all-trusting trust manager
                        final SSLContext sslContext = SSLContext.getInstance("SSL");
                        sslContext.init(null, trustAllCerts, new java.security.SecureRandom());
                        // Create an ssl socket factory with our all-trusting manager
                        final javax.net.ssl.SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();

                        builder.sslSocketFactory(sslSocketFactory);
                        builder.hostnameVerifier(new HostnameVerifier() {
                            @Override
                            public boolean verify(String hostname, SSLSession session) {
                                return true;
                            }
                        });

                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                    client = builder.build();
                }
            }
        }

    }

    /***
     * get请求
     *
     * @param params   请求参数
     * @param callback 回调方法
     * @return call 请求句柄
     */
    public Call get(Context context, RequestParams params, Callback callback) {
        if (params == null) {
            return null;
        }
        paramsOperate(context, params);

        Request.Builder requestBuilder = new Request.Builder();
        requestBuilder.url(StringUtils.getQueryUrl(params.getUrl(), params.getParams()));
        if (params.getTag() != 0) {
            requestBuilder.tag(params.getTag());
        }
        if (params.getHeaders() != null) {
            for (Map.Entry<String, String> entry : params.getHeaders().entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        Request request = requestBuilder.build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    /***
     * post请求
     *
     * @param params   请求参数
     * @param callback 回调方法
     * @return call 请求句柄
     */
    public Call post(Context context, RequestParams params, Callback callback) {
        if (params == null) {
            return null;
        }
        paramsOperate(context, params);

        Request.Builder requestBuilder = new Request.Builder().
                url(params.getUrl());
//        FormBody.Builder bodyBuilder = new FormBody.Builder();
        MultipartBody.Builder requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM);
        if (params.getTag() != 0) {
            requestBuilder.tag(params.getTag());
        }
        if (params.getHeaders() != null) {
            for (Map.Entry<String, String> entry : params.getHeaders().entrySet()) {
                requestBuilder.addHeader(entry.getKey(), entry.getValue());
            }
        }
        for (Map.Entry<String, String> entry : params.getParams().entrySet()) {
            try {
                if (entry.getValue().contains("png") || entry.getValue().contains("jpg")) {
                    if(StringUtils.isEmpty(params.getFormName())){
                        throw new IllegalStateException("from 表单名不能为空");
                    }
                    File file = new File(entry.getValue());
                    RequestBody fileBody = RequestBody.create(MediaType.parse("application/octet-stream"), file);
                    requestBody.addFormDataPart(params.getFormName(),entry.getKey(),fileBody);
                } else {
                    requestBody.addFormDataPart(entry.getKey(), entry.getValue());
//                    requestBody.addFormDataPart(URLEncoder.encode(entry.getKey(), "utf-8"), URLEncoder.encode(entry.getValue(), "utf-8"));
//                    bodyBuilder.add(URLEncoder.encode(entry.getKey(), "utf-8"), URLEncoder.encode(entry.getValue(), "utf-8"));

                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        Request request = requestBuilder.post(requestBody.build()).build();
        Call call = client.newCall(request);
        call.enqueue(callback);
        return call;
    }

    public static String getGetUrl(Context context, RequestParams params) {
        if (params == null) {
            return "";
        }
        paramsOperate(context, params);
        return StringUtils.getQueryUrl(params.getUrl(), params.getParams());
    }

    private static void paramsOperate(Context context, RequestParams params) {
        addUA(context, params.getParams());
        Map<String, String> map = params.getParams();
        map.put("sign", getToken(map));
        LogUtils.e("加密前：" + StringUtils.getQueryUrl(params.getUrl(), params == null ? null : params.getParams()));
        if (params.isEncrypt()) {
            String param = AESCrypt.getInstance().encrypt(GsonUtil.toJson(map));
            map.clear();
            map.put("p", param);
        }
        LogUtils.e("加密后:" + StringUtils.getQueryUrl(params.getUrl(), params == null ? null : params.getParams()));
    }

    private static void addUA(Context context, Map<String, String> map) {
        if (map == null) {
            return;
        }
        if (UA == null) {
            UA = new HashMap<>();
        }
        UA.clear();
        UA.put("from", "android");
        UA.put("imei", DeviceUtils.getPhoneUID(context));
        UA.put("version", DeviceUtils.getCurrentAppVersionCode(context) + "");
        UA.put("timestamp",System.currentTimeMillis()+"");
//        String channel=DeviceUtils.getChannel(context);
//        if("SPI".equals(channel)){
//            UA.put("channel", "");
//        }else {
//            UA.put("channel", DeviceUtils.getChannel(context));
//        }

        if(!StringUtils.isEmpty(UserUtils.getSessionId())){
            UA.put("token",UserUtils.getSessionId());
        }
        map.putAll(UA);
    }

    private static String getToken(Map<String, String> params) {
        String token = "";
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                token += entry.getKey() + "=" + entry.getValue() + "&";
            }
        }
        token = token.substring(0, token.length() - 1);
        LogUtils.e("sign:" + token);
        token = MD5.encodeByMD5(MD5.encodeByMD5(token) + TOKEN_KEY);
        LogUtils.e("sign:" + token);
        return token;

    }

    private String getTokenStr(Map<String, String> params) {
        String token = "";
        if (params != null) {
            for (Map.Entry<String, String> entry : params.entrySet()) {
                token += entry.getKey() + "=" + entry.getValue();
            }
        }
        LogUtils.e("token:" + token);
        return token;
    }


    public void setConnectTime(int connectTime) {
        getNewBuilder().connectTimeout(connectTime, TimeUnit.SECONDS);
    }

    public void setReadTime(int readTime) {
        getNewBuilder().readTimeout(readTime, TimeUnit.SECONDS);
    }

    public void setWriteTime(int writeTime) {
        getNewBuilder().writeTimeout(writeTime, TimeUnit.SECONDS);
    }

    private void setRetryOnErr(boolean isRetry) {
        getNewBuilder().retryOnConnectionFailure(isRetry);
    }

    private OkHttpClient.Builder getNewBuilder() {
        if (mBuilder == null) {
            mBuilder = client.newBuilder();
        }
        return mBuilder;
    }

    public OkHttpClient getClient() {
        if (mBuilder == null) {
            return client;
        } else {
            return mBuilder.build();
        }
    }
}
