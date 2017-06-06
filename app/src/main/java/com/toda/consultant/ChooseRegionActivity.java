package com.toda.consultant;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.toda.consultant.adapter.RegionAdapter;
import com.toda.consultant.bean.RegionBean;
import com.toda.consultant.model.RequestParams;
import com.toda.consultant.model.ResultData;
import com.toda.consultant.statics.Task;
import com.toda.consultant.util.IConfig;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * Created by guugangzhu on 2017/2/20.
 */

public class ChooseRegionActivity extends BaseActivity implements AdapterView.OnItemClickListener{

    @BindView(R.id.lv_region)
    ListView lvRegion;
    String cityName,cityId,provinceName,provinceId;
    RegionAdapter adapter;
    List<RegionBean.ListBean> mList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_region);
        this.cityId=getIntent().getStringExtra(CommonSelectCityActivity.KEY_CITY_ID);
        this.cityName=getIntent().getStringExtra(CommonSelectCityActivity.KEY_CITY_NAME);
        this.provinceId=getIntent().getStringExtra(CommonSelectCityActivity.KEY_PROVINCE_ID);
        this.provinceName=getIntent().getStringExtra(CommonSelectCityActivity.KEY_PROVINCE_NAME);
        ButterKnife.bind(this);
        initView();
        getRegionList();
    }
    //获取区域列表
    private void getRegionList() {
        RequestParams params = new RequestParams(IConfig.URL_GET_RETION_BY_CITY);
        params.add("cityId",cityId);
        startRequest(Task.GET_REGION_LIST, params, RegionBean.class);
    }

    @Override
    public void onRefresh(Call call, int tag, ResultData data) {
        super.onRefresh(call, tag, data);
        switch (tag) {
            case Task.GET_REGION_LIST:
                if (handlerRequestErr(data)) {
                    RegionBean bean = (RegionBean) data.getData();
                    if (bean == null) return;
                    mList.clear();
                    mList.addAll(bean.getList());
                    adapter.notifyDataSetChanged();

                }
                break;
        }
    }

    @Override
    public void initView() {
        setTitle("选择区域");
        adapter=new RegionAdapter(this,mList);
        lvRegion.setAdapter(adapter);
        lvRegion.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String regionId=mList.get(position).getAreaId()+"";
        String regionName=mList.get(position).getAreaName();

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
}
