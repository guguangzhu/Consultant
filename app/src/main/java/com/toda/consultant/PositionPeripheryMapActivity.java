package com.toda.consultant;

import android.graphics.BitmapFactory;
import android.os.Bundle;

import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.CameraUpdateFactory;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.toda.consultant.util.Ikeys;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by yangwei on 2016/12/8.
 */

public class PositionPeripheryMapActivity extends BaseActivity {

    @BindView(R.id.map)
    MapView map;

    private double latitude;
    private double longitude;
    private AMap aMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_periphery_map);
        initView();
        map.onCreate(savedInstanceState);
        getData();
        setUi();
    }

    private void setUi() {
        setTitle("项目位置");
        aMap = map.getMap();
        aMap.getUiSettings().setZoomControlsEnabled(false);
        //绘制marker
        aMap.addMarker(new MarkerOptions()
                .position(new LatLng(latitude, longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.mipmap.ic_location)))
                .draggable(true));
        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(
                latitude, longitude)));
    }

    private void getData() {
        Bundle bd = getIntentData();
        if (bd == null) return;
        latitude = bd.getDouble(Ikeys.KEY_LATITUDE);
        longitude = bd.getDouble(Ikeys.KEY_LONGITUDE);
    }

    @Override
    public void initView() {
        ButterKnife.bind(this);
    }

}
