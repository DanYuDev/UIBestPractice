package com.example.coderlt.uibestpractice;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.multidex.MultiDexApplication;
import android.widget.Toast;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.PushService;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.example.coderlt.uibestpractice.activity.NavigationActivity;
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

public class MyApplication extends MultiDexApplication {
    private static Context mContext;
    private static SharedPreferences sharedPreferences;
    private static User user;
    private final String APP_ID = "mlCcRI3LmLzGsL5KDfGufDrY-gzGzoHsz";
    private final String APP_KEY = "FB8SDGNEBNlniehHC0eeBCes";
    public static String clientId;

    @Override
    public void onCreate(){
        super.onCreate();
        mContext=getApplicationContext();
        sharedPreferences= getSharedPreferences(Constant.USER_PREF_NAME,MODE_PRIVATE);
        /**
         * 初始化参数依次为 this, AppId, AppKey
         * 开启调试日志
         */
        LCChatKit.getInstance().setProfileProvider(CustomUserProvider.getInstance());
        AVOSCloud.setDebugLogEnabled(true);
        // 这句好像已经覆盖了 AVOSCloud.init....
        LCChatKit.getInstance().init(getApplicationContext(), APP_ID, APP_KEY);
        AVIMClient.setAutoOpen(true);
        PushService.setDefaultPushCallback(this, NavigationActivity.class);
        PushService.setAutoWakeUp(true);
        PushService.setDefaultChannelId(this, "default");

        AVInstallation.getCurrentInstallation().saveInBackground(new SaveCallback() {
            public void done(AVException e) {
                if (e == null) {
                    // 保存成功
                    String installationId = AVInstallation.getCurrentInstallation().getInstallationId();
                    System.out.println("---  " + installationId);
                } else {
                    // 保存失败，输出错误信息
                    System.out.println("failed to save installation.");
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
