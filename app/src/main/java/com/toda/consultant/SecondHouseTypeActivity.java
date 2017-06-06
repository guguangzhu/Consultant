package com.toda.consultant;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.flyco.tablayout.SlidingTabLayout;
import com.toda.consultant.bean.SecondCommunityBean;
import com.toda.consultant.bean.SecondDetailBean;
import com.toda.consultant.fragment.HouseTypePicFragment;
import com.toda.consultant.fragment.OverallHrefFragment;
import com.toda.consultant.fragment.SecondUnitInfoFragment;
import com.toda.consultant.model.RequestParams;
import com.toda.consultant.model.ResultData;
import com.toda.consultant.statics.Task;
import com.toda.consultant.util.DeviceUtils;
import com.toda.consultant.util.IConfig;
import com.toda.consultant.util.Ikeys;
import com.toda.consultant.util.ImageUtils;
import com.toda.consultant.util.SpanUtils;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;

/**
 * 二手房户型页面
 * Created by yangwei on 2016/11/30.
 */

public class SecondHouseTypeActivity extends BaseActivity {

    @BindView(R.id.ll_broker)
    LinearLayout llBroker;
    @BindView(R.id.iv_title)
    ImageView ivTitle;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_size)
    TextView tvSize;
    @BindView(R.id.calculator)
    ImageView calculator;
    @BindView(R.id.rl_name)
    RelativeLayout rlName;
    @BindView(R.id.line1)
    View line1;
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.tv_total_area)
    TextView tvTotalArea;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.ll_top_content)
    LinearLayout llTopContent;
    @BindView(R.id.id_stickynavlayout_topview)
    RelativeLayout idStickynavlayoutTopview;
    @BindView(R.id.line2)
    View line2;
    @BindView(R.id.id_stickynavlayout_indicator)
    SlidingTabLayout idStickynavlayoutIndicator;
    @BindView(R.id.id_stickynavlayout_viewpager)
    ViewPager idStickynavlayoutViewpager;
    @BindView(R.id.lt_features_house)
    LinearLayout ltFeaturesHouse;
    @BindView(R.id.tv_image_num)
    TextView tvImageNum;
    @BindView(R.id.tv_broker_amount)
    TextView tvBrokerNum;

    private String[] TITLE = new String[]{"简介", "全景看房", "图片"};
    private MyFragmentAdapter mAdapter;
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private Fragment secondUnitInfoFragment, overallHrefFragment, houseTypePicFragment2;
    private SecondDetailBean secondDetailBean;
    private SecondCommunityBean secondCommunityBean;
    private String secondDetailId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_house_type);
        ButterKnife.bind(this);
        initView();
        secondDetailId = getBundleStr(KEY_HOUSE_ID);
        requestSecond();
    }

    //
    private void requestSecond() {
        showDialog();
        RequestParams params = new RequestParams(IConfig.URL_GET_SECOND_ALL_DETAIL);
        params.add("secondHouseId", secondDetailId);
        startRequest(Task.GET_SECOND_ALL_DETAIL, params, SecondDetailBean.class, false);
    }

//    /**
//     * 添加收藏
//     */
//    private void addFavorite() {
//        RequestParams params = new RequestParams(IConfig.URL_ADD_FAVORITE);
//        params.add("relatedFlag", 2 + "");
//        params.add("relatedId", secondDetailBean.getSecondHouseId() + "");
//        startRequest(Task.ADD_FAVORITE, params, CommonResponseBean.class);
//    }

    private void getSecondCommunity() {
        RequestParams params = new RequestParams(IConfig.URL_GET_SECOND_ALL_DETAIL);
        params.add("secondHouseId", secondDetailId);
        startRequest(Task.GET_SECOND_ALL_DETAIL, params, SecondCommunityBean.class, false);
    }

    @Override
    public void initView() {
//        topBar.setRightSecondImg(R.mipmap.ic_empty_heart);
//        topBar.setTitleRightImg(R.mipmap.ic_share);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
//        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

//    @Override
//    public void onTopRightClick() {
//        shareHouse();
//    }

    @Override
    public void onTopRightSecondClick() {
//        if (secondDetailBean != null && !string2Boolean(secondDetailBean.getIsFavorited()))
//            addFavorite();
    }

    @Override
    public void onRefresh(Call call, int tag, ResultData data) {
        super.onRefresh(call, tag, data);
        switch (tag) {
            case Task.GET_SECOND_ALL_DETAIL:
                if (handlerRequestErr(data)) {
                    secondDetailBean = (SecondDetailBean) data.getData();
                    if (secondDetailBean == null) return;
                    getSecondCommunity();
                }
                break;
            case Task.GET_SECOND_COMMUNITY:
                dissDialog();
                if (handlerRequestErr(data)) {
                    secondCommunityBean = (SecondCommunityBean) data.getData();
                    if (secondCommunityBean == null) return;
                    setSecondDetailUi();
                }
                break;
//            case Task.ADD_FAVORITE:
//                if (handlerRequestErr(data)) {
//                    topBar.setRightSecondImg(R.mipmap.ic_heart);
//                }
//                break;
        }
    }

    /**
     * 设置二手楼盘详情
     **/
    private void setSecondDetailUi() {
        String[] imageUrl = secondDetailBean.getRealPicUrl().split(",");
        ImageUtils.loadImage(ivTitle, imageUrl[0]);
        tvImageNum.setText("1/" + imageUrl.length);
        setTitle(secondDetailBean.getRoomType() + secondDetailBean.getHallType() + " " + secondDetailBean.getHouseArea() + "㎡");
        tvName.setText(secondDetailBean.getAgentName() + " " + secondDetailBean.getDirection() + " " + secondDetailBean.getHouseProperties());
        tvSize.setText(secondDetailBean.getAreaName() + " " + secondDetailBean.getHouseAddress() + " 单价:" + secondDetailBean.getAveragePrice() + "元/㎡");
        tvTotalMoney.setText(SpanUtils.getSizeSpan(this, null, secondDetailBean.getHouseMoney() + "万", secondDetailBean.getHouseMoney() + "", 16));
        tvTotalArea.setText(SpanUtils.getSizeSpan(this, null, secondDetailBean.getHouseArea() + "㎡", secondDetailBean.getHouseArea() + "", 16));
        tvUnit.setText(secondDetailBean.getRoomType() + secondDetailBean.getHallType());
        String[] aeatures = secondDetailBean.getHouseProperties().split(",");
        setHouseFeatures(aeatures);
        tvBrokerNum.setText(secondDetailBean.getOperatorNum());

        initFragment();
        mFragments.add(secondUnitInfoFragment);
        mFragments.add(overallHrefFragment);
        mFragments.add(houseTypePicFragment2);
        mAdapter = new MyFragmentAdapter(getSupportFragmentManager());
        idStickynavlayoutViewpager.setAdapter(mAdapter);
        idStickynavlayoutIndicator.setViewPager(idStickynavlayoutViewpager);

//        if (string2Boolean(secondDetailBean.getIsFavorited())) {
//            topBar.setRightSecondImg(R.mipmap.ic_heart);
//        } else {
//            topBar.setRightSecondImg(R.mipmap.ic_empty_heart);
//        }
    }

    private void setHouseFeatures(String[] aeatures) {
        for (int i = 0; i < aeatures.length; i++) {
            TextView tv = new TextView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(DeviceUtils.dip2px(this, 5), 0, 0, 0);
            tv.setPadding(DeviceUtils.dip2px(this, 8), DeviceUtils.dip2px(this, 2), DeviceUtils.dip2px(this, 8), DeviceUtils.dip2px(this, 2));
            tv.setText(aeatures[i]);
            tv.setLayoutParams(params);
            tv.setTextSize(10);
            if ("地铁房".equals(aeatures[i])) {
                tv.setBackgroundResource(R.drawable.bg_subway_house);
                tv.setTextColor(this.getResources().getColor(R.color.subway_house));
            } else if ("学区房".equals(aeatures[i])) {
                tv.setBackgroundResource(R.drawable.bg_school_house);
                tv.setTextColor(this.getResources().getColor(R.color.school_house));
            } else if ("满五".equals(aeatures[i]) || "满二".equals(aeatures[i])) {
                tv.setBackgroundResource(R.drawable.bg_full_year);
                tv.setTextColor(this.getResources().getColor(R.color.full_year));
            } else if ("精装修".equals(aeatures[i]) || "毛坯".equals(aeatures[i])) {
                tv.setBackgroundResource(R.drawable.bg_decoration_status);
                tv.setTextColor(this.getResources().getColor(R.color.decoration_status));
            } else if ("户型好".equals(aeatures[i])) {
                tv.setBackgroundResource(R.drawable.bg_good_unit);
                tv.setTextColor(this.getResources().getColor(R.color.good_unit));
            } else {
                tv.setBackgroundResource(R.drawable.bg_riverview_house);
                tv.setTextColor(this.getResources().getColor(R.color.riverview_house));
            }
            ltFeaturesHouse.addView(tv);
        }
    }

    private void initFragment() {
        Bundle bd = new Bundle();

        secondUnitInfoFragment = new SecondUnitInfoFragment();
        bd.putSerializable(SecondUnitInfoFragment.KEY_SECOND_DETAIL, secondDetailBean);
        bd.putSerializable(SecondUnitInfoFragment.KEY_SECOND_COMMUNITY, secondCommunityBean);
        secondUnitInfoFragment.setArguments(bd);

//        houseTypePicFragment1 = new HouseTypePicFragment();
//        bd.putString(Ikeys.KEY_IMAGE_URL, secondDetailBean.getOverallFileUrl());
//        houseTypePicFragment1.setArguments(bd);

        overallHrefFragment = new OverallHrefFragment();
        bd.putString(Ikeys.KEY_OVERALLHREF_IMAGE_URL, secondDetailBean.getOverallFileUrl());
        bd.putString(Ikeys.KEY_OVERALLHREF_H5_URL, secondDetailBean.getOverallHrefUrl());
        overallHrefFragment.setArguments(bd);


        houseTypePicFragment2 = new HouseTypePicFragment();
        bd.putString(Ikeys.KEY_IMAGE_URL, secondDetailBean.getRealPicUrl());
        houseTypePicFragment2.setArguments(bd);
    }

//    /**
//     * 分享
//     */
//    private void shareHouse() {
//        new ShareAction(SecondHouseTypeActivity.this).withText("test share")
//                .setDisplayList(SHARE_MEDIA.SINA, SHARE_MEDIA.QZONE, SHARE_MEDIA.WEIXIN_CIRCLE)
//                .setCallback(umShareListener).open();
//    }
//
//    private UMShareListener umShareListener = new UMShareListener() {
//        @Override
//        public void onResult(SHARE_MEDIA platform) {
//            Log.d("plat", "platform" + platform);
//
//            Toast.makeText(SecondHouseTypeActivity.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
//
//        }
//
//        @Override
//        public void onError(SHARE_MEDIA platform, Throwable t) {
//            Toast.makeText(SecondHouseTypeActivity.this, platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
//            if (t != null) {
//                Log.d("throw", "throw:" + t.getMessage());
//            }
//        }
//
//        @Override
//        public void onCancel(SHARE_MEDIA platform) {
//            Toast.makeText(SecondHouseTypeActivity.this, platform + " 分享取消了", Toast.LENGTH_SHORT).show();
//        }
//    };

//    @OnClick({R.id.iv_title, R.id.calculator, R.id.btn_group_buy, R.id.btn_order_watch, R.id.btn_recommend, R.id.ll_broker})
//    public void onClick(View view) {
//        switch (view.getId()) {
//            case R.id.iv_title: {
//                Bundle bd = new Bundle();
//                bd.putString(Ikeys.KEY_IMAGE_URL, secondDetailBean.getRealPicUrl());
//                goPage(PhotoViewActivity.class, bd);
//            }
//            break;
//            case R.id.calculator:
//                goPage(CalculatorActivity.class);
//                break;
//            case R.id.btn_group_buy:  //团购买房
//                break;
//            case R.id.btn_order_watch: { //预约看房
//                if (secondDetailBean == null)
//                    return;
//                Bundle bundle = new Bundle();
//                bundle.putString(KEY_HOUSE_ID, secondDetailBean.getSecondHouseId() + "");
//                bundle.putString(KEY_HOUSE_FLAG, "2");
//                goPage(OrderWatchActivity.class, bundle);
//            }
//            break;
//            case R.id.btn_recommend:  //推荐赚佣
//                if (secondDetailBean == null)
//                    return;
//                Bundle bundle1 = new Bundle();
//                bundle1.putString(KEY_HOUSE_ID, secondDetailBean.getSecondHouseId() + "");
//                bundle1.putString(KEY_HOUSE_FLAG, "2");
//                goPage(RecommendActivity.class, bundle1);
//                break;
//            case R.id.ll_broker: {
//                Bundle bundle = new Bundle();
//                bundle.putString(KEY_HOUSE_ID, secondDetailBean.getSecondHouseId() + "");
//                goPage(ChooseBrokerActivity.class, bundle);
//            }
//
//            break;
//        }
//    }

    private class MyFragmentAdapter extends FragmentPagerAdapter {
        FragmentManager fm;

        public MyFragmentAdapter(FragmentManager fm) {
            super(fm);
            this.fm = fm;
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return TITLE[position % TITLE.length];
        }

        @Override
        public int getCount() {
            return TITLE.length;
        }

        @Override
        public Fragment instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container,
                    position);
            fm.beginTransaction().show(fragment).commitAllowingStateLoss();
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            // super.destroyItem(container, position, object);
            Fragment fragment = mFragments.get(position);
            fm.beginTransaction().hide(fragment).commitAllowingStateLoss();

        }

    }
}
