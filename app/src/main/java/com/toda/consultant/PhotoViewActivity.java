package com.toda.consultant;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;


import com.toda.consultant.util.Ikeys;
import com.toda.consultant.util.ImageUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import uk.co.senab.photoview.PhotoView;

/**
 * Created by guugangzhu on 2016/10/12.
 */

public class PhotoViewActivity extends BaseActivity {

    @BindView(R.id.vp_photo)
    ViewPager vpPhoto;

    private String[] imageUrlArray;
    private String imageUrlStr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_view);
        ButterKnife.bind(this);
        imageUrlStr = getBundleStr(Ikeys.KEY_IMAGE_URL);
//        imageUrlStr="http://dynamic-image.yesky.com/740x-/uploadImages/2016/341/52/PADK5I4JU12B.jpg,http://p2.so.qhimg.com/t01cb056a86c851f0b9.jpg,http://img.netbian.com/file/2016/1202/8444b67eda03527563a926f4e85a96ba.jpg";
        initView();
    }

    @Override
    public void initView() {
        imageUrlArray = imageUrlStr.split(",");
        vpPhoto.setAdapter(new SamplePagerAdapter());
    }

    class SamplePagerAdapter extends PagerAdapter {

//        private static final int[] sDrawables = {R.mipmap.bg_login_one, R.mipmap.bg_login_two, R.mipmap.bg_login_three};

        @Override
        public int getCount() {
            return imageUrlArray.length;
        }

        @Override
        public View instantiateItem(ViewGroup container, int position) {
            PhotoView photoView = new PhotoView(container.getContext());
//            ImageView iv = new ImageView(container.getContext());
//            iv.
//            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            ImageUtils.loadImage(photoView, imageUrlArray[position]);
//            ImageUtils.loadImage(photoView, "http://dynamic-image.yesky.com/740x-/uploadImages/2016/341/52/PADK5I4JU12B.jpg");
//            PhotoViewAttacher attacher = new PhotoViewAttacher(iv);
//            photoView.
//            photoView.setImageResource(iv);
//            photoView.`

            // Now just add PhotoView to ViewPager and return it
//            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            return photoView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

    }
}
