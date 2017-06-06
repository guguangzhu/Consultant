package com.toda.consultant;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import com.toda.consultant.adapter.MyHouseListAdapter;
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
 * Created by guugangzhu on 2016/12/28.
 */

public class MyHouseListActivity extends BaseActivity implements MyHouseListAdapter.OnDeleteClickListener ,CommonListView.OnRefreshListener{
    private MyHouseListAdapter adapter;
    List<String> mList=new ArrayList<>();

    @BindView(R.id.lv_list)
    CommonListView lvList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_house_list);
        ButterKnife.bind(this);
        initView();
        lvList.refresh(false);
    }

    @Override
    public void initView() {
        setTitle("我的房源");
        lvList.getListView().setDivider(new ColorDrawable(this.getResources().getColor(R.color.line_color)));
        lvList.getListView().setDividerHeight(DeviceUtils.dip2px(this,1));
        lvList.setListener(this);
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        lvList.setList(mList);
        adapter = new MyHouseListAdapter(this, list);
        adapter.setOnDeleteClickListener(this);
//        lvList.getListView().setOnItemClickListener(this);
        lvList.setAdapter(adapter);
        lvList.notifyDataSetChanged(list);
    }

    /***
     * 代理房源列表
     */
    private void getHouseList(int pageNum) {
        RequestParams params = new RequestParams(IConfig.URL_GET_HOUSE_LIST);
        params.add("pageNum", pageNum + "");
        params.add("numPerPage", pageSize + "");
        startRequest(Task.GET_AGENT_HOUSE_LIST, params, null, false);
    }

    @Override
    public void onRefresh(Call call, int tag, ResultData data) {
        super.onRefresh(call, tag, data);
        switch (tag) {
            case Task.GET_AGENT_HOUSE_LIST:
                if (HandlerRequestErr.handlerListViewErr(this, lvList, data)) {
//                    SecondHouseListBean bean = (SecondHouseListBean) data.getData();
//                    List<SecondHouseListBean.ListBean> list = bean.getList();
//                    lvList.notifyDataSetChanged(list);
                }
        }
    }

    @Override
    public void onDeleteClick(int position) {
        toast("onDeleteClick");
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
