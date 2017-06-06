package com.toda.consultant.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by Zhao Haibin on 2016/3/15.
 */
public class ErrLayout extends FrameLayout {
    private boolean hasLoad;
    private NormalEmptyView emptyView;
    private OnErrClickListener listener;
    public ErrLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){
        emptyView=new NormalEmptyView(getContext());
        this.addView(emptyView);
        emptyView.setVisibility(View.GONE);
        emptyView.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onErrClick();
                }
            }
        });
    }

    public void setOnErrClickListener(OnErrClickListener listener){
        this.listener=listener;
    }

    public void showErrView(int type){
        for(int i=0;i<this.getChildCount();i++){
            View view=getChildAt(i);
            view.setVisibility(view==emptyView? View.VISIBLE: View.GONE);
        }
        emptyView.setEmptyType(type);
    }

    public void hideErrView(){
        for(int i=0;i<this.getChildCount();i++){
            View view=getChildAt(i);
            view.setVisibility(view==emptyView? View.GONE: View.VISIBLE);
        }
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
//        child.setVisibility(View.GONE);
    }

    public boolean isHasLoad() {
        return hasLoad;
    }

    public void setHasLoad(boolean hasLoad) {
        this.hasLoad = hasLoad;
    }

    @Override
    public void setOnClickListener(OnClickListener l) {
        emptyView.setOnClickListener(l);
    }

    public interface OnErrClickListener{
        public void onErrClick();
    }
}
