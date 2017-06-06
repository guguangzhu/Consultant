package com.toda.consultant.fragment;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.toda.consultant.ChatActivity;
import com.toda.consultant.ClientDetailActivity;
import com.toda.consultant.R;
import com.toda.consultant.adapter.CounselorAdapter;
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
 * 客户列表
 * Created by guugangzhu on 2016/12/21.
 */

public class ClientListFragment extends BaseFragment implements AdapterView.OnItemClickListener,CounselorAdapter.OnMessageClickListener ,CommonListView.OnRefreshListener{

    @BindView(R.id.lv_message)
    CommonListView lvMessage;

    List<String> mList=new ArrayList<>();

    CounselorAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_client_list, container, false);
        ButterKnife.bind(this, view);
        initView(view);
        lvMessage.refresh(false);
        return view;
    }

    @Override
    public void initView(View view) {
        lvMessage.getListView().setDivider(new ColorDrawable(getContext().getResources().getColor(R.color.line_color)));
        lvMessage.getListView().setDividerHeight(DeviceUtils.dip2px(getContext(),1));
        lvMessage.getListView().setOnItemClickListener(this);
        lvMessage.setListener(this);
        List<String> list = new ArrayList<>();
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        list.add("");
        adapter=new CounselorAdapter(getContext(),mList);
        adapter.setOnMessageClickListener(this);
        lvMessage.setList(mList);
        lvMessage.setAdapter(adapter);
        lvMessage.notifyDataSetChanged(list);
    }

    @Override
    protected void onPreAndVisible() {
        super.onPreAndVisible();

    }

    /***
     * 客户列表
     */
    private void getClientList(int pageNum) {
        RequestParams params = new RequestParams(IConfig.URL_CLIENT_LIST);
        params.add("pageNum", pageNum + "");
        params.add("numPerPage", pageSize + "");
        startRequest(Task.CLIENT_LIST, params, null, false);
    }


    @Override
    public void onRefresh(Call call, int tag, ResultData data) {
        super.onRefresh(call, tag, data);
        switch (tag) {
            case Task.CLIENT_LIST:
                if (HandlerRequestErr.handlerListViewErr(getContext(), lvMessage, data)) {
//                    SecondHouseListBean bean = (SecondHouseListBean) data.getData();
//                    List<SecondHouseListBean.ListBean> list = bean.getList();
//                    lvList.notifyDataSetChanged(list);
                }
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        goPage(ClientDetailActivity.class);
    }

    @Override
    public void onMessageClick(int position) {
        Bundle bundle=new Bundle();
        bundle.putString(KEY_IM_ID,"123456");
        bundle.putString(KEY_DATA,"测试A");
        goPage(ChatActivity.class,bundle);
    }

    @Override
    public void onPullDownToRefresh(int currentPage) {
        getClientList(currentPage);
    }

    @Override
    public void onPullUpToRefresh(int currentPage) {
        getClientList(currentPage);
    }
}
