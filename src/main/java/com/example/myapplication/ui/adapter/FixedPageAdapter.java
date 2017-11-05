package com.example.myapplication.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.example.myapplication.ui.fragment.base.BaseFragment;

import java.util.List;

/**
 * Created by john1 on 2017/11/4.
 */

public class FixedPageAdapter extends FragmentStatePagerAdapter {
    private List<BaseFragment> fragments;
    private String[] titles;

    public void setFragments(List<BaseFragment> fragments){
        this.fragments = fragments;
    }

    public void setTitle(String[] titles) {
        this.titles = titles;
    }

    public FixedPageAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) super.instantiateItem(container,position);
        }catch (Exception e){
            e.printStackTrace();
        }
        return fragment;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }
}
