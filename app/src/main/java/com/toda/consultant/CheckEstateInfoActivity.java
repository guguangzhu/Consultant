package com.toda.consultant;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;

import com.toda.consultant.model.RequestParams;
import com.toda.consultant.model.ResultData;
import com.toda.consultant.statics.Task;
import com.toda.consultant.util.IConfig;
import com.toda.consultant.util.StringUtils;
import com.toda.consultant.view.UnitDescriptionLayout;
import com.toda.consultant.view.dialog.CommonInputDialog;
import com.toda.consultant.view.dialog.CommonInputNumDialog;
import com.toda.consultant.view.dialog.CommonInputRateDialog;
import com.toda.consultant.view.dialog.CommonSelectDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 小区户型资料
 * Created by guugangzhu on 2016/12/29.
 */

public class CheckEstateInfoActivity extends BaseActivity {

    @BindView(R.id.tv_estate_info)
    TextView tvEstateInfo;
    @BindView(R.id.ll_estate_info)
    UnitDescriptionLayout llEstateInfo;
    @BindView(R.id.tv_developer)
    TextView tvDeveloper;
    @BindView(R.id.ll_developer)
    UnitDescriptionLayout llDeveloper;
    @BindView(R.id.tv_manager)
    TextView tvManager;
    @BindView(R.id.ll_manager)
    UnitDescriptionLayout llManager;
    @BindView(R.id.tv_estate_name)
    TextView tvEstateName;
    @BindView(R.id.ll_estate_name)
    UnitDescriptionLayout llEstateName;
    @BindView(R.id.tv_average_price)
    TextView tvAveragePrice;
    @BindView(R.id.ll_average_price)
    UnitDescriptionLayout llAveragePrice;
    @BindView(R.id.tv_open_date)
    TextView tvOpenDate;
    @BindView(R.id.ll_open_date)
    UnitDescriptionLayout llOpenDate;
    @BindView(R.id.tv_give_date)
    TextView tvGiveDate;
    @BindView(R.id.ll_give_date)
    UnitDescriptionLayout llGiveDate;
    @BindView(R.id.tv_decorate_standard)
    TextView tvDecorateStandard;
    @BindView(R.id.ll_standard)
    UnitDescriptionLayout llStandard;
    @BindView(R.id.tv_construct_type)
    TextView tvConstructType;
    @BindView(R.id.ll_build_type)
    UnitDescriptionLayout llBuildType;
    @BindView(R.id.tv_manager_type)
    TextView tvManagerType;
    @BindView(R.id.ll_manager_type)
    UnitDescriptionLayout llManagerType;
    @BindView(R.id.tv_estate_period)
    TextView tvEstatePeriod;
    @BindView(R.id.ll_period)
    UnitDescriptionLayout llPeriod;
    @BindView(R.id.tv_estate_area)
    TextView tvEstateArea;
    @BindView(R.id.ll_area)
    UnitDescriptionLayout llArea;
    @BindView(R.id.tv_land_area)
    TextView tvLandArea;
    @BindView(R.id.ll_floor_area)
    UnitDescriptionLayout llFloorArea;
    @BindView(R.id.tv_green_ratio)
    TextView tvGreenRatio;
    @BindView(R.id.ll_greening_rate)
    UnitDescriptionLayout llGreeningRate;
    @BindView(R.id.tv_plot_ratio)
    TextView tvPlotRatio;
    @BindView(R.id.ll_inner_rate)
    UnitDescriptionLayout llInnerRate;
    @BindView(R.id.tv_manage_fee)
    TextView tvManageFee;
    @BindView(R.id.ll_management_price)
    UnitDescriptionLayout llManagementPrice;
    @BindView(R.id.tv_park_amount)
    TextView tvParkAmount;
    @BindView(R.id.ll_park_amount)
    UnitDescriptionLayout llParkAmount;
    @BindView(R.id.tv_house_amount)
    TextView tvHouseAmount;
    @BindView(R.id.ll_house_amount)
    UnitDescriptionLayout llHouseAmount;

    private String averagePrice; //均价
    private String floorArea; //占地面积
    private String constructArea;//建筑面积
    private String greenRate; //绿化率
    private String innerRate; //容积率
    private String managePrice; //物业费

    private String houseId;
    private Calendar calendar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_estate_info);
        ButterKnife.bind(this);
        houseId = getBundleStr(KEY_HOUSE_ID);
        initView();
    }

    @Override
    public void initView() {
        setTitle("小区资料");
        calendar = Calendar.getInstance();
        setTopBarRightText("提交");
    }

    @Override
    public void onTopRightClick() {
        super.onTopRightClick();
        if(checkData()){
            commitEstateInfo();
        }
    }

    /***
     * 完善小区资料
     */
    private void commitEstateInfo() {
        RequestParams params = new RequestParams(IConfig.URL_COMMIT_ESTATE_INFO);
        params.add("developer", tvDeveloper.getText().toString());
        params.add("houseCorp", tvManager.getText().toString());
        params.add("buildingName", tvEstateName.getText().toString());
        params.add("openTime", tvOpenDate.getText().toString());
        params.add("inTime", tvGiveDate.getText().toString());
        params.add("designStandard", tvDecorateStandard.getText().toString());
        params.add("buidlingType", tvConstructType.getText().toString());
        params.add("houseType", tvManagerType.getText().toString());
        params.add("ownYear", tvEstatePeriod.getText().toString());
        params.add("averagePrice", averagePrice);
        params.add("buildingArea", constructArea);
        params.add("landArea", floorArea);
        params.add("greenPercent", greenRate);
        params.add("volumeRatio", innerRate);
        params.add("propertyFee ", managePrice);
        params.add("carNum ", tvParkAmount.getText().toString());
        params.add("peopleNum ", tvHouseAmount.getText().toString());
        params.add("buildingDescription ", tvEstateInfo.getText().toString());
        params.add("secondHousePersonalId ", houseId);
        startRequest(Task.COMMIT_ESTATE_INFO, params, null, false);
    }

    @Override
    public void onRefresh(Call call, int tag, ResultData data) {
        super.onRefresh(call, tag, data);
        switch (tag) {
            case Task.GET_AGENT_HOUSE_LIST:
                if (handlerRequestErr(data)) {

                }
        }
    }

    private boolean checkData(){
        if(StringUtils.isEmpty(tvEstateInfo.getText().toString())){
            toast("小区简介不能为空");
            return false;
        }
        if(StringUtils.isEmpty(tvDeveloper.getText().toString())){
            toast("开发商不能为空");
            return false;
        }
        if(StringUtils.isEmpty(tvManager.getText().toString())){
            toast("物业公司不能为空");
            return false;
        }
        if(StringUtils.isEmpty(tvEstateName.getText().toString())){
            toast("楼盘名称不能为空");
            return false;
        }
        if(StringUtils.isEmpty(tvEstateName.getText().toString())){
            toast("楼盘名称不能为空");
            return false;
        }
        if(StringUtils.isEmpty(averagePrice)){
            toast("均价不能为空");
            return false;
        }
        if(StringUtils.isEmpty(tvOpenDate.getText().toString())){
            toast("开盘日期不能为空");
            return false;
        }
        if(StringUtils.isEmpty(tvGiveDate.getText().toString())){
            toast("交房日期不能为空");
            return false;
        }
        if(StringUtils.isEmpty(tvDecorateStandard.getText().toString())){
            toast("装修标准不能为空");
            return false;
        }
        if(StringUtils.isEmpty(tvConstructType.getText().toString())){
            toast("建筑类型不能为空");
            return false;
        }
        if(StringUtils.isEmpty(tvManagerType.getText().toString())){
            toast("物业类型不能为空");
            return false;
        }
        if(StringUtils.isEmpty(tvEstatePeriod.getText().toString())){
            toast("产权年限不能为空");
            return false;
        }
        if(StringUtils.isEmpty(floorArea)){
            toast("占地面积不能为空");
            return false;
        }
        if(StringUtils.isEmpty(constructArea)){
            toast("建筑面积不能为空");
            return false;
        }
        if(StringUtils.isEmpty(greenRate)){
            toast("绿化率不能为空");
            return false;
        }
        if(StringUtils.isEmpty(innerRate)){
            toast("容积率不能为空");
            return false;
        }
        if(StringUtils.isEmpty(managePrice)){
            toast("物业费不能为空");
            return false;
        }
        if(StringUtils.isEmpty(tvParkAmount.getText().toString())){
            toast("车位不能为空");
            return false;
        }
        if(StringUtils.isEmpty(tvHouseAmount.getText().toString())){
            toast("户数不能为空");
            return false;
        }
        return true;
    }

    @OnClick({R.id.ll_estate_info, R.id.ll_developer, R.id.ll_manager, R.id.ll_estate_name, R.id.ll_average_price, R.id.ll_open_date, R.id.ll_give_date, R.id.ll_standard, R.id.ll_build_type,
            R.id.ll_manager_type, R.id.ll_period, R.id.ll_area, R.id.ll_floor_area, R.id.ll_greening_rate, R.id.ll_inner_rate, R.id.ll_management_price, R.id.ll_park_amount, R.id.ll_house_amount})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_estate_info:
                showEstateInfo();
                break;
            case R.id.ll_developer:
                showDeveloper();
                break;
            case R.id.ll_manager:
                showManager();
                break;
            case R.id.ll_estate_name:
                showEstateName();
                break;
            case R.id.ll_average_price:
                showAveragePriceDialog();
                break;
            case R.id.ll_open_date:
                showOpenDate();
                break;
            case R.id.ll_give_date:
                showGiveDate();
                break;
            case R.id.ll_standard:
                showStandard();
                break;
            case R.id.ll_build_type:
                showBuildType();
                break;
            case R.id.ll_manager_type:
                showManagementType();
                break;
            case R.id.ll_period:
                showYears();
                break;
            case R.id.ll_area:
                showAreaDialog();
                break;
            case R.id.ll_floor_area:
                showFloorAreaDialog();
                break;
            case R.id.ll_greening_rate:
                showGreeningRateDialog();
                break;
            case R.id.ll_inner_rate:
                showInnerRateDialog();
                break;
            case R.id.ll_management_price:
                showManagementPriceDialog();
                break;
            case R.id.ll_park_amount:
                showParkAmountDialog();
                break;
            case R.id.ll_house_amount:
                showHouseDialog();
                break;
        }
    }


    /**
     * 小区介绍
     */
    private void showEstateInfo() {
        CommonInputDialog dialog = new CommonInputDialog(this, "请输入小区简介", new CommonInputDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String selectedString) {
                tvEstateInfo.setText(selectedString);
            }
        });
        dialog.show();
    }

    /**
     * 开发商
     */
    private void showDeveloper() {
        CommonInputDialog dialog = new CommonInputDialog(this, "请输入开发商", new CommonInputDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String selectedString) {
                tvDeveloper.setText(selectedString);
            }
        });
        dialog.show();
    }

    /**
     * 物业公司
     */
    private void showManager() {
        CommonInputDialog dialog = new CommonInputDialog(this, "请输入物业公司", new CommonInputDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String selectedString) {
                tvManager.setText(selectedString);
            }
        });
        dialog.show();
    }

    /**
     * 楼盘名称
     */
    private void showEstateName() {
        CommonInputDialog dialog = new CommonInputDialog(this, "请输入楼盘名称", new CommonInputDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String selectedString) {
                tvEstateName.setText(selectedString);
            }
        });
        dialog.show();
    }

    /**
     * 均价
     */
    private void showAveragePriceDialog() {
        CommonInputNumDialog dialog = new CommonInputNumDialog(this, "元/㎡", new CommonInputNumDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String selectedString) {
                averagePrice=selectedString;
                tvAveragePrice.setText(selectedString+" 元/㎡");
            }
        });
        dialog.show();
    }

    /**
     * 开盘日期
     */
    private void showOpenDate(){
        DatePickerDialog  datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tvOpenDate.setText(year+"-"+month+"-"+dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * 交房日期
     */
    private void showGiveDate(){
        DatePickerDialog  datePickerDialog= new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                tvGiveDate.setText(year+"-"+month+"-"+dayOfMonth);
            }
        }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    /**
     * 装修标准
     */
    private void showStandard() {
        final List<String> list = new ArrayList<>();
        list.add("毛坯");
        list.add("简装");
        list.add("精装");
        CommonSelectDialog standardDialog = new CommonSelectDialog(this, list, new CommonSelectDialog.OnStringSelectListener() {
            @Override
            public void onStringSelect(String selectedString) {
                tvDecorateStandard.setText(selectedString);
            }
        });
        standardDialog.show();
    }
    /**
     * 建筑类型
     */
    private void showBuildType() {
        final List<String> list = new ArrayList<>();
        /**
         * 多层，小高层，高层，超高层，花园洋房，独栋别墅，双拼别墅，四合院，板楼，塔楼，楼塔组合
         */
        list.add("多层");
        list.add("小高层");
        list.add("高层");
        list.add("超高层");
        list.add("花园洋房");
        list.add("独栋别墅");
        list.add("双拼别墅");
        list.add("四合院");
        list.add("板楼");
        list.add("楼塔组合");
        CommonSelectDialog dialog = new CommonSelectDialog(this, list, new CommonSelectDialog.OnStringSelectListener() {
            @Override
            public void onStringSelect(String selectedString) {
                tvConstructType.setText(selectedString);
            }
        });
        dialog.show();
    }

    /**
     * 物业类型
     */
    private void showManagementType() {
        final List<String> list = new ArrayList<>();
        list.add("住宅用地");
        list.add("商用地");
        list.add("商住两用");
        CommonSelectDialog dialog = new CommonSelectDialog(this, list, new CommonSelectDialog.OnStringSelectListener() {
            @Override
            public void onStringSelect(String selectedString) {
                tvManagerType.setText(selectedString);
            }
        });
        dialog.show();
    }

    /**
     * 年限
     */
    private void showYears() {
        final List<String> list = new ArrayList<>();
        list.add("住宅用地70年");
        list.add("商用地40年");
        list.add("商住两用40年");
        CommonSelectDialog elevatorDialog = new CommonSelectDialog(this, list, new CommonSelectDialog.OnStringSelectListener() {
            @Override
            public void onStringSelect(String selectedString) {
                tvEstatePeriod.setText(selectedString);
            }
        });
        elevatorDialog.show();
    }


    /**
     * 建筑面积
     */
    private void showAreaDialog() {
        CommonInputNumDialog dialog = new CommonInputNumDialog(this, "㎡", new CommonInputNumDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String selectedString) {
                constructArea=selectedString;
                tvEstateArea.setText(selectedString+" ㎡");
            }
        });
        dialog.show();
    }

    /**
     * 占地面积
     */
    private void showFloorAreaDialog() {
        CommonInputNumDialog dialog = new CommonInputNumDialog(this, "㎡", new CommonInputNumDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String selectedString) {
                floorArea=selectedString;
                tvLandArea.setText(selectedString+" ㎡");
            }
        });
        dialog.show();
    }
    /**
     * 绿化率
     */
    private void showGreeningRateDialog() {
        CommonInputRateDialog dialog = new CommonInputRateDialog(this, "%", new CommonInputRateDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String selectedString) {
                greenRate=selectedString;
                tvGreenRatio.setText(selectedString+" %");
            }
        });
        dialog.show();
    }

    /**
     * 容积率
     */
    private void showInnerRateDialog() {
        CommonInputRateDialog dialog = new CommonInputRateDialog(this, "%", new CommonInputRateDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String selectedString) {
                innerRate=selectedString;
                tvPlotRatio.setText(selectedString+" %");
            }
        });
        dialog.show();
    }

    /**
     * 物业费
     */
    private void showManagementPriceDialog() {
        CommonInputNumDialog dialog = new CommonInputNumDialog(this, "元", new CommonInputNumDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String selectedString) {
                managePrice=selectedString;
                tvManageFee.setText(selectedString+" 元");
            }
        });
        dialog.show();
    }


    /**
     * 停车位
     */
    private void showParkAmountDialog() {
        CommonInputNumDialog dialog = new CommonInputNumDialog(this, "个", new CommonInputNumDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String selectedString) {
                tvParkAmount.setText(selectedString);
            }
        });
        dialog.show();
    }

    /**
     * 户数
     */
    private void showHouseDialog() {
        CommonInputNumDialog dialog = new CommonInputNumDialog(this, "户", new CommonInputNumDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String selectedString) {
                tvHouseAmount.setText(selectedString);
            }
        });
        dialog.show();
    }
}
