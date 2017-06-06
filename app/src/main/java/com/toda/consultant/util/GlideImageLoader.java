package com.toda.consultant.util;


import android.app.Activity;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.lzy.imagepicker.loader.ImageLoader;
import com.toda.consultant.R;

import java.io.File;

/**
 * Created by guugangzhu on 2017/2/8.
 */

public class GlideImageLoader implements ImageLoader {
    @Override
    public void displayImage(Activity activity, String path, ImageView imageView, int width, int height) {
        Glide.with(activity)
                .load(new File(path))//
                .placeholder(R.mipmap.default_image)//
                .error(R.mipmap.default_image)//
                .override(width, height)//
                .into(imageView);
    }

    @Override
    public void clearMemoryCache() {

    }
}
