package com.example.coderlt.uibestpractice.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.example.coderlt.uibestpractice.utils.Constant;

/**
 * Created by coderlt on 2018/4/4.
 */

public class SquareReativelayout extends RelativeLayout {

    public SquareReativelayout(Context context){
        super(context);
    }

    public SquareReativelayout(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredWidth());
    }
}
