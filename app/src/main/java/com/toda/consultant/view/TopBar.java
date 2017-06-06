package com.toda.consultant.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.toda.consultant.R;


/**
 * 标题
 */
public class TopBar extends RelativeLayout {
    private boolean lineVisible;
    private Drawable contentBackground;
    private int titleTextColor;
    private float titleTextSize;
    private String titleName;

    private String leftBtnName;
    private float leftBtnMargin;
    private int leftTextColor;
    private Drawable leftButtonBackground;
    private boolean leftButtonVisible;
    private float leftTextSize;

    private String rightBtnName;
    private float rightBtnMargin;
    private int rightTextColor;
    private Drawable rightButtonBackground;
    private boolean rightButtonVisible;
    private float rightTextSize;

    private TextView mTvLeftBtn;
    private TextView mTvRightBtn;
    private TextView mTvTitle;
    private TextView tvLeftSecond;
    private TextView tvRightSecond;

    private LinearLayout llLeft;
    private LinearLayout llRight;
    private LinearLayout llCenter;
    private LinearLayout llLeftSecond;
    private LinearLayout llRightSecond;

    private View contentView;
    private View viewLine;


    public TopBar(Context context) {
        super(context);
    }

    public TopBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray styleable = context.obtainStyledAttributes(attrs, R.styleable.TopTitle);
        if (styleable != null) {
            titleName = styleable.getString(R.styleable.TopTitle_titleContentName);
            titleTextSize = styleable.getDimension(R.styleable.TopTitle_titleTextSize, 0);
            titleTextColor = styleable.getColor(R.styleable.TopTitle_titleContentColor, 0);

            leftBtnName = styleable.getString(R.styleable.TopTitle_leftButtonName);
            leftBtnMargin = styleable.getDimension(R.styleable.TopTitle_leftButtonMargin, 0);
            leftTextSize = styleable.getDimension(R.styleable.TopTitle_leftTextSize, 0);
            leftTextColor = styleable.getColor(R.styleable.TopTitle_leftTextColor, 0);
            leftButtonBackground = styleable.getDrawable(R.styleable.TopTitle_leftButtonBackground);
            leftButtonVisible = styleable.getBoolean(R.styleable.TopTitle_leftButtonVisible, true);

            rightBtnName = styleable.getString(R.styleable.TopTitle_rightButtonName);
            rightBtnMargin = styleable.getDimension(R.styleable.TopTitle_rightButtonMargin, 0);
            rightTextSize = styleable.getDimension(R.styleable.TopTitle_rightTextSize, 0);
            rightTextColor = styleable.getColor(R.styleable.TopTitle_rightTextColor, 0);
            rightButtonBackground = styleable.getDrawable(R.styleable.TopTitle_rightButtonBackground);
            rightButtonVisible = styleable.getBoolean(R.styleable.TopTitle_rightButtonVisible, true);

            lineVisible = styleable.getBoolean(R.styleable.TopTitle_lineVisible, false);
            contentBackground = styleable.getDrawable(R.styleable.TopTitle_topTitleBackground);
            styleable.recycle();
        }

        View.inflate(getContext(), R.layout.top_bar, this);
        initView();
    }

    private void initView(){
        contentView = (LinearLayout) findViewById(R.id.llt_parent);
        llLeft = (LinearLayout) findViewById(R.id.ll_left);
        llCenter = (LinearLayout) findViewById(R.id.ll_centent);
        llRight = (LinearLayout) findViewById(R.id.ll_right);
        llLeftSecond = (LinearLayout) findViewById(R.id.ll_left_second);
        llRightSecond = (LinearLayout) findViewById(R.id.ll_right_second);
        mTvLeftBtn = (TextView) findViewById(R.id.tv_left);
        mTvRightBtn = (TextView) findViewById(R.id.tv_right);
        tvLeftSecond = (TextView) findViewById(R.id.tv_left_second);
        tvRightSecond = (TextView) findViewById(R.id.tv_right_second);
        mTvTitle = (TextView) findViewById(R.id.tv_cent);
        viewLine = (View) findViewById(R.id.view_top_bar_line);
        if (titleTextSize != 0.0)
            mTvTitle.setTextSize(titleTextSize);
        if (titleTextColor != -1 && titleTextColor != 0)
            mTvTitle.setTextColor(titleTextColor);
        if (!TextUtils.isEmpty(titleName))
            mTvTitle.setText(titleName);


        if (!TextUtils.isEmpty(leftBtnName))
            mTvLeftBtn.setText(leftBtnName);
        if (Build.VERSION.SDK_INT > 16) {
            mTvLeftBtn.setBackground(leftButtonBackground);
        } else {
            mTvLeftBtn.setBackgroundDrawable(leftButtonBackground);
        }
        if (leftTextColor != -1 && leftTextColor != 0)
            mTvLeftBtn.setTextColor(leftTextColor);
        if (leftButtonVisible) {
            llLeft.setVisibility(View.VISIBLE);
        } else {
            llLeft.setVisibility(View.INVISIBLE);
        }
        if (leftTextSize != 0.0)
            mTvLeftBtn.setTextSize(leftTextSize);


        if (rightTextSize != -0.0) {
            mTvRightBtn.setTextSize(rightTextSize);
        }

        if (!TextUtils.isEmpty(rightBtnName))
            mTvRightBtn.setText(rightBtnName);
        if (Build.VERSION.SDK_INT > 16) {
            mTvRightBtn.setBackground(rightButtonBackground);
        } else {
            mTvRightBtn.setBackgroundDrawable(rightButtonBackground);
        }
        if (rightTextColor != -1 && rightTextColor != 0) {
            mTvRightBtn.setTextColor(rightTextColor);
        }
        if (contentBackground != null) {
            if (Build.VERSION.SDK_INT > 16) {
                contentView.setBackground(contentBackground);
            } else {
                contentView.setBackgroundDrawable(contentBackground);
            }
        }

        if (rightButtonVisible) {
            llRight.setVisibility(View.VISIBLE);
        } else {
            llRight.setVisibility(View.INVISIBLE);
        }
        if (lineVisible) {
            viewLine.setVisibility(View.VISIBLE);
        } else {
            viewLine.setVisibility(View.GONE);
        }
        llLeft.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTopLeftClick();
                }
            }
        });
        llRight.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTopRightClick();
                }
            }
        });

        llLeftSecond.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTopLeftSecondClick();
                }
            }
        });

        llRightSecond.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTopLeftSecondClick();
                }
            }
        });

        llCenter.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onTopCenterClick();
                }
            }
        });
    }

    private OnTopBarClickListener listener;

    public void setOnTopBarClickListener(OnTopBarClickListener listener) {
        this.listener = listener;
    }

    public interface OnTopBarClickListener {
        void onTopLeftClick();

        void onTopRightClick();

        void onTopLeftSecondClick();

        void onTopRightSecondClick();

        void onTopCenterClick();
    }

    public void setTitleText(String str) {
        mTvTitle.setText(str);
    }

    public void setTitleTextColor(int color) {
        mTvTitle.setTextColor(color);
    }

    public void setTitleRightTextColor(int color){
        mTvRightBtn.setTextColor(color);
    }

    public void setTitleLeftText(String str) {
        mTvLeftBtn.setBackgroundResource(0);
        llLeft.setVisibility(View.VISIBLE);
        mTvLeftBtn.setText(str);
    }

    public void setTitleRightText(String str) {
        mTvRightBtn.setBackgroundResource(0);
        llRight.setVisibility(View.VISIBLE);
        mTvRightBtn.setText(str);
    }

    public void setLeftSecondText(String str) {
        llLeftSecond.setVisibility(View.VISIBLE);
        tvLeftSecond.setBackgroundResource(0);
        tvLeftSecond.setText(str);
    }
    public void setRightSecondText(String str) {
        llRightSecond.setVisibility(View.VISIBLE);
        tvRightSecond.setBackgroundResource(0);
        tvRightSecond.setText(str);
    }

    public void setTitleLeftImg(int imgId) {
        llLeft.setVisibility(View.VISIBLE);
        mTvLeftBtn.setBackgroundResource(imgId);
    }

    public void setTitleRightImg(int imgId) {
        llRight.setVisibility(View.VISIBLE);
        mTvRightBtn.setBackgroundResource(imgId);
    }
    public void setLeftSecondImg(int imgId) {
        llLeftSecond.setVisibility(View.VISIBLE);
        tvLeftSecond.setBackgroundResource(imgId);
    }
    public void setRightSecondImg(int imgId) {
        llRightSecond.setVisibility(View.VISIBLE);
        tvRightSecond.setBackgroundResource(imgId);
    }

    public void hideLeft(){
        llLeft.setVisibility(View.INVISIBLE);
    }

    public void hideRight(){
        llRight.setVisibility(View.INVISIBLE);
    }

    public void hideLeftSecond(){
        llLeftSecond.setVisibility(View.GONE);
    }


    /***
     * 设置top透明
     */
    public void setTopBarTrans() {
        contentView.setBackgroundColor(Color.TRANSPARENT);
        viewLine.setVisibility(View.GONE);
    }

    public TextView getTopBarLeftTextView(){
        return mTvLeftBtn;
    }

    public void setLineVisible(int visible){
        viewLine.setVisibility(visible);
    }

    public View getContentView(){

        return contentView;
    }

    public TextView getTitleView(){
        return mTvTitle;
    }
    public TextView getRightView(){
        return mTvRightBtn;
    }

    @Override
    public void setBackgroundColor(int color) {
        contentView.setBackgroundColor(color);
    }
}
