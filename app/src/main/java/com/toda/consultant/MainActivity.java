package com.toda.consultant;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.FragmentTabHost;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.toda.consultant.util.TabDb;

import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;


public class MainActivity extends BaseActivity implements TabHost.OnTabChangeListener {
    private FragmentTabHost tabHost;
    private String[] tabs;
    private int currentIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tabHost = (FragmentTabHost) super.findViewById(android.R.id.tabhost);
        tabHost.setup(this, super.getSupportFragmentManager()
                , R.id.contentLayout);
        tabHost.getTabWidget().setDividerDrawable(null);
        tabHost.setOnTabChangedListener(this);
        initTab();
        initView();
        connect("xD1kX2U06GNgNF/KPDoSWx+wCT3MXX5+v46TMgkRxL5RTg8RfBvpQZDyaEaSi5sf00cc3MnwiEjG8L+bECrPDg==");
    }

    @Override
    public void initView() {
        topBar.getTopBarLeftTextView().setBackgroundResource(0);
    }

    private void initTab() {
        tabs= TabDb.getTabsTxt();
        for (int i = 0; i < tabs.length; i++) {
            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabs[i]).setIndicator(getTabView(i));
            tabHost.addTab(tabSpec, TabDb.getFragments()[i], null);
            tabHost.setTag(i);
        }
    }

    private View getTabView(int idx) {
        View view = LayoutInflater.from(this).inflate(R.layout.footer_tabs, null);
        ((TextView) view.findViewById(R.id.tvTab)).setText(TabDb.getTabsTxt()[idx]);
        if (idx == 0) {

            ((TextView) view.findViewById(R.id.tvTab)).setTextColor(Color.RED);
            ((ImageView) view.findViewById(R.id.ivImg)).setImageResource(TabDb.getTabsImgLight()[idx]);
        } else {
            ((ImageView) view.findViewById(R.id.ivImg)).setImageResource(TabDb.getTabsImg()[idx]);
        }
        return view;
    }

    @Override
    public void onTopRightClick() {
        super.onTopRightClick();
        if (currentIndex==1){
           goPage(MyHouseListActivity.class);
        }
    }

    @Override
    public void onTabChanged(String tabId) {
        // TODO Auto-generated method stub
        updateTab();
        updateTitle(tabId);
    }

    private void updateTitle(String tabId){
        switch (tabId){
            case "消息":
                setTitle("消息列表");
                setTopBarRightText("");
                currentIndex=0;
                break;
            case "二手房源":
                setTitle("房源");
                setTopBarRightText("我的房源");
                currentIndex=1;
                break;
            case "客户":
                setTitle("客户列表");
                setTopBarRightText("");
                currentIndex=2;
                break;
            case "我的":
                setTitle("我的");
                setTopBarRightText("");
                currentIndex=3;
                break;
            default:
                setTitle("消息列表");
                setTopBarRightText("");
                break;
        }
    }

    private void updateTab() {
        TabWidget tabw = tabHost.getTabWidget();
        for (int i = 0; i < tabw.getChildCount(); i++) {
            View view = tabw.getChildAt(i);
            ImageView iv = (ImageView) view.findViewById(R.id.ivImg);
            if (i == tabHost.getCurrentTab()) {
                ((TextView) view.findViewById(R.id.tvTab)).setTextColor(Color.RED);
                iv.setImageResource(TabDb.getTabsImgLight()[i]);
            } else {
                ((TextView) view.findViewById(R.id.tvTab)).setTextColor(getResources().getColor(R.color.foot_txt_gray));
                iv.setImageResource(TabDb.getTabsImg()[i]);
            }

        }
    }

    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.e("MainActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {

                    Log.e("MainActivity", "--onSuccess" + userid);
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.e("MainActivity", "--onError" + errorCode);
                }
            });
        }
    }

    private String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager
                .getRunningAppProcesses()) {
            if (appProcess.pid == pid) {

                return appProcess.processName;
            }
        }
        return null;
    }
}
