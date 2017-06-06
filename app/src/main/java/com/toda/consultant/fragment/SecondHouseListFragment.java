package com.toda.consultant.fragment;


import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.toda.consultant.HouseInfoActivity;
import com.toda.consultant.R;
import com.toda.consultant.SecondHouseTypeActivity;
import com.toda.consultant.adapter.HouseListAdapter;
import com.toda.consultant.bean.SecondHouseListBean;
import com.toda.consultant.model.RequestParams;
import com.toda.consultant.model.ResultData;
import com.toda.consultant.statics.Task;
import com.toda.consultant.util.DeviceUtils;
import com.toda.consultant.util.HandlerRequestErr;
import com.toda.consultant.util.IConfig;
import com.toda.consultant.view.CommonListView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;


/**
 * 二手房源
 */
public class SecondHouseListFragment extends BaseFragment implements AdapterView.OnItemClickListener, CommonListView.OnRefreshListener {
    @BindView(R.id.lv_list)
    CommonListView lvList;

    private HouseListAdapter adapter;
    List<SecondHouseListBean.ListBean> mList = new ArrayList<>();

    public SecondHouseListFragment() {
        // Required empty public constructor
    }


    public static SecondHouseListFragment newInstance() {
        SecondHouseListFragment fragment = new SecondHouseListFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_second_list, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        lvList.refresh(false);
        return view;
    }


    /***
     * 二手房源列表
     */
    private void getHouseList(int pageNum) {
        RequestParams params = new RequestParams(IConfig.URL_SECOND_HOUSE_LIST);
        params.add("pageNum", pageNum + "");
        params.add("numPerPage", pageSize + "");
        startRequest(Task.SECOND_HOUSE_LIST, params, SecondHouseListBean.class, false);
    }

    @Override
    public void onRefresh(Call call, int tag, ResultData data) {
        super.onRefresh(call, tag, data);
        switch (tag) {
            case Task.SECOND_HOUSE_LIST:
                if (HandlerRequestErr.handlerListViewErr(getContext(), lvList, data)) {
                    SecondHouseListBean bean = (SecondHouseListBean) data.getData();
                    List<SecondHouseListBean.ListBean> list = bean.getList();
                    lvList.notifyDataSetChanged(list);
                }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void initView(View view) {
        lvList.getListView().setDivider(new ColorDrawable(getContext().getResources().getColor(R.color.line_color)));
        lvList.getListView().setDividerHeight(DeviceUtils.dip2px(getContext(), 1));
        lvList.setListener(this);
        lvList.setList(mList);
        adapter = new HouseListAdapter(getContext(), mList);
        lvList.getListView().setOnItemClickListener(this);
        lvList.setAdapter(adapter);

    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        SecondHouseListBean.ListBean bean = mList.get(position - lvList.getListView().getHeaderViewsCount());
        Bundle bundle = new Bundle();
        bundle.putString(KEY_HOUSE_ID, bean.getSecondHousePersonalId() + "");
        if (bean.getIsChecked() == 1) {  //已核验
            goPage(SecondHouseTypeActivity.class, bundle);
        } else { //待核验
            goPage(HouseInfoActivity.class, bundle);
        }

    }

    @Override
    public void onPullDownToRefresh(int currentPage) {
        getHouseList(currentPage);
    }

    @Override
    public void onPullUpToRefresh(int currentPage) {
        getHouseList(currentPage);
    }
}
