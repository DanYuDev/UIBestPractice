package com.example.coderlt.uibestpractice.utils;

import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * Created by coderlt on 2018/4/4.
 */

public class DrawableUtils {
    public static void setRightDrawableBounds(TextView tv, int width, int height){
        Drawable rightDrawable= tv.getCompoundDrawables()[2];
        rightDrawable.setBounds(0, 0, width,
                height);
        tv.setCompoundDrawables(null,null,rightDrawable,null);
    }
}
