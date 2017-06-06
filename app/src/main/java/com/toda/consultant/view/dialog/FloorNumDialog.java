package com.toda.consultant.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.toda.consultant.R;
import com.toda.consultant.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FloorNumDialog extends Dialog {

    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.et_floor)
    EditText etFloor;
    @BindView(R.id.et_total_floor)
    EditText etTotalFloor;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    private View contentView;
    OnConfirmListener listener;

    public FloorNumDialog(Context context, OnConfirmListener listener) {
        super(context, R.style.customdialog);
        this.listener = listener;
        contentView = getLayoutInflater().inflate(R.layout.dialog_input_floor, null);
        setContentView(contentView);
        ButterKnife.bind(this, contentView);
        initView();

        Display display = this.getWindow().getWindowManager().getDefaultDisplay();
        this.getWindow().setLayout((int) (display.getWidth() * 0.85), LayoutParams.WRAP_CONTENT);

//        this.setCancelable(false);
//        this.setCanceledOnTouchOutside(false);

    }

    private void initView() {
    }

    @OnClick(R.id.btn_confirm)
    public void onClick() {
        String floor = etFloor.getText().toString();
        String totalFloor = etTotalFloor.getText().toString();
        if (StringUtils.isEmpty(floor)) {
            Toast.makeText(getContext(), "请输入楼层", Toast.LENGTH_SHORT).show();
            return;
        }
        if (StringUtils.isEmpty(totalFloor)) {
            Toast.makeText(getContext(), "请输入楼层", Toast.LENGTH_SHORT).show();
            return;
        }
        if (listener != null) {
            listener.onConfirm(floor,totalFloor);
            dismiss();
        }
    }


    public interface OnConfirmListener {
        void onConfirm(String floor,String totalFloor);
    }

}