package com.example.coderlt.uibestpractice.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by coderlt on 2018/1/3.
 */

public class MyFragmentPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments;

    public MyFragmentPagerAdapter(FragmentManager fm,List<Fragment> fragments){
        super(fm);
        this.fragments=fragments;
    }

    @Override
    public Fragment getItem(int position){
        return fragments.get(position);
    }

    @Override
    public int getCount(){
        return fragments.size();
    }

    // 选择性实现
    @Override
    public CharSequence getPageTitle(int position){
       return fragments.get(position).getClass().getSimpleName();
    }

}