package com.example.coderlt.uibestpractice.View;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by coderlt on 2018/2/9.
 */

public class SquareImage extends ImageView {

    public SquareImage(Context context){
        super(context);
    }

    public SquareImage(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec,int heightMeasureSpec){
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        setMeasuredDimension(getMeasuredWidth(),getMeasuredWidth());
    }
}
