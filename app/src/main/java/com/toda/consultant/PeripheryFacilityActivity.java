package com.toda.consultant;

import android.os.Bundle;
import android.widget.TextView;


import com.toda.consultant.bean.SecondDetailBean;
import com.toda.consultant.util.Ikeys;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangwei on 2016/12/8.
 */

public class PeripheryFacilityActivity extends BaseActivity {

    @BindView(R.id.tv_jiaotongsheshi)
    TextView tvJiaotongsheshi;
    @BindView(R.id.tv_xuexiao)
    TextView tvXuexiao;
    @BindView(R.id.tv_chaoshicaichang)
    TextView tvChaoshicaichang;
    @BindView(R.id.tv_yiyuanyaofang)
    TextView tvYiyuanyaofang;
    @BindView(R.id.tv_yinhang)
    TextView tvYinhang;

    private SecondDetailBean secondDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periphery_facility);
        initView();
        getData();
        setUi();
    }

    private void setUi() {
        setTitle("周边配套");
        tvJiaotongsheshi.setText(secondDetailBean.getTraffic());
        tvXuexiao.setText(secondDetailBean.getSchool());
        tvChaoshicaichang.setText(secondDetailBean.getMarket());
        tvYiyuanyaofang.setText(secondDetailBean.getHospital());
        tvYinhang.setText(secondDetailBean.getBank());
    }

    private void getData() {
        Bundle bd = getIntentData();
        if (bd == null) return;
        secondDetailBean = (SecondDetailBean) bd.getSerializable(Ikeys.KEY_DATA);
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }
}
