package com.toda.consultant;

import android.os.Bundle;
import android.widget.TextView;


import com.toda.consultant.bean.SecondDetailBean;
import com.toda.consultant.util.Ikeys;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 二手房房源详情
 * Created by yangwei on 2016/12/6.
 */

public class HousingProfileActivity extends BaseActivity {

    @BindView(R.id.tv_house_profile)
    TextView tvHouseProfile;
    @BindView(R.id.tv_leibie)
    TextView tvLeibie;
    @BindView(R.id.tv_woshi)
    TextView tvWoshi;
    @BindView(R.id.tv_keting)
    TextView tvKeting;
    @BindView(R.id.tv_chufang)
    TextView tvChufang;
    @BindView(R.id.tv_cesuo)
    TextView tvCesuo;
    @BindView(R.id.tv_yangtai)
    TextView tvYangtai;
    @BindView(R.id.tv_huayuan)
    TextView tvHuayuan;
    @BindView(R.id.tv_jianzhumianji)
    TextView tvJianzhumianji;
    @BindView(R.id.tv_taoneimianji)
    TextView tvTaoneimianji;
    @BindView(R.id.tv_louceng)
    TextView tvLouceng;
    @BindView(R.id.tv_junjia)
    TextView tvJunjia;
    @BindView(R.id.tv_zongjia)
    TextView tvZongjia;
    @BindView(R.id.tv_zhuangxiubiaozhun)
    TextView tvZhuangxiubiaozhun;
    @BindView(R.id.tv_chanquannianxian)
    TextView tvChanquannianxian;
    @BindView(R.id.tv_youwudianti)
    TextView tvYouwudianti;
    @BindView(R.id.tv_chaoxiang)
    TextView tvChaoxiang;

    private SecondDetailBean secondDetailBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_profile);
        initView();
        getData();
        setUi();
    }

    private void setUi() {
        tvLeibie.setText(secondDetailBean.getHouseType());
        tvWoshi.setText(secondDetailBean.getRoomType());
        tvKeting.setText(secondDetailBean.getHallType());
        tvChufang.setText(secondDetailBean.getCookroomType());
        tvCesuo.setText(secondDetailBean.getWashroomType());
        tvYangtai.setText(secondDetailBean.getShineroomType());
        tvHuayuan.setText(secondDetailBean.getGardenType());
        tvJianzhumianji.setText(secondDetailBean.getHouseArea() + "㎡");
        tvTaoneimianji.setText(secondDetailBean.getRoomArea() + "㎡");
        tvLouceng.setText("暂无");
        tvJunjia.setText(secondDetailBean.getAveragePrice() + "元/㎡");
        tvZongjia.setText(secondDetailBean.getHouseMoney() + "万元/套");
        tvZhuangxiubiaozhun.setText(secondDetailBean.getDesignStandard());
        tvChanquannianxian.setText(secondDetailBean.getOwnYear());
        tvYouwudianti.setText(secondDetailBean.getLiftType());
        tvChaoxiang.setText(secondDetailBean.getDirection());
    }

    private void getData() {
        Bundle bd = getIntentData();
        if (bd == null) return;
        secondDetailBean = (SecondDetailBean) bd.getSerializable(Ikeys.KEY_DATA);
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        setTitle("房源介绍");
    }


}
