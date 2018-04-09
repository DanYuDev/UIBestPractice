package com.example.coderlt.uibestpractice.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;

import com.example.coderlt.uibestpractice.MyApplication;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * Created by coderlt on 2018/1/6.
 */

public class OkHttpUtils {
    public static final int DEFAULT_TIMEOUT = 3000;
    public OkHttpClient getClient(){
        Cache cache = new Cache(FileUtil.getDiskCacheDir(MyApplication.getContext(),"OkHttpCache"),1024*1024);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.MILLISECONDS)
                .cache(cache)
                .addInterceptor(new HttpHeaderInterceptor())
                .build();
        return okHttpClient;
    }
}

class HttpHeaderInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        //  将token统一放到请求头
        SharedPreferences pref = MyApplication.getContext().getSharedPreferences(Constant.USER_PREF_NAME, Context.MODE_PRIVATE);
        String token= (String) pref.getString(Constant.LOGIN_STATUS.APP_TOKEN,"");
        //  也可以统一配置用户名
        Response originalResponse = chain.proceed(chain.request());
        return originalResponse.newBuilder()
                .header("token", token)
                .build();
    }
}
