package com.example.myapplication.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.myapplication.R;
import com.example.myapplication.common.DefineView;
import com.example.myapplication.ui.fragment.base.BaseFragment;

/**
 * Created by john1 on 2017/11/4.
 */

public class PageFragment extends BaseFragment implements DefineView{
    private View mView;
    private static final String KEY = "EXTRA";
    private String message;
    private TextView tv_page;

    public static PageFragment newsInstance(String extra){
        Bundle bundle = new Bundle();
        bundle.putString(KEY,extra);
        PageFragment pageFragment = new PageFragment();
        pageFragment.setArguments(bundle);
        return pageFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null){
            message = bundle.getString(KEY);
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
        if (message != null){
            tv_page.setText(message);
        }
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
