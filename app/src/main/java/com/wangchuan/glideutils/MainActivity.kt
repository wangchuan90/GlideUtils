package com.wangchuan.glideutils

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GlideUtils.Instance().init(this)
        GlideUtils.Instance().loadImg(R.mipmap.ic_launcher, img_main)
    }
}