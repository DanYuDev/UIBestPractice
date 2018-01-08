package com.example.coderlt.uibestpractice.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by coderlt on 2018/1/1.
 */

public class MyPagerAdapter extends PagerAdapter {
    private List<View> viewList;
    private List<String> titleList;


    public MyPagerAdapter(List<View> list){
        viewList=list;
    }

    /*
    public MyPagerAdapter(List<View> viewlist,List<String> titleList){
        this.viewList=viewlist;
        this.titleList=titleList;
    }*/
    @Override
    public boolean isViewFromObject(View arg0, Object arg1) {
        // TODO Auto-generated method stub
        return arg0 == arg1;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return viewList.size();
    }

    @Override
    public void destroyItem(ViewGroup container, int position,
                            Object object) {
        // TODO Auto-generated method stub
        container.removeView(viewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        // TODO Auto-generated method stub
        container.addView(viewList.get(position));
        return viewList.get(position);
    }

}
