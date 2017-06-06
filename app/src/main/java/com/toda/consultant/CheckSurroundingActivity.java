package com.toda.consultant;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toda.consultant.model.RequestParams;
import com.toda.consultant.model.ResultData;
import com.toda.consultant.statics.Task;
import com.toda.consultant.util.IConfig;
import com.toda.consultant.util.StringUtils;
import com.toda.consultant.view.dialog.CommonInputDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

/**
 * 验房周边
 * Created by guugangzhu on 2016/12/29.
 */

public class CheckSurroundingActivity extends BaseActivity {

    @BindView(R.id.tv_transformation)
    TextView tvTransformation;
    @BindView(R.id.ll_transformation)
    LinearLayout llTransformation;
    @BindView(R.id.tv_school)
    TextView tvSchool;
    @BindView(R.id.ll_school)
    LinearLayout llSchool;
    @BindView(R.id.tv_market)
    TextView tvMarket;
    @BindView(R.id.ll_market)
    LinearLayout llMarket;
    @BindView(R.id.tv_hospital)
    TextView tvHospital;
    @BindView(R.id.ll_hospital)
    LinearLayout llHospital;
    @BindView(R.id.tv_bank)
    TextView tvBank;
    @BindView(R.id.ll_bank)
    LinearLayout llBank;

    private String houseId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_surrounding);
        houseId = getBundleStr(KEY_HOUSE_ID);
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void initView() {
        setTitle("周边配套");
        setTopBarRightText("提交");
    }

    @Override
    public void onTopRightClick() {
        super.onTopRightClick();
        if(checkData()){
            commitSurroundingInfo();
        }
    }

    @OnClick({R.id.ll_transformation, R.id.ll_school, R.id.ll_market, R.id.ll_hospital, R.id.ll_bank})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.ll_transformation:
                showTransformation();
                break;
            case R.id.ll_school:
                showSchool();
                break;
            case R.id.ll_market:
                showMarket();
                break;
            case R.id.ll_hospital:
                showHospital();
                break;
            case R.id.ll_bank:
                showBank();
                break;
        }
    }

    /***
     * 完善周边资料
     */
    private void commitSurroundingInfo() {
        RequestParams params = new RequestParams(IConfig.URL_COMMIT_SURROUNDING_INFO);
        params.add("traffic", tvTransformation.getText().toString());
        params.add("school", tvSchool.getText().toString());
        params.add("market", tvMarket.getText().toString());
        params.add("hospital", tvHospital.getText().toString());
        params.add("bank", tvBank.getText().toString());
        params.add("secondHousePersonalId ", houseId);
        startRequest(Task.COMMIT_SURROUNDING_INFO, params, null);
    }

    @Override
    public void onRefresh(Call call, int tag, ResultData data) {
        super.onRefresh(call, tag, data);
        switch (tag) {
            case Task.COMMIT_SURROUNDING_INFO:
                if (handlerRequestErr(data)) {

                }
        }
    }

    private boolean checkData(){
        if(StringUtils.isEmpty(tvTransformation.getText().toString())){
            toast("交通设施不能为空");
            return false;
        }

        if(StringUtils.isEmpty(tvSchool.getText().toString())){
            toast("学校不能为空");
            return false;
        }
        if(StringUtils.isEmpty(tvMarket.getText().toString())){
            toast("超市菜场不能为空");
            return false;
        }

        if(StringUtils.isEmpty(tvHospital.getText().toString())){
            toast("医院不能为空");
            return false;
        }
        if(StringUtils.isEmpty(tvBank.getText().toString())){
            toast("银行不能为空");
            return false;
        }
        return true;
    }

    /**
     * 交通设施
     */
    private void showTransformation() {
        CommonInputDialog dialog = new CommonInputDialog(this, "请输入交通设施", new CommonInputDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String selectedString) {
                tvTransformation.setText(selectedString);
            }
        });
        dialog.show();
    }

    /**
     * 学校
     */
    private void showSchool() {
        CommonInputDialog dialog = new CommonInputDialog(this, "请输入学校", new CommonInputDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String selectedString) {
                tvSchool.setText(selectedString);
            }
        });
        dialog.show();
    }
    /**
     * 超市
     */
    private void showMarket() {
        CommonInputDialog dialog = new CommonInputDialog(this, "请输入超市菜场", new CommonInputDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String selectedString) {
                tvMarket.setText(selectedString);
            }
        });
        dialog.show();
    }

    /**
     * 医院
     */
    private void showHospital() {
        CommonInputDialog dialog = new CommonInputDialog(this, "请输入医院", new CommonInputDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String selectedString) {
                tvHospital.setText(selectedString);
            }
        });
        dialog.show();
    }
    /**
     * 银行
     */
    private void showBank() {
        CommonInputDialog dialog = new CommonInputDialog(this, "请输入银行", new CommonInputDialog.OnConfirmListener() {
            @Override
            public void onConfirm(String selectedString) {
                tvBank.setText(selectedString);
            }
        });
        dialog.show();
    }
}
