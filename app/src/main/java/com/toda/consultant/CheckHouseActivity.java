package com.toda.consultant;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.toda.consultant.bean.SecondHouseSimpleBean;
import com.toda.consultant.model.RequestParams;
import com.toda.consultant.model.ResultData;
import com.toda.consultant.statics.Task;
import com.toda.consultant.util.HandlerRequestErr;
import com.toda.consultant.util.IConfig;
import com.toda.consultant.view.ErrLayout;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * Created by guugangzhu on 2016/12/29.
 */

public class CheckHouseActivity extends BaseActivity {

    @BindView(R.id.ll_house_info)
    LinearLayout llHouseInfo;
    @BindView(R.id.ll_estate_info)
    LinearLayout llEstateInfo;
    @BindView(R.id.ll_surrounding)
    LinearLayout llSurrounding;
    @BindView(R.id.err_info)
    ErrLayout errInfo;
    @BindView(R.id.iv_house_info)
    ImageView ivHouseInfo;
    @BindView(R.id.iv_estate_info)
    ImageView ivEstateInfo;
    @BindView(R.id.iv_surrounding)
    ImageView ivSurrounding;
    private String houseId;
    private SecondHouseSimpleBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_house_main);
        ButterKnife.bind(this);
        houseId = getBundleStr(KEY_HOUSE_ID);
        initView();
        getHouseInfo();
    }

    @Override
    public void initView() {
        setTitle("核验房源");
    }

    /***
     * 获取二手房初步信息
     */
    private void getHouseInfo() {
        RequestParams params = new RequestParams(IConfig.URL_SECOND_HOUSE_PRELIMINARY_INFO);
        params.add("secondHousePersonalId", houseId);
        startRequest(Task.SECOND_HOUSE_PRELIMINARY_INFO, params, SecondHouseSimpleBean.class);
    }


    @Override
    public void onRefresh(Call call, int tag, ResultData data) {
        super.onRefresh(call, tag, data);
        switch (tag) {
            case Task.SECOND_HOUSE_PRELIMINARY_INFO:
                if (HandlerRequestErr.handlerEmptyViewErr(this, errInfo, data)) {
                    bean = (SecondHouseSimpleBean) data.getData();
                    if (bean != null)
                        setUI();
                }
        }
    }

    private void setUI() {
        if(int2Boolean(bean.getStep1Status())){
            ivHouseInfo.setImageResource(R.mipmap.ic_finished);
            llHouseInfo.setClickable(false);
        }else {
            ivHouseInfo.setImageResource(R.mipmap.ic_arrow_right);
            llHouseInfo.setClickable(true);
        }

        if(int2Boolean(bean.getStep2Status())){
            ivEstateInfo.setImageResource(R.mipmap.ic_finished);
            llEstateInfo.setClickable(false);
        }else {
            ivEstateInfo.setImageResource(R.mipmap.ic_arrow_right);
            llEstateInfo.setClickable(true);
        }

        if(int2Boolean(bean.getStep3Status())){
            ivSurrounding.setImageResource(R.mipmap.ic_finished);
            llSurrounding.setClickable(false);
        }else {
            ivSurrounding.setImageResource(R.mipmap.ic_arrow_right);
            llSurrounding.setClickable(true);
        }
    }

    @OnClick({R.id.ll_house_info, R.id.ll_estate_info, R.id.ll_surrounding, R.id.err_info})
    public void onClick(View view) {
        Bundle bundle=new Bundle();
        bundle.putString(KEY_HOUSE_ID,houseId);
        switch (view.getId()) {
            case R.id.ll_house_info:
                goPage(CheckHouseInfoActivity.class,bundle);
                break;
            case R.id.ll_estate_info:
                goPage(CheckEstateInfoActivity.class,bundle);
                break;
            case R.id.ll_surrounding:
                goPage(CheckSurroundingActivity.class,bundle);
                break;
            case R.id.err_info:
                getHouseInfo();
                break;
        }
    }
}
