package com.toda.consultant;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.toda.consultant.model.HouseHandler;
import com.toda.consultant.model.RequestParams;
import com.toda.consultant.util.ActivityManagerTool;
import com.toda.consultant.util.HandlerRequestErr;
import com.toda.consultant.util.Ikeys;
import com.toda.consultant.app.BrokerApplication;
import com.toda.consultant.model.ResponseListener;
import com.toda.consultant.model.ResultData;
import com.toda.consultant.util.StringUtils;
import com.toda.consultant.view.TopBar;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by guugangzhu on 2016/9/23.
 */

public abstract class BaseActivity extends AppCompatActivity implements ResponseListener,TopBar.OnTopBarClickListener ,Ikeys {
    private Context mContext;
    private BrokerApplication app;
    public TopBar topBar;
    private RelativeLayout cltParent;
    private List<Call> calls;
    private View contentView;
    private Dialog dialog;
    protected final int pageSize = 10;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext=this;
        ActivityManagerTool.getInstance().addActivity(this);
        app= (BrokerApplication) getApplication();
        setParentView(R.layout.activity_base);
    }

    /***
     * 设置父布局，即父类的setContentView
     *
     * @param resourceId
     */
    public void setParentView(int resourceId) {
//        super.setContentView(resourceId);
        View view= LayoutInflater.from(this).inflate(resourceId, null);
        topBar = (TopBar) view.findViewById(R.id.topbar);
        topBar.setOnTopBarClickListener(this);
        topBar.setTitleLeftImg(R.mipmap.ic_arrow_back);
        cltParent = (RelativeLayout) view.findViewById(R.id.clt_parent);

    }
    @Override
    public void setContentView(@LayoutRes int layoutResID) {
//        super.setContentView(layoutResID);
        contentView = LayoutInflater.from(this).inflate(layoutResID, null);
        if (contentView != null){
            RelativeLayout.LayoutParams p = new RelativeLayout.LayoutParams(ViewGroup.
                    LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//            p.setBehavior(new AppBarLayout.ScrollingViewBehavior());
            p.addRule(RelativeLayout.BELOW, R.id.topbar);
            cltParent.addView(contentView, p);
        }
//        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//        llRoot.addView(topBar,params);
//        LinearLayout.LayoutParams paramsContent=new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,0);
//        paramsContent.weight=1;
//        llRoot.addView(contentView,paramsContent);
        setContentView(cltParent, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
    }

    public abstract void initView();

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
        Toast.makeText(this, text, duration).show();
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
        if (clas == null) {
            return;
        }
        Intent intent = new Intent(this, clas);
        if (data != null) {
            intent.putExtra(Ikeys.KEY_DATA, data);
        }
        startActivityForResult(intent, requestCode);
    }


    /***
     * 设置toolbar title
     *
     * @param title
     */
    public void setTitle(String title) {
        topBar.setTitleText(title);
    }

    /***
     * 设置toolbar左侧图标
     *
     * @param drawable
     */
    public void setTopBarLeftImg(int drawable) {
        topBar.setTitleLeftImg(drawable);
    }

    /***
     * 设置toolbar左侧图标
     *
     * @param drawable
     */
    public void setTopBarRightImg(int drawable) {
        topBar.setTitleRightImg(drawable);
    }

    /***
     * 设置topbar右侧文字
     * @param str
     */
    public void setTopBarRightText(String str){
        topBar.setTitleRightText(str);
    }

    /***
     * 设置topbar左侧文字
     * @param str
     */
    public void setTopBarLeftText(String str){
        topBar.setTitleLeftText(str);
    }

    /***
     * 获取intent传入值
     * @return bundle
     */
    protected Bundle getIntentData(){
        Bundle bundle=getIntent().getBundleExtra(Ikeys.KEY_DATA);
        if(bundle==null){
            bundle=new Bundle();
        }
        return bundle;
    }

    /***
     * 获取String传入值，如果为空返回空字符串
     * @param key
     * @return
     */
    protected String getBundleStr(String key){
        Bundle bundle=getIntent().getBundleExtra(Ikeys.KEY_DATA);
        if(bundle==null){
            return "";
        }
        return bundle.getString(key,"");
    }

    @Override
    public void onRefresh(Call call, int tag, ResultData data) {

    }

    @Override
    public void onTopLeftClick() {
        this.finish();
    }

    @Override
    public void onTopRightClick() {

    }

    @Override
    public void onTopLeftSecondClick() {

    }

    @Override
    public void onTopCenterClick() {

    }

    @Override
    public void onTopRightSecondClick() {

    }

    protected void showDialog() {
        try {
            if (dialog == null) {
                dialog = new ProgressDialog(this);
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

    protected HouseHandler startRequest(int flag, RequestParams params, Type type) {
        return startRequest(flag, params, type, true);
    }

    protected HouseHandler startRequest(int flag, RequestParams params, Type type, boolean showDialog) {
        if (!(this instanceof ResponseListener)) {
            return null;
        }
        HouseHandler handler = new HouseHandler(this, type);
        Call call = handler.start(flag, params, (ResponseListener) this, showDialog);
        addCall(call);
        return handler;
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

    /***
     * 网络回调处理，提示错误信息
     *
     * @param data
     * @return
     */
    public boolean handlerRequestErr(ResultData data) {
        return HandlerRequestErr.handlerRequestErr(this, data);
    }

    /***
     * 网络回调处理，提示错误信息
     *
     * @param data
     * @param defaultStr 无错误信息时的默认提示信息
     * @return
     */
    public boolean handlerRequestErr(ResultData data, String defaultStr) {
        return HandlerRequestErr.handlerRequestErr(this, data, defaultStr);
    }

    /***
     * 网络回调处理，提示错误信息
     *
     * @param data
     * @param isTips 是否提示错误信息
     * @return
     */
    public boolean handlerRequestErr(ResultData data, boolean isTips) {
        return HandlerRequestErr.handlerRequestErr(this, data, isTips);
    }

    /***
     * string转boolean，1为true，其他为false
     *
     * @param str
     * @return
     */
    public boolean string2Boolean(String str) {
        return StringUtils.string2Boolean(str);
    }

    /***
     * int转boolean，1为true，其他为false
     *
     * @param num
     * @return
     */
    public boolean int2Boolean(int num) {
        return StringUtils.int2Boolean(num);
    }

    /***
     * string转int，
     *
     * @param str
     * @return
     */
    public int string2Int(String str) {
        return StringUtils.string2Integer(str);
    }

}
