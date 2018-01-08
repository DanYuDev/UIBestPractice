package com.example.coderlt.uibestpractice.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.coderlt.uibestpractice.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coderlt on 2018/1/2.
 */

public class MyTab extends LinearLayout {
    public int mWidth,mHeight;
    private final String TAG=getClass().getName();
    private List<String> titleList;
    private List<View> tabList=new ArrayList<>();
    private static int selectedTabPosition=-1;
    private int selectedBg;

    private OnTabSelectedListenr mListener=null;

    public MyTab(Context context,List<String> titleList){
        super(context);
        this.titleList=titleList;
    }

    public MyTab(Context context, AttributeSet attrs){
        super(context,attrs);
        titleList=new ArrayList<>();
        selectedBg=R.color.background_gray;
    }

    public void setTitleList(List<String> titleList){
        //setBackgroundResource(R.color.colorAccent);
        this.titleList=titleList;
        // TODO 为什么设置了 Orientation 就显示不出来呢
        //setOrientation(HORIZONTAL);
        for(int i=0;i<titleList.size();i++){
            Log.d(TAG,"add a tab: "+titleList.get(i));
            TextView tv=new TextView(getContext());
            LayoutParams layoutParams=new LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT,1.0f);
            tv.setText(titleList.get(i));
            tv.setTextColor(getResources().getColor(android.R.color.white));
            tv.setGravity(Gravity.CENTER);
            final int temp=i;
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener!=null)
                        mListener.onTabSelected(temp);
                }
            });
            addView(tv,layoutParams);
            tabList.add(tv);
        }
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Log.d(TAG,"draw myTab.");
    }

    public void selectTab(int position){
        if(selectedTabPosition!=-1)
        tabList.get(selectedTabPosition).setBackgroundResource(R.color.main_blue);
        tabList.get(position).setBackgroundResource(selectedBg);
        selectedTabPosition=position;
    }

    public interface OnTabSelectedListenr{
        void onTabSelected(int position);
    }

    public void setOnTabSelectedListener(OnTabSelectedListenr listener){
        mListener=listener;
    }

    public void setSelectColor(int colorId){
        selectedBg=colorId;
    }
}
