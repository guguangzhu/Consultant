package com.toda.consultant.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Toast;

import com.toda.consultant.model.HouseHandler;
import com.toda.consultant.model.RequestParams;
import com.toda.consultant.model.ResponseListener;
import com.toda.consultant.model.ResultData;
import com.toda.consultant.util.HandlerRequestErr;
import com.toda.consultant.util.Ikeys;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by guugangzhu on 2016/11/1.
 */

public abstract class BaseFragment extends Fragment implements ResponseListener, Ikeys {

    private List<Call> calls;
    protected final int pageSize = 10;

    public abstract void initView(View view);

    // Fragment当前状态是否可见
    private boolean isVisible;
    //界面是否准备好
    private boolean isPrepared;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        prepareAndVisible();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            prepareAndVisible();
        } else {
            isVisible = false;
        }
    }

    /***
     * 界面准备完毕并且界面可见
     */
    private void prepareAndVisible() {
        if (isPrepared && isVisible) {
            onPreAndVisible();
        }
    }

    /***
     * 界面准备完毕并且界面可见时触发的方法
     */
    protected void onPreAndVisible() {

    }

    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
        isPrepared = false;
    }

    protected HouseHandler startRequest(int flag, RequestParams params, Type type) {
        return startRequest(flag, params, type, true);
    }

    protected HouseHandler startRequest(int flag, RequestParams params, Type type, boolean showDialog) {
        if (!(this instanceof ResponseListener)) {
            return null;
        }
        HouseHandler handler = new HouseHandler(getActivity(), type);
        Call call = handler.start(flag, params, (ResponseListener) this, showDialog);
        addCall(call);
        return handler;
    }

    @Override
    public void onRefresh(Call call, int tag, ResultData data) {
        cancelCall(call);
    }

    private void addCall(Call call) {
        if (call == null) {
            return;
        }
        if (calls == null) {
            calls = new ArrayList<>();
        }
        calls.add(call);
    }

    private void cancelCall(Call call) {
        if (calls != null && calls.size() != 0) {
            if (call != null) {
                if (!call.isCanceled()) {
                    call.cancel();
                }
                calls.remove(call);
            } else {
                for (Call cal : calls) {
                    if (cal != null) {
                        cal.cancel();
                    }
                }
            }
            calls.clear();
        }
    }

    /***
     * 网络回调处理，提示错误信息
     *
     * @param data
     * @param isTips 是否提示错误信息
     * @return
     */
    public boolean handlerRequestErr(ResultData data, boolean isTips) {
        return HandlerRequestErr.handlerRequestErr(getActivity(), data, isTips);
    }

    /***
     * 网络回调处理，提示错误信息
     *
     * @param data
     * @return
     */
    public boolean handlerRequestErr(ResultData data) {
        return HandlerRequestErr.handlerRequestErr(getActivity(), data);
    }

    /***
     * 跳转到指定页面
     *
     * @param clas 指定页面
     */
    public void goPage(Class<? extends Activity> clas) {
        goPage(clas, null);
    }

    /***
     * 跳转到指定页面
     *
     * @param clas 指定页面
     * @param data 传入数据
     */
    protected void goPage(Class<? extends Activity> clas, Bundle data) {
        goPage(clas, data, -1);
    }

    /***
     * 跳转到指定页面
     *
     * @param clas        指定页面
     * @param data        传入数据
     * @param requestCode 请求值
     */
    protected void goPage(Class<? extends Activity> clas, Bundle data, int requestCode) {
        if (clas == null || getActivity() == null) {
            return;
        }
        Intent intent = new Intent(getActivity(), clas);
        if (data != null) {
            intent.putExtra(Ikeys.KEY_DATA, data);
        }
        startActivityForResult(intent, requestCode);
    }

    /***
     * 显示toast，默认显示时间为LENGTH_SHORT
     *
     * @param msg 显示信息
     */
    public void toast(String msg) {
        toast(msg, Toast.LENGTH_SHORT);
    }

    /**
     * 显示Toast，防止一直点击弹出Toast
     *
     * @param text     显示的信息
     * @param duration 信息显示的时间
     */
    public void toast(String text, int duration) {
        Toast.makeText(getActivity(), text, duration).show();
    }
}
