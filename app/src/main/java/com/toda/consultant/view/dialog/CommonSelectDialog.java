package com.toda.consultant.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.AdapterView;
import android.widget.ListView;


import com.toda.consultant.R;
import com.toda.consultant.adapter.AddHouseOptAdapter;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class CommonSelectDialog extends Dialog {
    @BindView(R.id.lv_list)
    ListView lvList;
    private View contentView;

    List<String> mList;
    OnStringSelectListener listener;

    public CommonSelectDialog(Context context, List<String> list, OnStringSelectListener onStringSelectListener) {
        super(context, R.style.customdialog);
        this.mList=list;
        this.listener=onStringSelectListener;
        contentView = getLayoutInflater().inflate(R.layout.dialog_select, null);
        setContentView(contentView);
        ButterKnife.bind(this, contentView);
        initView();

        this.getWindow().setLayout(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);

//        this.setCancelable(false);
//        this.setCanceledOnTouchOutside(false);

    }

    private void initView() {
        if(mList==null||mList.size()==0){
            throw new IllegalStateException("list must not be empty");
        }
        AddHouseOptAdapter adapter=new AddHouseOptAdapter(getContext(),mList);
        lvList.setAdapter(adapter);
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(listener!=null)
                    listener.onStringSelect(mList.get(position));
                dismiss();
            }
        });
    }


    public interface OnStringSelectListener{
        void onStringSelect(String selectedString);
    }

}