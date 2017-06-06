package com.toda.consultant.app;

import android.app.Application;
import android.content.Context;

import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.view.CropImageView;
import com.toda.consultant.util.FileUtils;
import com.toda.consultant.util.GlideImageLoader;
import com.toda.consultant.util.IConfig;
import com.toda.consultant.util.ImageUtils;
import com.toda.consultant.util.cache.ShareData;

import java.io.File;

import io.rong.imageloader.cache.disc.impl.UnlimitedDiskCache;
import io.rong.imageloader.cache.disc.naming.Md5FileNameGenerator;
import io.rong.imageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import io.rong.imageloader.core.ImageLoader;
import io.rong.imageloader.core.ImageLoaderConfiguration;
import io.rong.imageloader.core.assist.QueueProcessingType;
import io.rong.imkit.RongIM;

/**
 * Created by guugangzhu on 2016/9/23.
 */

public class BrokerApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        ShareData.init(this);
        RongIM.init(this);
        initImageLoader(getApplicationContext());
        initImagePicker();
    }

    public static void initImageLoader(Context context) {
        File dir = new File(FileUtils.getFilePath(IConfig.IMAGE_CACHE));
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(context).threadPoolSize(3)
                .threadPriority(Thread.NORM_PRIORITY - 2)
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024))
                .denyCacheImageMultipleSizesInMemory().diskCache(new UnlimitedDiskCache(dir))
                .diskCacheFileNameGenerator(new Md5FileNameGenerator())
                .tasksProcessingOrder(QueueProcessingType.LIFO)
                .defaultDisplayImageOptions(ImageUtils.getDisplayImageOptions())
                .build();
        ImageLoader.getInstance().init(config);
    }

    private void initImagePicker(){
        ImagePicker imagePicker = ImagePicker.getInstance();
        imagePicker.setImageLoader(new GlideImageLoader());   //设置图片加载器
        imagePicker.setShowCamera(true);  //显示拍照按钮
        imagePicker.setCrop(true);        //允许裁剪（单选才有效）
        imagePicker.setSaveRectangle(true); //是否按矩形区域保存
        imagePicker.setSelectLimit(9);    //选中数量限制
        imagePicker.setStyle(CropImageView.Style.RECTANGLE);  //裁剪框的形状
//        imagePicker.setFocusWidth(800);   //裁剪框的宽度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setFocusHeight(800);  //裁剪框的高度。单位像素（圆形自动取宽高最小值）
//        imagePicker.setOutPutX(1000);//保存文件的宽度。单位像素
//        imagePicker.setOutPutY(1000);//保存文件的高度。单位像素
    }
}
