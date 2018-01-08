package com.example.coderlt.uibestpractice.View;

import android.content.Context;
import android.graphics.Canvas;
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
 * Created by coderlt on 2018/1/7.
 */

public class BottomNavigationView extends LinearLayout {

    private final String TAG=getClass().getName();
    private List<MyImgButton> btnList;
    private static int selectedBtnPosition=-1;

    private OnBtnSelectedListenr mListener=null;

    public BottomNavigationView(Context context, List<MyImgButton> btnList){
        super(context);
        this.btnList=btnList;
    }

    public BottomNavigationView(Context context, AttributeSet attrs){
        super(context,attrs);
        btnList=new ArrayList<>();
    }

    public void setBtnList(List<MyImgButton> btnList){
        //setBackgroundResource(R.color.colorAccent);
        this.btnList=btnList;
        // TODO 为什么设置了 Orientation 就显示不出来呢
        //setOrientation(HORIZONTAL);
        MyImgButton btn;

        LayoutParams layoutParams=new LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT,1.0f);

        for(int i=0;i<btnList.size();i++){
            btn=btnList.get(i);
            final int temp=i;
            btn.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v){
                    if(mListener!=null)
                    mListener.onBtnSelected(temp);
                }
            });
            addView(btnList.get(i),layoutParams);
        }
    }
    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);
        Log.d(TAG,"draw myTab.");
    }

    public void setCurrentItem(int position){
        if(selectedBtnPosition!=-1)
        btnList.get(selectedBtnPosition).setUnSelected();
        btnList.get(position).setSelected();
        selectedBtnPosition=position;
    }

    public interface OnBtnSelectedListenr{
        void onBtnSelected(int position);
    }

    public void setOnBtnSelectedListener(OnBtnSelectedListenr listener){
        mListener=listener;
    }
}
