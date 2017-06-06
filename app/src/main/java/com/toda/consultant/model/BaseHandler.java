package com.toda.consultant.model;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;


import com.toda.consultant.util.UtilTools;

import java.io.IOException;
import java.net.ConnectException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * Created by Zhao Haibin on 2016/1/27.
 */
public class BaseHandler implements Callback {
    protected Context context;
    protected NetManager manager;
    protected ResponseListener listener;
    protected Call call;
    private Dialog dialog;
    protected int tag;
    private boolean isDialog;
    private boolean cancel;

    public BaseHandler(Context context) {
        this.context = context;
        manager = new NetManager();
    }

    public Call start(int tag, RequestParams params, ResponseListener listener) {
        return start(tag, params, listener, true);
    }

    public Call start(int tag, RequestParams params, ResponseListener listener, boolean isDialog) {
        if (params == null) {
            return null;
        }
        this.tag = tag;
        this.isDialog = isDialog;
        this.listener = listener;
        if(!hasNet()){
            return null;
        }
        onStart();
        if (params.isPost()) {
            call = manager.post(context,params, this);
        } else {
            call = manager.get(context,params, this);
        }
        return call;
    }

    private boolean hasNet(){
        if(!UtilTools.isOpenNetwork(context)){
           onFailure(call, new ConnectException("网络不可用"));
           return false;
        }
        return true;

    }

    protected void onStart() {
        if (isDialog){
            showDialog();
         }
    }

    public void cancel() {
        if (call != null && !call.isCanceled()) {
            call.cancel();
        }
        cancel=true;
    }

    public boolean isCancel() {
        return cancel;
    }

    @Override
    public void onFailure(Call call, IOException e) {
        call=null;
    }

    @Override
    public void onResponse(Call call, Response response) throws IOException {
        call=null;
    }

    protected void showDialog() {
        if (!isDialog) {
            return;
        }
        try {
            if(dialog==null) {
                dialog = new ProgressDialog(context);
            }
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected void dissDialog() {
        if (dialog != null) {
            try {
               dialog.dismiss();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean isDialog() {
        return isDialog;
    }


    public void setDialog(Dialog dialog){
        this.dialog=dialog;
    }
}
