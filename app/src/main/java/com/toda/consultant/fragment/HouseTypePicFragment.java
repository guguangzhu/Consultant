package com.toda.consultant.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.toda.consultant.PhotoViewActivity;
import com.toda.consultant.R;
import com.toda.consultant.adapter.EstateTypeAdapter;
import com.toda.consultant.util.Ikeys;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * 户型图片
 * Created by guugangzhu on 2016/9/28.
 */

public class HouseTypePicFragment extends BaseFragment implements AdapterView.OnItemClickListener {
    private ArrayList<String> imageUrlList;

    @BindView(R.id.id_stickynavlayout_innerscrollview)
    GridView lvPicture;

    private String imageUrl;

    public static HouseTypePicFragment newInstance() {
        HouseTypePicFragment fragment = new HouseTypePicFragment();
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
        imageUrl = bd.getString(Ikeys.KEY_IMAGE_URL);
        if (TextUtils.isEmpty(imageUrl)) return;
        String[] imageUrlArray = imageUrl.split(",");
        imageUrlList = new ArrayList<>();
        for (int i = 0; i < imageUrlArray.length; i++) {
            imageUrlList.add(imageUrlArray[i]);
        }
        EstateTypeAdapter adapter = new EstateTypeAdapter(getContext(), imageUrlList);
        lvPicture.setAdapter(adapter);
    }


    @Override
    public void initView(View view) {
        ButterKnife.bind(this, view);
        lvPicture.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle bd = new Bundle();
        bd.putString(Ikeys.KEY_IMAGE_URL, imageUrl);
        goPage(PhotoViewActivity.class, bd);
    }
}
