package com.toda.consultant.model;

import okhttp3.Call;

/**
 * Created by Zhao Haibin on 2016/1/28.
 */
public interface ResponseListener {
    public void onRefresh(Call call, int tag, ResultData data);
}
