package com.toda.consultant.fragment;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.toda.consultant.CommunityInfoActivity;
import com.toda.consultant.HousingProfileActivity;
import com.toda.consultant.PeripheryFacilityActivity;
import com.toda.consultant.PositionPeripheryMapActivity;
import com.toda.consultant.R;
import com.toda.consultant.bean.SecondCommunityBean;
import com.toda.consultant.bean.SecondDetailBean;
import com.toda.consultant.util.Ikeys;
import com.toda.consultant.view.UnitDescriptionLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by yangwei on 2016/12/5.
 */

public class SecondUnitInfoFragment extends BaseFragment implements View.OnClickListener {

    public static final String KEY_SECOND_DETAIL = "key_second_detail";
    public static final String KEY_SECOND_COMMUNITY = "key_second_community";

    @BindView(R.id.map)
    MapView map;
    @BindView(R.id.udlt_house_more)
    UnitDescriptionLayout udltHouseMore;
    @BindView(R.id.tv_house_jianzhuianji)
    TextView tvHouseJianzhuianji;
    @BindView(R.id.tv_taoneimianji)
    TextView tvTaoneimianji;
    @BindView(R.id.tv_junjia)
    TextView tvJunjia;
    @BindView(R.id.tv_cenggao)
    TextView tvCenggao;
    @BindView(R.id.tv_zhuangxiubiaozhun)
    TextView tvZhuangxiubiaozhun;
    @BindView(R.id.tv_chanquannianxian)
    TextView tvChanquannianxian;
    @BindView(R.id.tv_chaoxiang)
    TextView tvChaoxiang;
    @BindView(R.id.udlt_community_more)
    UnitDescriptionLayout udltCommunityMore;
    @BindView(R.id.tv_kaifashang)
    TextView tvKaifashang;
    @BindView(R.id.tv_wuye)
    TextView tvWuye;
    @BindView(R.id.tv_jiaofangshijian)
    TextView tvJiaofangshijian;
    @BindView(R.id.tv_community_jianzhumianji)
    TextView tvCommunityJianzhumianji;
    @BindView(R.id.tv_zhandimianji)
    TextView tvZhandimianji;
    @BindView(R.id.tv_lvhualv)
    TextView tvLvhualv;
    @BindView(R.id.tv_rongjilv)
    TextView tvRongjilv;
    @BindView(R.id.udlt_periphery_more)
    UnitDescriptionLayout udltPeripheryMore;
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
    @BindView(R.id.tv_community_info)
    TextView tvCommunityInfo;
    @BindView(R.id.tv_community_tip)
    TextView tvCommunityTip;
    @BindView(R.id.tv_periphery_tip)
    TextView tvPeripheryTip;
    @BindView(R.id.id_stickynavlayout_innerscrollview)
    ScrollView idStickynavlayoutInnerscrollview;
    @BindView(R.id.tv_house_address)
    TextView tvHouseAddress;

    private SecondDetailBean secondDetailBean;
    private SecondCommunityBean secondCommunityBean;

    private AMap aMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_second_unit_info, null);
        ButterKnife.bind(this, view);
        initView(view);
        map.onCreate(savedInstanceState);
        getData();
        setUi();
        return view;
    }

    private void setUi() {
        tvHouseAddress.setText(secondDetailBean.getHouseAddress());
        setUpMap();
        tvHouseJianzhuianji.setText(secondDetailBean.getHouseArea() + "㎡");
        tvTaoneimianji.setText(secondDetailBean.getRoomArea() + "㎡");
        tvJunjia.setText(secondDetailBean.getAveragePrice() + "元/㎡");
        tvZhuangxiubiaozhun.setText(secondDetailBean.getDesignStandard());
        tvChanquannianxian.setText(secondDetailBean.getOwnYear() + "年");
        tvChaoxiang.setText(secondDetailBean.getDirection());
        tvCommunityInfo.setText(secondCommunityBean.getBuildingDescription());
        tvKaifashang.setText(secondCommunityBean.getDeveloper());
        tvWuye.setText(secondCommunityBean.getHouseCorp());
        tvJiaofangshijian.setText(secondCommunityBean.getInTime());
        tvCommunityJianzhumianji.setText(secondCommunityBean.getBuildingArea() + "㎡");
        tvZhandimianji.setText(secondCommunityBean.getLandArea() + "㎡");
        tvLvhualv.setText(secondCommunityBean.getGreenPercent() + "%");
        tvRongjilv.setText(secondCommunityBean.getVolumeRatio() + "%");
        tvJiaotongsheshi.setText(secondDetailBean.getTraffic());
        tvXuexiao.setText(secondDetailBean.getSchool());
        tvChaoshicaichang.setText(secondDetailBean.getMarket());
        tvYiyuanyaofang.setText(secondDetailBean.getHospital());
        tvYinhang.setText(secondDetailBean.getBank());
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        //绘制marker
        aMap.addMarker(new MarkerOptions()
                .position(new LatLng(secondDetailBean.getHouseAddressY(), secondDetailBean.getHouseAddressX()))
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.mipmap.ic_location)))
                .draggable(true));
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(
                secondDetailBean.getHouseAddressY(), secondDetailBean.getHouseAddressX())));
        aMap.setOnMapClickListener(new AMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                Bundle bd = new Bundle();
                bd.putDouble(Ikeys.KEY_LATITUDE, secondDetailBean.getHouseAddressY());
                bd.putDouble(Ikeys.KEY_LONGITUDE, secondDetailBean.getHouseAddressX());
                goPage(PositionPeripheryMapActivity.class, bd);
            }
        });
    }

    private void getData() {
        Bundle bd = this.getArguments();
        if (bd == null) return;
        secondDetailBean = (SecondDetailBean) bd.getSerializable(KEY_SECOND_DETAIL);
        secondCommunityBean = (SecondCommunityBean) bd.getSerializable(KEY_SECOND_COMMUNITY);
    }

    @Override
    public void initView(View view) {
        if (aMap == null) {
            aMap = map.getMap();
        }
        aMap.getUiSettings().setAllGesturesEnabled(false);
    }

    @OnClick({R.id.udlt_house_more, R.id.udlt_community_more, R.id.udlt_periphery_more})
    public void onClick(View view) {
        Bundle bd = new Bundle();
        switch (view.getId()) {
            case R.id.udlt_house_more:
                bd.putSerializable(Ikeys.KEY_DATA, secondDetailBean);
                goPage(HousingProfileActivity.class, bd);
                break;
            case R.id.udlt_community_more:
                bd.putSerializable(CommunityInfoActivity.KEY_SECOND_COMMUNITY, secondCommunityBean);
                bd.putSerializable(CommunityInfoActivity.KEY_SECOND_DETAIL, secondDetailBean);
                goPage(CommunityInfoActivity.class, bd);
                break;
            case R.id.udlt_periphery_more:
                bd.putSerializable(Ikeys.KEY_DATA, secondDetailBean);
                goPage(PeripheryFacilityActivity.class, bd);
                break;
        }
    }

}
