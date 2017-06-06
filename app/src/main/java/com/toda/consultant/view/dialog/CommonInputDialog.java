package com.toda.consultant.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.toda.consultant.R;
import com.toda.consultant.util.StringUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CommonInputDialog extends Dialog {
    @BindView(R.id.et_content)
    EditText etContent;
    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    private View contentView;
    String unit;
    OnConfirmListener listener;

    public CommonInputDialog(Context context, String unit, OnConfirmListener listener) {
        super(context, R.style.customdialog);
        this.listener = listener;
        contentView = getLayoutInflater().inflate(R.layout.dialog_input_content, null);
        this.unit = unit;
        setContentView(contentView);
        ButterKnife.bind(this, contentView);
        initView();

        Display display = this.getWindow().getWindowManager().getDefaultDisplay();
        this.getWindow().setLayout((int) (display.getWidth() * 0.85), LayoutParams.WRAP_CONTENT);

//        this.setCancelable(false);
//        this.setCanceledOnTouchOutside(false);

    }

    private void initView() {
        etContent.setHint(unit);
    }

    @OnClick(R.id.btn_confirm)
    public void onClick() {
        String content=etContent.getText().toString();
        if(StringUtils.isEmpty(content)){
            Toast.makeText(getContext(),"请输入内容",Toast.LENGTH_SHORT).show();
            return;
        }
        if(listener!=null){
            listener.onConfirm(content);
            dismiss();
        }
    }


    public interface OnConfirmListener {
        void onConfirm(String selectedString);
    }

}