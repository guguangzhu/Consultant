package com.toda.consultant.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.Display;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.toda.consultant.R;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class CommonTagDialog extends Dialog {

    @BindView(R.id.btn_confirm)
    Button btnConfirm;
    @BindView(R.id.id_flow_layout_feature)
    TagFlowLayout idFlowLayoutFeature;
    private View contentView;
    OnConfirmListener listener;
    private List<String> mList;
    private List<String> mSelectedString=new ArrayList<>();

    public CommonTagDialog(Context context,List<String> list, OnConfirmListener listener) {
        super(context, R.style.customdialog);
        this.listener = listener;
        mList=list;
        contentView = getLayoutInflater().inflate(R.layout.dialog_choose_tag, null);
        setContentView(contentView);
        ButterKnife.bind(this, contentView);
        initView();

        Display display = this.getWindow().getWindowManager().getDefaultDisplay();
        this.getWindow().setLayout((int) (display.getWidth() * 0.85), LayoutParams.WRAP_CONTENT);


    }

    private void initView() {
        if(mList==null||mList.size()==0){
            throw new IllegalStateException("list must not be null");
        }
        idFlowLayoutFeature.setAdapter(new TagAdapter<String>(mList) {
            @Override
            public View getView(FlowLayout parent, int position, String s) {
                final TextView tv = (TextView) getLayoutInflater().inflate(R.layout.item_radio_textview,
                        idFlowLayoutFeature, false);
                tv.setText(s);
                return tv;
            }
        });
        idFlowLayoutFeature.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
            @Override
            public boolean onTagClick(View view, int position, FlowLayout parent) {
                String tag=mList.get(position);
                if(mSelectedString.contains(tag)){
                    mSelectedString.remove(tag);
                }else {
                    mSelectedString.add(tag);
                }
                return false;
            }
        });
    }

    @OnClick(R.id.btn_confirm)
    public void onClick() {

        if (listener != null) {
            listener.onConfirm(mSelectedString);
            dismiss();
        }
    }


    public interface OnConfirmListener {
        void onConfirm(List<String> list);
    }

}