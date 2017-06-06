package com.toda.consultant;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps2d.AMap;
import com.amap.api.maps2d.AMapOptions;
import com.amap.api.maps2d.LocationSource;
import com.amap.api.maps2d.MapView;
import com.amap.api.maps2d.model.BitmapDescriptorFactory;
import com.amap.api.maps2d.model.LatLng;
import com.amap.api.maps2d.model.MarkerOptions;
import com.amap.api.maps2d.model.MyLocationStyle;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 添加地图标记
 */

public class MarkMapActivity extends BaseActivity implements LocationSource,
        AMapLocationListener ,AMap.OnMapLongClickListener{

    @BindView(R.id.map)
    MapView mapView;

    LatLng mLatlng;

    private double latitude;
    private double longitude;
    private AMap aMap;
    private LocationSource.OnLocationChangedListener mListener;
    private AMapLocationClient mlocationClient;
    private AMapLocationClientOption mLocationOption;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mark_map);
        initView();
        mapView.onCreate(savedInstanceState);
        setUpMap();
    }

    /**
     * 设置一些amap的属性
     */
    private void setUpMap() {
        aMap.setLocationSource(this);// 设置定位监听
        aMap.getUiSettings().setMyLocationButtonEnabled(false);// 设置默认定位按钮是否显示
        aMap.getUiSettings().setZoomControlsEnabled(false);
        aMap.getUiSettings().setLogoPosition(AMapOptions.ZOOM_POSITION_RIGHT_CENTER);
//        aMap.setMyLocationStyle();
        aMap.setMyLocationEnabled(true);// 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false

        aMap.setOnMapLongClickListener(this);
        // 设置定位的类型为定位模式 ，可以由定位、跟随或地图根据面向方向旋转几种
//        aMap.setMyLocationType(AMap.LOCATION_TYPE_LOCATE);
//        aMap.setOnCameraChangeListener(this);
//        aMap.setAMapGestureListener(this);
        // 自定义系统定位蓝点
        MyLocationStyle myLocationStyle = new MyLocationStyle();
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.mipmap.gps_point));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
//        aMap.setMyLocationStyle(myLocationStyle);
//        aMap.setOnMapTouchListener(this);
//        aMap.setOnMapLoadedListener(this);
//        aMap.setOnMarkerClickListener(this);
    }


    @Override
    public void initView() {
        ButterKnife.bind(this);
        setTitle("位置及周边");
        aMap = mapView.getMap();
        aMap.getUiSettings().setZoomControlsEnabled(false);
        //绘制marker
//        aMap.addMarker(new MarkerOptions()
//                .position(new LatLng(latitude, longitude))
//                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
//                        .decodeResource(getResources(), R.mipmap.ic_location)))
//                .draggable(true));
//        aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(
//                latitude, longitude)));
    }

    @Override
    public void onTopRightClick() {
        super.onTopRightClick();
        if(mLatlng==null){
            toast("请选择坐标");
        }
        Intent intent=new Intent();
        intent.putExtra(KEY_LONGITUDE,mLatlng.longitude+"");
        intent.putExtra(KEY_LATITUDE,mLatlng.latitude+"");
        setResult(RESULT_OK,intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapView.onResume();
        if (mlocationClient != null) {
            mlocationClient.startLocation();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (mlocationClient != null) {
            mlocationClient.onDestroy();
        }
    }

    /**
     * 方法必须重写
     * map的生命周期方法
     */
    @Override
    protected void onPause() {
        super.onPause();
        mapView.onPause();
        if (mlocationClient != null) {
            mlocationClient.stopLocation();//ֹͣ
        }
    }


    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
        if (mlocationClient == null) {
            mlocationClient = new AMapLocationClient(this);
            mLocationOption = new AMapLocationClientOption();
            //设置定位监听
            mlocationClient.setLocationListener(this);
            //设置为高精度定位模式
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            //设置定位参数
            mlocationClient.setLocationOption(mLocationOption);
            // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
            // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
            // 在定位结束后，在合适的生命周期调用onDestroy()方法
            // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
            mlocationClient.startLocation();
        }
    }


    @Override
    public void deactivate() {
        mListener = null;
        if (mlocationClient != null) {
            mlocationClient.stopLocation();
            mlocationClient.onDestroy();
        }
        mlocationClient = null;
    }


    @Override
    public void onLocationChanged(AMapLocation amapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null && amapLocation.getErrorCode() == 0) {
                mListener.onLocationChanged(amapLocation);// 显示系统小蓝点
                latitude = amapLocation.getLatitude();
                longitude = amapLocation.getLongitude();

            } else {
                String errText = "定位失败," + amapLocation.getErrorCode() + ": " + amapLocation.getErrorInfo();
                Log.e("AmapErr", errText);
            }
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        mLatlng=latLng;
        //绘制marker
        aMap.addMarker(new MarkerOptions()
                .position(new LatLng(latLng.latitude, latLng.longitude))
                .icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory
                        .decodeResource(getResources(), R.mipmap.ic_location)))
                .draggable(true));
        setTopBarRightText("完成");
    }
}
