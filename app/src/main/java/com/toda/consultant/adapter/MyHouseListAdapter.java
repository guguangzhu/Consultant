package com.toda.consultant.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.toda.consultant.R;
import com.toda.consultant.util.ImageUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 类描述：我的房源
 * 创建人：G.G.Z
 * 创建时间：16/3/18 19:41
 */
public class MyHouseListAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> list;
    private LayoutInflater mInflater;
    private OnDeleteClickListener listener;

    public MyHouseListAdapter(Context mContext, List<String> list) {
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
            convertView = mInflater.inflate(R.layout.item_my_house_list, null);
            mViewHolder = new ViewHolder(convertView);
            convertView.setTag(mViewHolder);
        } else {
            mViewHolder = (ViewHolder) convertView.getTag();
        }
//        ImageUtils.loadImage(mContext, mViewHolder.ivLogo, bean.getVirtualPicUrl());
        ImageUtils.loadImage(mViewHolder.ivLogo, "http://img1.imgtn.bdimg.com/it/u=2811846226,3534502207&fm=23&gp=0.jpg");
//        mViewHolder.tvName.setText(bean.getBuildingName());
//        mViewHolder.tvTotalMoney.setText(bean.getHouseMoney()+"万");
//        mViewHolder.tvSize.setText(bean.getHouseArea() + "㎡  " + bean.getRoomType()+"室"+bean.getHallType()+"厅"+bean.getWashroomType()+"卫");
//        int unitPrice= bean.getHouseMoney()/bean.getHouseArea();
//        mViewHolder.tvAddressPrice.setText(bean.getHouseAddress() + "  " + unitPrice + "/㎡");
//        setLabel(mViewHolder, bean);

        mViewHolder.btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onDeleteClick(position);
                }
            }
        });
        return convertView;
    }

//    private void setLabel(ViewHolder viewHolder, MyHouseBean.ListBean bean) {
//        List<TextView> list = new ArrayList<>();
//        list.add(viewHolder.label1);
//        list.add(viewHolder.label2);
//        list.add(viewHolder.label3);
//        String str = bean.getHouseProperties();
//        String[] labels = str.split(",");
//        for (int i = 0; i < labels.length; i++) {
//            if (i < 3) {
//                list.get(i).setVisibility(View.VISIBLE);
//                list.get(i).setText(labels[i]);
//            } else {
//                break;
//            }
//        }
//
//    }

    static class ViewHolder {
        @BindView(R.id.iv_logo)
        ImageView ivLogo;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.tv_size)
        TextView tvSize;
        @BindView(R.id.tv_address_price)
        TextView tvAddressPrice;
        @BindView(R.id.label_1)
        TextView label1;
        @BindView(R.id.label_2)
        TextView label2;
        @BindView(R.id.label_3)
        TextView label3;
        @BindView(R.id.ll_content)
        LinearLayout llContent;
        @BindView(R.id.tv_total_money)
        TextView tvTotalMoney;
        @BindView(R.id.btn_delete)
        TextView btnDelete;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener){
        this.listener=listener;
    }

    public interface OnDeleteClickListener{
        void onDeleteClick(int position);
    }
}
