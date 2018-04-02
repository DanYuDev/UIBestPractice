package com.example.coderlt.uibestpractice.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.coderlt.uibestpractice.R;
import com.kcode.autoscrollviewpager.view.AutoScrollViewPager;


/**
 * Created by coderlt on 2018/4/1.
 */

public class MyAutoViewPager extends AutoScrollViewPager {
    public MyAutoViewPager(Context context){
        super(context,null);
    }

    public MyAutoViewPager(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    public void loadImage(ImageView view, int position, String url){
        Glide.with(getContext())
                .load(url)
                .placeholder(R.drawable.place_holder)
                .into(view);
    }
}
