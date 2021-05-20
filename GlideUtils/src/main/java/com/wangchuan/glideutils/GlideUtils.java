package com.wangchuan.glideutils;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.load.MultiTransformation;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.bumptech.glide.request.RequestOptions;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.ColorFilterTransformation;

/**
 * @author wangchuan
 * @date 2018/11/30
 * glide工具类
 */

public class GlideUtils {

    final int defautImage = GlideConfig.INSTANCE.getDefaultImg();

    private Context context;
    private RequestManager requestManager;

    private GlideUtils() {
    }

    private static GlideUtils glideUtils;

    public static GlideUtils Instance() {
        if (glideUtils == null) {
            glideUtils = new GlideUtils();
        }
        return glideUtils;
    }

    public void init(Context context) {
        this.context = context;
        requestManager = Glide.with(context);
    }

    /**
     * ---------------常规配置-----------------------
     */

    /**
     * 缓存配置
     */
    public RequestOptions getCacheOptions() {
        return GlideConfig.INSTANCE.CacheOptions();
    }


    /**
     * 加载圆形图片配置
     */
    public RequestOptions getCircleCropOptions() {
        return GlideConfig.INSTANCE.CircleCropOptions();
    }


    public GlideUrl getGlideUrl(String path) {
        if (TextUtils.isEmpty(path)) {
            return null;
        }
        GlideUrl glideUrl = new GlideUrl(path, new LazyHeaders.Builder()
                .addHeader("Referer", "*.heyporn.com/*")
                .build());
//        KLog.i("headeer", JSON.toJSON(glideUrl.getHeaders()));
        return glideUrl;
    }


    /**
     * 清除缓存
     */
    public void clear() {
        if (context == null) {
            return;
        }
        Glide.get(context).clearDiskCache();
    }

    /**
     * 公共图片加载
     */
    public void commonLoadImg(RequestOptions options, @Nullable Object model, ImageView imageView) {
        if (model == null || imageView == null || requestManager == null) {
            return;
        }
        if (model instanceof String) {
            String url = String.valueOf(model);
            if (TextUtils.isEmpty(url)) {
                return;
            }
            requestManager.load(getGlideUrl(url)).apply(options).into(imageView);
        } else {
            requestManager.load(model).apply(options).into(imageView);
        }

    }

    /**
     * 一般图片加载
     */
    public void loadImg(@Nullable Object model, ImageView imageView) {
        commonLoadImg(getCacheOptions(), model, imageView);
    }

    public void loadImg(String url, ImageView imageView) {
        commonLoadImg(getCacheOptions(), url, imageView);
    }

    public void loadImg(int width, int heigh, @Nullable Object model, ImageView imageView) {
        commonLoadImg(getCacheOptions().override(width, heigh), model, imageView);
    }

    public void loadCenterCropImg(@Nullable Object model, ImageView imageView) {
        commonLoadImg(getCacheOptions().centerCrop(), model, imageView);
    }

    /**
     * 加载圆形图片
     */
    public void loadCircleCropImg(@Nullable Object model, ImageView imageView) {
        commonLoadImg(getCircleCropOptions(), model, imageView);
    }


    /**
     * 高斯模糊效果
     *
     * @param blurRadius：模糊度 （25f是最大模糊度）
     */
    public void loadBlurImg(@Nullable Object model, ImageView imageView, int blurRadius, int sampling, @DrawableRes int resourceId) {
        MultiTransformation multi = new MultiTransformation(
//                radius ：模糊度   sampling ：采样值（值越大，模糊度效果越好）
                new BlurTransformation(blurRadius, sampling),
//                颜色处理
                new ColorFilterTransformation(Color.argb(66, 54, 54, 54)));
        RequestOptions requestOptions = RequestOptions.bitmapTransform(multi).error(resourceId);
        Glide.with(imageView.getContext())
                .load(model)
                .apply(requestOptions)
                //淡入淡出
                .transition(DrawableTransitionOptions.withCrossFade())
                .into(imageView);
    }

    public void loadBlurImg(@Nullable Object model, ImageView imageView, int radius, @DrawableRes int resourceId) {
        loadBlurImg(model, imageView, radius, 10, resourceId);
    }

    public void loadBlurImg(@Nullable Object model, ImageView imageView, int radius) {
        loadBlurImg(model, imageView, radius, 10, defautImage);
    }

}
