package com.example.myapplication.ui;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.ui.adapter.LeftItemAdapter;
import com.example.myapplication.common.DefineView;
import com.example.myapplication.entity.AdHeadBean;
import com.example.myapplication.manager.HeadDataManager;
import com.example.myapplication.ui.activity.BaseActivity;
import com.example.myapplication.ui.widget.DragLayout;
import com.google.gson.Gson;
import com.nineoldandroids.view.ViewHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;


public class MainActivity extends BaseActivity implements DefineView {

    private DragLayout drag_Layout;
    private ImageView top_bar_icon;
    private ListView lv_left_main;

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

    public DragLayout getDrag_Layout() {
        return drag_Layout;
    }

    @Override
    public void initView(){
        drag_Layout = findViewById(R.id.drag_Layout);
        top_bar_icon = findViewById(R.id.top_bar_icon);
        lv_left_main = findViewById(R.id.lv_left_main);
    }

    @Override
    public void initValidata() {
        lv_left_main.setAdapter(new LeftItemAdapter(this));
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

