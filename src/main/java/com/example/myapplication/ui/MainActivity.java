package com.example.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.myapplication.R;
import com.example.myapplication.common.DefineView;
import com.example.myapplication.ui.activity.BaseActivity;
import com.example.myapplication.ui.widget.DragLayout;
import com.nineoldandroids.view.ViewHelper;


public class MainActivity extends BaseActivity implements DefineView {

    private DragLayout drag_Layout;
    private ImageView top_bar_icon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setStatusBar();
        initView();
        initValidata();
        initListener();
        bindData();

    }

    @Override
    public void initView(){
        drag_Layout = findViewById(R.id.drag_Layout);
        top_bar_icon = findViewById(R.id.top_bar_icon);
    }

    @Override
    public void initValidata() {

    }
    @Override
    public void initListener(){
        drag_Layout.setDragListener(new CustomDragListener());
        top_bar_icon.setOnClickListener(new CustomOnClickListener());
    }

    @Override
    public void bindData() {

    }

    class CustomDragListener implements DragLayout.DragListener{

        //界面打开的时候
        @Override
        public void onOpen() {

        }
        //界面关闭的时候
        @Override
        public void onClose() {

        }
        //界面滑动的时候
        @Override
        public void onDrag(float percent) {
            ViewHelper.setAlpha(top_bar_icon,1-percent);
        }

    }
    class CustomOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            drag_Layout.open();
        }
    }
}

