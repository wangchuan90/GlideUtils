package com.wangchuan.glideutils

import android.support.annotation.DrawableRes
import com.bumptech.glide.load.MultiTransformation
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions

object GlideConfig {

    var defaultImg = android.R.drawable.stat_notify_error

    /**
     * 缓存配置
     */
    fun CacheOptions(@DrawableRes resourceId: Int = defaultImg): RequestOptions {
        return RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            .error(resourceId)
    }

    fun CacheOptions(): RequestOptions {
        return CacheOptions(defaultImg)
    }


    /**
     * 加载圆形图片配置
     */
    fun CircleCropOptions(@DrawableRes resourceId: Int = defaultImg): RequestOptions {
        return RequestOptions()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .circleCrop()
            .error(resourceId)
    }

    fun CircleCropOptions(): RequestOptions {
        return CircleCropOptions(defaultImg)
    }
}