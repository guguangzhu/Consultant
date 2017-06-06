package com.toda.consultant.util;

import android.graphics.Bitmap;
import android.widget.ImageView;

import io.rong.imageloader.core.DisplayImageOptions;
import io.rong.imageloader.core.ImageLoader;
import io.rong.imageloader.core.assist.ImageScaleType;

/**
 * 图片处理Utils
 * Created by yangwei on 2016/11/25.
 */

public class ImageUtils {
    public static void loadImage(ImageView iv, String imageUrl) {
        ImageLoader.getInstance().displayImage(imageUrl, iv, getDisplayImageOptions());
    }

    public static DisplayImageOptions getDisplayImageOptions() {
        return getDisplayImageOptions(0, 0, 0, true, true);
    }

    /**
     * 配置如何显示图片
     *
     * @param forEmptyUri   如果图片Uri为empty的情况下，显示的图片Id
     * @param stubImage     正在加载中的情况下显示的图片Id
     * @param imageOnFial   加载图片失败情况下显示的图片Id
     * @param cacheInMemory 是否将图片保存到内存中，ture为缓存到内存中
     * @param cacheOnDisk   是否将图片保存到sd卡上，ture为缓存到sd卡上
     * @return 返回配置如何显示图片的DisplayImageOptions 对象
     */
    public static DisplayImageOptions getDisplayImageOptions(int forEmptyUri, int stubImage, int imageOnFial, boolean cacheInMemory, boolean cacheOnDisk) {
        /**
         * 配置如何显示图片
         */
        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        // 设置图片Uri为空或是错误的时候显示的图片
        builder.showImageForEmptyUri(forEmptyUri);
        // 设置图片在下载期间显示的图片
        builder.showImageOnLoading(stubImage);
        // 设置图片加载/解码过程中错误时候显示的图片
        builder.showImageOnFail(imageOnFial);
        // 设置是否将加载的图片缓存到磁盘上
        builder.cacheOnDisk(cacheOnDisk);
        // 设置是否将加载的图片缓存到内存中
        builder.cacheInMemory(cacheInMemory);
        // 默认使用RGB_565颜色显示
        builder.bitmapConfig(Bitmap.Config.RGB_565);
        builder.imageScaleType(ImageScaleType.EXACTLY);
        DisplayImageOptions options = builder.build();
        return options;
    }
}
