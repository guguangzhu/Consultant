package com.toda.consultant.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toda.consultant.R;


public class NormalEmptyView extends RelativeLayout {
	public static final int EMPTY_TYPE_LOADING = 1;
	public static final int EMPTY_TYPE_ERROR = 2;
	public static final int EMPTY_TYPE_NOCONTENT = 3;
	public static final int EMPTY_TYPE_NONET = 4;

	public TextView tv_empty_text;
	private ProgressBar pb_empty_loading;
	private ImageView iv_err;

	private String mEmptyRes = "暂无数据，点击刷新";
	private String mLoadingRes = "";
	private String mErrorRes = "页面加载失败\n点击屏幕刷新";
	private String mNoNetRes = "额...没网络了\n点击屏幕重新连接";
	private int mEmptyType;

	private Drawable requestErrDrawable,noNetDrawable,noDataDrawable;

	public NormalEmptyView(Context context){
		this(context,null);
	}
	public NormalEmptyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		LayoutInflater.from(context).inflate(R.layout.normal_empty, this);
		tv_empty_text = (TextView) findViewById(R.id.tv_empty_text);
		pb_empty_loading = (ProgressBar) findViewById(R.id.pb_empty_loading);
		iv_err= (ImageView) findViewById(R.id.iv_err);

		setEmptyType(EMPTY_TYPE_LOADING);
	}

	public void setEmptyType(int type) {
		switch (type) {
		case EMPTY_TYPE_ERROR:
			if(requestErrDrawable==null){
				requestErrDrawable=getResources().getDrawable(R.drawable.ic_request_err);
			}
			iv_err.setVisibility(View.VISIBLE);
			iv_err.setImageDrawable(requestErrDrawable);
			tv_empty_text.setText(mErrorRes);
			pb_empty_loading.setVisibility(View.GONE);
			setClickable(true);
			break;
		case EMPTY_TYPE_LOADING:
			iv_err.setVisibility(View.GONE);
			tv_empty_text.setText(mLoadingRes);
			pb_empty_loading.setVisibility(View.VISIBLE);
			setClickable(false);
			break;
		case EMPTY_TYPE_NOCONTENT:
			if(noDataDrawable==null){
				noDataDrawable=getResources().getDrawable(R.drawable.ic_no_data);
			}
			iv_err.setVisibility(View.VISIBLE);
			tv_empty_text.setText(mEmptyRes);
			pb_empty_loading.setVisibility(View.GONE);
			iv_err.setImageDrawable(noDataDrawable);
			setClickable(true);
			break;
		case EMPTY_TYPE_NONET:
			if(noNetDrawable==null){
				noNetDrawable=getResources().getDrawable(R.drawable.ic_no_connect);
			}
			iv_err.setVisibility(View.VISIBLE);
			iv_err.setImageDrawable(noNetDrawable);
			tv_empty_text.setText(mNoNetRes);
			pb_empty_loading.setVisibility(View.GONE);
			setClickable(true);
			break;
		}
		mEmptyType = type;
	}

	public int getEmptyType() {
		return mEmptyType;
	}

	public void setEmptyStr(String str) {
		mEmptyRes = str;
	}

}
