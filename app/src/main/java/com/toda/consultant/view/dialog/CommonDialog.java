package com.toda.consultant.view.dialog;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Display;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.toda.consultant.R;
import com.toda.consultant.util.DeviceUtils;


public class CommonDialog extends Dialog implements
		View.OnClickListener {

	private Context mContext;
	private TextView tv_title, tv_content;
	private Button btn_right, btn_left;
	private View line;
	private CallBackListener mLeftCallBack, mRightCallBack;

	public CommonDialog(Context context, CharSequence title, CharSequence content,
						String leftBtnStr, String rightBtnStr,
						CallBackListener leftCallBack, CallBackListener rightCallBack) {
		this(context);
		setTitle(title);
		setContent(content);
		setLeftButton(leftBtnStr, leftCallBack);
		setRightButton(rightBtnStr, rightCallBack);
		this.setCanceledOnTouchOutside(false);
	}

	public CommonDialog(Context context) {
		super(context, R.style.customdialog);
		this.mContext = context;
		setContentView(R.layout.common_dialog);
		initView();
		
		Display display = this.getWindow().getWindowManager().getDefaultDisplay();
		this.getWindow().setLayout((int) (display.getWidth() * 0.85), LayoutParams.WRAP_CONTENT);

	}

	private void initView() {
		tv_title = (TextView) findViewById(R.id.tv_title);
		tv_content = (TextView) findViewById(R.id.tv_content);
		btn_left = (Button) findViewById(R.id.btn_left);
		btn_right = (Button) findViewById(R.id.btn_right);
		line = findViewById(R.id.line);
		btn_right.setOnClickListener(this);
		btn_left.setOnClickListener(this);
	}
public void setTitleContentSize(float size){
	tv_content.setTextSize(size);
}
	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.btn_left:
			if (mLeftCallBack != null)
				mLeftCallBack.callBack();
			dismiss();
			break;
		case R.id.btn_right:
			if (mRightCallBack != null)
				mRightCallBack.callBack();
			dismiss();
			break;
		}
	}

	public void setTitle(CharSequence title) {
		setTitle(title, 0);
	}

	public void setTitle(CharSequence title, int color) {
		if (TextUtils.isEmpty(title)) {
			tv_title.setVisibility(View.GONE);
		} else {
			tv_title.setVisibility(View.VISIBLE);
			tv_title.setText(title);
		}
		if (color != 0) {
			tv_title.setTextColor(mContext.getResources().getColor(color));
		}
	}

	public void setContent(CharSequence content) {
		setContent(content, 0);
	}

	public void setContent(CharSequence content, int color) {
		content = content == null ? "" : content;
		tv_content.setText(content);
		if (color != 0) {
			tv_content
					.setTextColor(mContext.getResources().getColor(color));
		}

	}

	public void setLeftButton(String leftStr, CallBackListener listener) {
		setLeftButton(leftStr, 0, listener);
	}

	public void setLeftButton(String leftStr, int color,
							  CallBackListener listener) {
		leftStr = leftStr == null ? "取消" : leftStr;
		btn_left.setText(leftStr);
		if (color != 0) {
			btn_left.setTextColor(mContext.getResources().getColor(color));
		}
		this.mLeftCallBack = listener;
	}

	public void setRightButton(String rightStr, CallBackListener listener) {
		setRightButton(rightStr, 0, listener);
	}

	public void setRightButton(String rightStr, int color,
							   CallBackListener listener) {
		if (TextUtils.isEmpty(rightStr)) {
			line.setVisibility(View.GONE);
			btn_right.setVisibility(View.GONE);
			return;
		}

		line.setVisibility(View.VISIBLE);
		btn_right.setVisibility(View.VISIBLE);
		btn_right.setText(rightStr);
		if (color != 0) {
			btn_right.setTextColor(mContext.getResources().getColor(color));
		}
		this.mRightCallBack = listener;
	}
	
	public void setContentGravity(int gravity){
//		LinearLayout.LayoutParams lp=(LinearLayout.LayoutParams)tv_content.getLayoutParams();
//		lp.gravity=gravity;
//		tv_content.setLayoutParams(lp);
		tv_content.setGravity(gravity);
	}

	public interface CallBackListener {
		void callBack();
	}
	
	@Override
	public void show() {
		if(tv_title.getVisibility()!= View.VISIBLE){
			tv_content.setMinHeight(DeviceUtils.dip2px(mContext, 50));
		}
		super.show();
	}
	
	/** 获取内容的textview **/
	public TextView getContentTextView(){
		return tv_content;
	}
}