package com.example.coderlt.uibestpractice;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.example.coderlt.uibestpractice.bean.User;
import com.example.coderlt.uibestpractice.utils.Constant;
import com.example.coderlt.uibestpractice.utils.Utils;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;

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
    private String appId = "mlCcRI3LmLzGsL5KDfGufDrY-gzGzoHsz";
    private String appKey = "FB8SDGNEBNlniehHC0eeBCes";

    @Override
    public void onCreate(){
        super.onCreate();
        mContext=getApplicationContext();
        sharedPreferences= getSharedPreferences(Constant.USER_PREF_NAME,MODE_PRIVATE);
        // 初始化参数依次为 this, AppId, AppKey
        AVOSCloud.initialize(this, appId, appKey);
        AVOSCloud.setDebugLogEnabled(true);
        LCChatKit lcChatKit = LCChatKit.getInstance();
        lcChatKit.init(mContext,appId,appKey);
        lcChatKit.setProfileProvider(CustomUserProvider.getInstance());
        // Tom 登陆了，并且执行了一些回调操作
        LCChatKit.getInstance().open("Tom", new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (null == e) {
                    Utils.showToast("Tom login the IM .");
                } else {
                    Utils.showToast(e.toString());
                }
            }
        });
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
