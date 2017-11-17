package com.example.myapplication.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.common.Config;
import com.example.myapplication.common.DefineView;
import com.example.myapplication.entity.CategoriesBean;
import com.example.myapplication.manager.CategoryDataManager;
import com.example.myapplication.ui.fragment.base.BaseFragment;
import com.example.myapplication.util.OkHttpManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

/**
 * Created by john1 on 2017/11/4.
 */

public class PageFragment extends BaseFragment implements DefineView{
    private View mView;
    private static final String KEY = "EXTRA";
    private CategoriesBean categoriesBean;
    private TextView tv_page;

    public static PageFragment newsInstance(CategoriesBean extra){
        Bundle bundle = new Bundle();
        bundle.putSerializable(KEY,extra);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(bundle);
        return pageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            categoriesBean = (CategoriesBean) bundle.getSerializable(KEY);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mView == null){
            mView = inflater.inflate(R.layout.page_fragment_layout,container,false);
            initView();
            initValidata();
            initListener();
            bindData();
        }
        return mView;
    }

    @Override
    public void initView() {
        tv_page = mView.findViewById(R.id.tv_page);
            tv_page.setText(categoriesBean.getTitle());
    }

    @Override
    public void initValidata() {

    }

    @Override
    public void initListener() {

    }

    @Override
    public void bindData() {

    }
}
