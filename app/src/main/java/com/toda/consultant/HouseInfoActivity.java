package com.toda.consultant;

import android.os.Bundle;
import android.widget.TextView;

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
 * 房源资料
 * Created by guugangzhu on 2016/12/28.
 */

public class HouseInfoActivity extends BaseActivity {
    @BindView(R.id.tv_username)
    TextView tvUsername;
    @BindView(R.id.tv_sex)
    TextView tvSex;
    @BindView(R.id.tv_phone)
    TextView tvPhone;
    @BindView(R.id.tv_city)
    TextView tvCity;
    @BindView(R.id.tv_estate_name)
    TextView tvEstateName;
    @BindView(R.id.tv_address)
    TextView tvAddress;
    @BindView(R.id.tv_house_type)
    TextView tvHouseType;
    @BindView(R.id.tv_floor)
    TextView tvFloor;
    @BindView(R.id.tv_orientation)
    TextView tvOrientation;
    @BindView(R.id.tv_area)
    TextView tvArea;
    @BindView(R.id.tv_standard)
    TextView tvStandard;
    @BindView(R.id.tv_has_elivator)
    TextView tvHasElevator;
    @BindView(R.id.tv_expect_price)
    TextView tvExpectPrice;
    @BindView(R.id.err_info)
    ErrLayout errInfo;
    private String houseId;
    private SecondHouseSimpleBean bean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_house_info);
        ButterKnife.bind(this);
        houseId = getBundleStr(KEY_HOUSE_ID);
        initView();
        getHouseInfo();
    }

    @Override
    public void initView() {
        setTitle("房源资料");
        setTopBarRightText("核验");
    }

    /***
     * 获取二手房初步信息
     */
    private void getHouseInfo() {
        RequestParams params = new RequestParams(IConfig.URL_SECOND_HOUSE_PRELIMINARY_INFO);
        params.add("secondHousePersonalId",houseId);
        startRequest(Task.SECOND_HOUSE_PRELIMINARY_INFO, params, SecondHouseSimpleBean.class);
    }

    @Override
    public void onTopRightClick() {
        Bundle bundle=new Bundle();
        bundle.putString(KEY_HOUSE_ID,houseId);
        goPage(CheckHouseActivity.class,bundle);
    }

    @Override
    public void onRefresh(Call call, int tag, ResultData data) {
        super.onRefresh(call, tag, data);
        switch (tag) {
            case Task.SECOND_HOUSE_PRELIMINARY_INFO:
                if (HandlerRequestErr.handlerEmptyViewErr(this,errInfo,data)) {
                    bean= (SecondHouseSimpleBean) data.getData();
                    if(bean!=null)
                        setUI();
                }
        }
    }

    private void setUI(){
        tvUsername.setText(bean.getRealname());
        tvSex.setText(bean.getSex());
        tvPhone.setText(bean.getTel());
        tvCity.setText(bean.getProName()+" "+bean.getCityName());
        tvEstateName.setText(bean.getBuildingName());
        tvAddress.setText(bean.getHouseAddress());
        tvHouseType.setText(bean.getRoomType()+"室"+bean.getHallType()+"厅"+bean.getWashroomType()+"卫");
        tvFloor.setText("第"+bean.getFloorNum()+"层  共"+bean.getTotalFloor()+"层");
        tvOrientation.setText(bean.getDirection());
        tvArea.setText(bean.getHouseArea()+"㎡");
        tvStandard.setText(bean.getDesignStandard());
        if(string2Boolean(bean.getLiftType())){
            tvHasElevator.setText("有");
        }else {
            tvHasElevator.setText("无");
        }
        tvExpectPrice.setText(bean.getHouseMoney()+" 万");
    }

    @OnClick(R.id.err_info)
    public void onClick() {
        getHouseInfo();
    }
}
