package com.example.coderlt.uibestpractice.utils;

import android.content.Context;
import android.widget.Toast;

import com.example.coderlt.uibestpractice.MyApplication;

/**
 * Created by coderlt on 2018/1/6.
 */

public class Utils {
    public static void showToast(String str){
        Toast.makeText(MyApplication.getContext(),str,Toast.LENGTH_SHORT)
                .show();
    }

    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }
}
