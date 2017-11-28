package com.example.myapplication.ui.fragment;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.myapplication.R;
import com.example.myapplication.common.Config;
import com.example.myapplication.common.DefineView;
import com.example.myapplication.entity.CategoriesBean;
import com.example.myapplication.entity.HomeNewsBean;
import com.example.myapplication.manager.CategoryDataManager;
import com.example.myapplication.manager.HomeNewsDataManager;
import com.example.myapplication.ui.adapter.HomeRecyclerAdapter;
import com.example.myapplication.ui.fragment.base.BaseFragment;
import com.example.myapplication.util.OkHttpManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import okhttp3.Request;

/**
 * 分类页面列表
 */

public class PageFragment extends BaseFragment implements DefineView{
    private View mView;
    private static final String KEY = "EXTRA";
    private CategoriesBean categoriesBean;
    private RecyclerView home_recyclerView;
    private FrameLayout home_fragment;
    private LinearLayout loading, error, empty;

    //recyclerview布局管理器
    private LinearLayoutManager layoutManager;
    //新闻列表的数据
    private List<HomeNewsBean> homeNewsBeans;
    //recyclerView适配器
    HomeRecyclerAdapter adapter;

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
        loading = mView.findViewById(R.id.loading);
        error = mView.findViewById(R.id.error);
        empty = mView.findViewById(R.id.empty);
//        home_fragment = mView.findViewById(R.id.home_fragment);
        home_recyclerView = mView.findViewById(R.id.page_recyclerview);
    }

    @Override
    public void initValidata() {
        home_recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        home_recyclerView.setLayoutManager(layoutManager);
        adapter = new HomeRecyclerAdapter(getActivity());
        //设置分割线
        //设置动画
        //获取数据
        OkHttpManager.getAsync(categoriesBean.getHref(), new OkHttpManager.DataCallBack() {
            @Override
            public void requestFailue(Request request, IOException e) {
                Toast.makeText(getActivity(),"requestFailue...",Toast.LENGTH_SHORT).show();
                Log.d("PageFragment","数据加载失败...");
            }

            @Override
            public void requestSucess(String result) {
                Log.d("PageFragmentSync","数据加载成功");
                Document document = Jsoup.parse(result, Config.CRAWLER_URL);
                homeNewsBeans = new HomeNewsDataManager().getHomeNews(document);
                bindData();
            }
        });
    }

    @Override
    public void initListener() {

    }

    //绑定数据
    @Override
    public void bindData() {
        adapter.setHomeNewsBeans(homeNewsBeans);
        home_recyclerView.setAdapter(adapter);
    }
}
