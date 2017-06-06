package com.toda.consultant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;


import com.toda.consultant.CommonWebActivity;
import com.toda.consultant.R;
import com.toda.consultant.adapter.EstateTypeAdapter;
import com.toda.consultant.util.Ikeys;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 全景图展示 fragment
 * Created by yangwei on 2017/1/4.
 */

public class OverallHrefFragment extends BaseFragment implements AdapterView.OnItemClickListener {

    private ArrayList<String> imageUrlList;

    @BindView(R.id.id_stickynavlayout_innerscrollview)
    GridView lvPicture;

    private String overallFileUrl;
    private String overallHrefUrl;
    private String[] h5UrlArray;
    private EstateTypeAdapter adapter;

    public static OverallHrefFragment newInstance(Bundle bd) {
        OverallHrefFragment fragment = new OverallHrefFragment();
        fragment.setArguments(bd);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_house_pic, container, false);
        initView(view);
        getData();
        return view;
    }

    private void getData() {
        Bundle bd = this.getArguments();
        if (bd == null) return;
        overallFileUrl = bd.getString(Ikeys.KEY_OVERALLHREF_IMAGE_URL);
        overallHrefUrl = bd.getString(Ikeys.KEY_OVERALLHREF_H5_URL);
        setUi();
    }

    public void setUrl(String overallFileUrl, String overallHrefUrl) {
        this.overallFileUrl = overallFileUrl;
        this.overallHrefUrl = overallHrefUrl;
        setUi();
    }

    private void setUi() {
        if (TextUtils.isEmpty(overallFileUrl) || TextUtils.isEmpty(overallHrefUrl)) return;
        String[] imageUrlArray = overallFileUrl.split(",");
        h5UrlArray = overallHrefUrl.split(",");
        imageUrlList = new ArrayList<>();
        for (int i = 0; i < imageUrlArray.length; i++) {
            imageUrlList.add(imageUrlArray[i]);
        }
        if (adapter == null){
            EstateTypeAdapter adapter = new EstateTypeAdapter(getActivity(), imageUrlList);
            lvPicture.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }
    }


    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        lvPicture.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            Bundle bd = new Bundle();
            bd.putString(Ikeys.KEY_DATA, h5UrlArray[position]);http://120.26.227.108/testpano/index.html
            bd.putString(Ikeys.KEY_DATA, "http://120.26.227.108/testpano/index.html");
            goPage(CommonWebActivity.class, bd);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
