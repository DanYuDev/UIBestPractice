package com.example.coderlt.uibestpractice.utils;

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
}
