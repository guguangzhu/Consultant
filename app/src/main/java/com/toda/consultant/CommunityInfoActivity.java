package com.toda.consultant;

import android.os.Bundle;
import android.widget.TextView;

import com.toda.consultant.bean.SecondCommunityBean;
import com.toda.consultant.bean.SecondDetailBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangwei on 2016/12/7.
 */

public class CommunityInfoActivity extends BaseActivity {
    public static final String KEY_SECOND_DETAIL = "key_second_detail";
    public static final String KEY_SECOND_COMMUNITY = "key_second_community";

    @BindView(R.id.tv_comminity_info)
    TextView tvComminityInfo;
    @BindView(R.id.tv_kaifashang)
    TextView tvKaifashang;
    @BindView(R.id.tv_wuye)
    TextView tvWuye;
    @BindView(R.id.tv_loupanmingcheng)
    TextView tvLoupanmingcheng;
    @BindView(R.id.tv_junjia)
    TextView tvJunjia;
    @BindView(R.id.tv_kaipanshijian)
    TextView tvKaipanshijian;
    @BindView(R.id.tv_jiaofangshijian)
    TextView tvJiaofangshijian;
    @BindView(R.id.tv_zhuangxiubiaozhun)
    TextView tvZhuangxiubiaozhun;
    @BindView(R.id.tv_jianzhuleixing)
    TextView tvJianzhuleixing;
    @BindView(R.id.tv_wuyeleixing)
    TextView tvWuyeleixing;
    @BindView(R.id.tv_chanquannianxian)
    TextView tvChanquannianxian;
    @BindView(R.id.tv_jianzhumianji)
    TextView tvJianzhumianji;
    @BindView(R.id.tv_zhandimianji)
    TextView tvZhandimianji;
    @BindView(R.id.tv_lvhualv)
    TextView tvLvhualv;
    @BindView(R.id.tv_rongjilv)
    TextView tvRongjilv;
    @BindView(R.id.tv_cheweishu)
    TextView tvCheweishu;
    @BindView(R.id.tv_hushu)
    TextView tvHushu;
    @BindView(R.id.tv_wuyefei)
    TextView tvWuyefei;
    private SecondDetailBean secondDetailBean;
    private SecondCommunityBean secondCommunityBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_community_info);
        initView();
        getData();
        setUi();
    }

    private void setUi() {
        setTitle("小区简介");
        tvComminityInfo.setText(secondCommunityBean.getBuildingDescription());
        tvKaifashang.setText(secondCommunityBean.getDeveloper());
        tvWuye.setText(secondCommunityBean.getHouseCorp());
        tvLoupanmingcheng.setText(secondCommunityBean.getBuildingName());
        tvJunjia.setText(secondCommunityBean.getAveragePrice() + "元/㎡");
        tvKaipanshijian.setText(secondCommunityBean.getOpenTime());
        tvJiaofangshijian.setText(secondCommunityBean.getInTime());
        tvZhuangxiubiaozhun.setText(secondDetailBean.getDesignStandard());
        tvJianzhuleixing.setText(secondCommunityBean.getBuidlingType());
        tvWuyeleixing.setText(secondCommunityBean.getHouseType());
        tvChanquannianxian.setText(secondDetailBean.getOwnYear());
        tvJianzhumianji.setText(secondCommunityBean.getBuildingArea() + "㎡");
        tvZhandimianji.setText(secondCommunityBean.getLandArea() + "㎡");
        tvLvhualv.setText(secondCommunityBean.getGreenPercent() + "%");
        tvRongjilv.setText(secondCommunityBean.getVolumeRatio() + "%");
        tvWuyefei.setText("暂无");
        tvCheweishu.setText(secondCommunityBean.getCarNum() + "");
        tvHushu.setText(secondCommunityBean.getPeopleNum() + "");
    }

    private void getData() {
        Bundle bd = getIntentData();
        if (bd == null) return;
        secondDetailBean = (SecondDetailBean) bd.getSerializable(KEY_SECOND_DETAIL);
//        secondCommunityBean = (SecondCommunityBean) bd.getSerializable(KEY_SECOND_COMMUNITY);
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
        setTitle("小区简介");
    }
}
