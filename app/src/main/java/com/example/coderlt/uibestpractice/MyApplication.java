package com.example.coderlt.uibestpractice;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.coderlt.uibestpractice.bean.User;
import com.example.coderlt.uibestpractice.utils.Constant;

/**
 * Created by coderlt on 2018/1/6.
 */

/**
 * 通过sp来保存用户的登陆状态，即是否需要登陆
 * 通过 user 来保存用户的登陆详细信息
 */

public class MyApplication extends Application {
    private static Context mContext;
    private static SharedPreferences sharedPreferences;
    private static User user;

    @Override
    public void onCreate(){
        super.onCreate();
        mContext=getApplicationContext();
        sharedPreferences= getSharedPreferences(Constant.USER_PREF_NAME,MODE_PRIVATE);
    }

    public static Context getContext(){
        return mContext;
    }

    public static SharedPreferences getMySp(){
        return sharedPreferences;
    }

    public static User getUser(){
        return user;
    }
}
