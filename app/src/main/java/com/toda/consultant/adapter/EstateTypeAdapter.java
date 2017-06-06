package com.toda.consultant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.toda.consultant.R;
import com.toda.consultant.util.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：楼盘户型适配
 * 创建人：G.G.Z
 * 创建时间：16/3/18 19:41
 */
public class EstateTypeAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> list;
    private LayoutInflater mInflater;

    public EstateTypeAdapter(Context mContext, List<String> list) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder mViewHolder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_estate_type, null);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
        setData(mViewHolder, position);

        return convertView;
    }

    private void setData(ViewHolder mViewHolder, int position) {
        ImageUtils.loadImage(mViewHolder.tvProvince, list.get(position));
        mViewHolder.tvCity.setVisibility(View.GONE);
    }


    static class ViewHolder {
        @BindView(R.id.tv_province)
        ImageView tvProvince;
        @BindView(R.id.tv_city)
        TextView tvCity;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
