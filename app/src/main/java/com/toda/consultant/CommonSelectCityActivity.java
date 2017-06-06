package com.toda.consultant;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;


import com.toda.consultant.bean.CityListBean;
import com.toda.consultant.model.RequestParams;
import com.toda.consultant.model.ResultData;
import com.toda.consultant.statics.Task;
import com.toda.consultant.util.IConfig;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;


/**
 * 城市选择，返回城市名
 * Created by guugangzhu on 2016/10/18.
 */

public class CommonSelectCityActivity extends BaseActivity {
    public static final String KEY_PROVINCE_ID="province_id";
    public static final String KEY_PROVINCE_NAME="province_name";

    public static final String KEY_CITY_ID="city_id";
    public static final String KEY_CITY_NAME="city_name";

    public static final String KEY_REGION_ID="region_id";
    public static final String KEY_REGION_NAME="region_name";
    @BindView(R.id.list)
    ExpandableListView list;

    private static final int REQUEST_REGION=222;
    String cityName,cityId,provinceName,provinceId,regionId,regionName;

    private ArrayList<CityListBean.ProListBean> citiList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_common_select_city);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
//        bvCustomBlur.setBlurredImg(getResources().getDrawable(R.mipmap.bg_login_three));
//        list.setChildDivider(new ColorDrawable(this.getResources().getColor(R.color.line_color)));
        getCityList();
    }




    //获取城市列表
    private void getCityList() {
        RequestParams params = new RequestParams(IConfig.URL_GET_CITY_LIST);
        startRequest(Task.GET_CITY_LIST, params, CityListBean.class);
    }

    @Override
    public void onRefresh(Call call, int tag, ResultData data) {
        super.onRefresh(call, tag, data);
        switch (tag) {
            case Task.GET_CITY_LIST:
                if (handlerRequestErr(data)) {
                    CityListBean bean = (CityListBean) data.getData();
                    if (bean == null) return;
                    citiList.addAll(bean.getProList());
                    setListUi();
                }
                break;
        }
    }

    private void setListUi(){
        list.setAdapter(new MyAdapter());
        list.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
                String province=citiList.get(groupPosition).getProName();
                String provinceId=citiList.get(groupPosition).getProId()+"";
                String city=citiList.get(groupPosition).getCityList().get(childPosition).getCityName();
                String cityId=citiList.get(groupPosition).getCityList().get(childPosition).getCityId()+"";
                Intent intent=new Intent(CommonSelectCityActivity.this,ChooseRegionActivity.class);
                intent.putExtra(KEY_PROVINCE_ID,provinceId);
                intent.putExtra(KEY_PROVINCE_NAME,province);

                intent.putExtra(KEY_CITY_ID,cityId);
                intent.putExtra(KEY_CITY_NAME,city);
                startActivityForResult(intent,REQUEST_REGION);
                return false;
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case REQUEST_REGION:
                if(resultCode==RESULT_OK){
//                    String city=data.getStringExtra(KEY_DATA);
//                    tvCity.setText(city);
                    this.cityId=data.getStringExtra(CommonSelectCityActivity.KEY_CITY_ID);
                    this.cityName=data.getStringExtra(CommonSelectCityActivity.KEY_CITY_NAME);
                    this.provinceId=data.getStringExtra(CommonSelectCityActivity.KEY_PROVINCE_ID);
                    this.provinceName=data.getStringExtra(CommonSelectCityActivity.KEY_PROVINCE_NAME);
                    regionId=data.getStringExtra(CommonSelectCityActivity.KEY_REGION_ID);
                    regionName=data.getStringExtra(CommonSelectCityActivity.KEY_REGION_NAME);
//                    tvCity.setText(provinceName+cityName);
                    Intent intent=new Intent();
                    intent.putExtra(CommonSelectCityActivity.KEY_PROVINCE_ID,provinceId);
                    intent.putExtra(CommonSelectCityActivity.KEY_PROVINCE_NAME,provinceName);

                    intent.putExtra(CommonSelectCityActivity.KEY_CITY_ID,cityId);
                    intent.putExtra(CommonSelectCityActivity.KEY_CITY_NAME,cityName);

                    intent.putExtra(CommonSelectCityActivity.KEY_REGION_ID,regionId);
                    intent.putExtra(CommonSelectCityActivity.KEY_REGION_NAME,regionName);

                    setResult(RESULT_OK,intent);
                    finish();
                }
                break;
        }
    }

    class MyAdapter extends BaseExpandableListAdapter {

        //得到子item需要关联的数据
        @Override
        public Object getChild(int groupPosition, int childPosition) {

            return citiList.get(groupPosition).getCityList().get(childPosition);
        }

        //得到子item的ID
        @Override
        public long getChildId(int groupPosition, int childPosition) {
            return childPosition;
        }

        //设置子item的组件
        @Override
        public View getChildView(int groupPosition, int childPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            CityListBean.ProListBean.CityListBean1 bean1=citiList.get(groupPosition).getCityList().get(childPosition);
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) CommonSelectCityActivity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_provice, null);
            }
            TextView tv = (TextView) convertView
                    .findViewById(R.id.parent_textview);
            tv.setText(bean1.getCityName());
            return tv;
        }

        //获取当前父item下的子item的个数
        @Override
        public int getChildrenCount(int groupPosition) {

            return citiList.get(groupPosition).getCityList().size();
        }
        //获取当前父item的数据
        @Override
        public Object getGroup(int groupPosition) {
            return citiList.get(groupPosition);
        }

        @Override
        public int getGroupCount() {
            return citiList.size();
        }

        @Override
        public long getGroupId(int groupPosition) {
            return groupPosition;
        }
        //设置父item组件
        @Override
        public View getGroupView(int groupPosition, boolean isExpanded,
                                 View convertView, ViewGroup parent) {
            CityListBean.ProListBean proListBean=citiList.get(groupPosition);
            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) CommonSelectCityActivity.this
                        .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.item_provice, null);
            }
            TextView tv = (TextView) convertView
                    .findViewById(R.id.parent_textview);
            tv.setText(proListBean.getProName());
            return tv;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }

        @Override
        public boolean isChildSelectable(int groupPosition, int childPosition) {
            return true;
        }

    }
}
