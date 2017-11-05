package com.example.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.myapplication.R;
import com.example.myapplication.common.DefineView;
import com.example.myapplication.ui.MainActivity;
import com.example.myapplication.ui.adapter.FixedPageAdapter;
import com.example.myapplication.ui.fragment.base.BaseFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by john1 on 2017/11/2.
 */

public class MainInfoFragment extends BaseFragment implements DefineView ,ViewPager.OnPageChangeListener{
    private View mView;
    private TabLayout tab_layout;
    private ViewPager info_viewpager;
    private FixedPageAdapter fixedPageAdapter;
    private List<BaseFragment> fragments;
    private String[] titles = new String[]{"业界","人工智能","智能驾驶","AI+","Fintech","未来医疗","网络安全","AR/VR","机器人","开发者","智能硬件","物联网","GAIR"};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        if (mView == null){
            mView=inflater.inflate(R.layout.main_info_fragment_layout,container,false);
            initView();
            initValidata();
            initListener();
            bindData();
        }
        return mView;
    }

    @Override
    public void initView() {
        tab_layout = mView.findViewById(R.id.tab_layout);
        info_viewpager = mView.findViewById(R.id.info_viewpager);
    }

    @Override
    public void initValidata() {
        fixedPageAdapter=new FixedPageAdapter(getChildFragmentManager());
        fixedPageAdapter.setTitle(titles);
        fragments=new ArrayList<BaseFragment>();
        for(int i=0;i<titles.length;i++){
            fragments.add(PageFragment.newsInstance(titles[i]));
        }
        fixedPageAdapter.setFragments(fragments);

        tab_layout.setTabMode(TabLayout.MODE_SCROLLABLE);
        info_viewpager.setAdapter(fixedPageAdapter);
        tab_layout.setupWithViewPager(info_viewpager);
    }

    @Override
    public void initListener() {
        info_viewpager.setOnPageChangeListener(this);
    }

    @Override
    public void bindData() {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        if (position == 0){
            ((MainActivity)getActivity()).getDrag_Layout().setDrage(true);
        }else {
            ((MainActivity)getActivity()).getDrag_Layout().setDrage(false);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
