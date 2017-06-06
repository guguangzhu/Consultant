package com.toda.consultant.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.RelativeLayout;

import com.toda.consultant.R;
import com.toda.consultant.util.DeviceUtils;

/**
 * Created by yangwei on 2016/12/1.
 */

public class UnitDescriptionLayout extends RelativeLayout {

    private Context mContext;
    private View line;

    public UnitDescriptionLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mContext = context;
        initBottomLine();
    }

    private void initBottomLine() {
        line = new View(mContext);
        LayoutParams rl = new LayoutParams(LayoutParams.MATCH_PARENT, DeviceUtils.dip2px(mContext, 1));
        rl.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//        rl.setMargins(DeviceUtils.dip2px(mContext, 15), 0, 0, 0);
        line.setBackgroundColor(mContext.getResources().getColor(R.color.line_color));
        line.setLayoutParams(rl);
        addView(line);
    }
}