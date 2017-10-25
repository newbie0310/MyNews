package com.example.myapplication.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.myapplication.R;

import java.lang.reflect.Field;

/**
 * Created by john1 on 2017/10/22.
 */

public class BaseActivity extends FragmentActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            //透明状态栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            //透明导航栏
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
    }
    protected void openActivity(Class<?> pClass){
        Intent mIntent=new Intent(this,pClass);
        this.startActivity(mIntent);
    }
    protected void setStatusBar(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            final ViewGroup linear_bar = findViewById(R.id.bar_layout);
            final int statusHeight = getStatusBarHeight();
            linear_bar.post(new Runnable() {
                @Override
                public void run() {
                    int titleHeight = linear_bar.getHeight();
                    android.widget.RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) linear_bar.getLayoutParams();
                    layoutParams.height = statusHeight + titleHeight;
                    linear_bar.setLayoutParams(layoutParams);
                }
            });
        }
    }
    //动态计算状态栏高度
    private int getStatusBarHeight(){
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object obj = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = Integer.parseInt(field.get(obj).toString());
            return getResources().getDimensionPixelSize(x);
        }catch (Exception e){
            e.printStackTrace();
        }

        return 0;
    }
}
