package com.toda.consultant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.toda.consultant.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 类描述：二手房
 * 创建人：G.G.Z
 * 创建时间：16/3/18 19:41
 */
public class CounselorAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> list;
    private LayoutInflater mInflater;
    private OnMessageClickListener listener;

    public CounselorAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_counselor, null);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }

        mViewHolder.btnChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onMessageClick(position);
                }
            }
        });


        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.iv_logo)
        CircleImageView ivLogo;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.iv_sex)
        ImageView ivSex;
        @BindView(R.id.ratingbar)
        RatingBar ratingbar;
        @BindView(R.id.tv_company)
        TextView tvCompany;
        @BindView(R.id.tv_success_times)
        TextView tvSuccessTimes;
        @BindView(R.id.tv_current_status)
        TextView tvCurrentStatus;
        @BindView(R.id.btn_chat)
        ImageView btnChat;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setOnMessageClickListener(OnMessageClickListener listener){
        this.listener=listener;
    }

    public interface OnMessageClickListener{
       void onMessageClick(int position);
    }
}
