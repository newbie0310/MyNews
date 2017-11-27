package com.example.myapplication.application;

import android.app.Application;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * Created by zaXie on 2017/11/23.
 * 初始化全局变量
 */

public class MyApplication extends Application{

    private static MyApplication instance=null;

    @Override
    public void onCreate() {
        super.onCreate();
        this.instance=this;
        initImageLoader();
    }

    public static MyApplication getInstance(){
        return instance;
    }

    private void initImageLoader(){
        ImageLoaderConfiguration configuration=ImageLoaderConfiguration.createDefault(this);
        ImageLoader.getInstance().init(configuration);
    }
}
