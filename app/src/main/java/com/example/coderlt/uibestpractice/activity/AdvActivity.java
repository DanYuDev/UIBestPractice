package com.example.coderlt.uibestpractice.activity;

import android.app.Notification;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Environment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.coderlt.uibestpractice.MyApplication;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.View.MyDialog;
import com.example.coderlt.uibestpractice.bean.AppInfo;
import com.example.coderlt.uibestpractice.service.DownloadListener;
import com.example.coderlt.uibestpractice.service.DownloadTask;
import com.example.coderlt.uibestpractice.utils.Constant;
import com.example.coderlt.uibestpractice.utils.JsonUtils;

import java.io.File;
import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class AdvActivity extends AppCompatActivity {
    private static final String TAG = "AdvActivity";
    private SharedPreferences preferences;
    private DownloadTask downloadTask;
    private MyDialog mDialog;
    private TextView progressTv;
    private NotificationManager notificationManager;
    private Notification notification;
    private NotificationCompat.Builder builder;

    Runnable loginRunnable=new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(500);
            }catch (InterruptedException ex){
                ex.printStackTrace();
            }
            startActivity(new Intent(AdvActivity.this,LoginActivity.class));
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adv);
        notificationManager = (NotificationManager)getSystemService(NOTIFICATION_SERVICE);
        checkUpdate();
        startActivity(new Intent(AdvActivity.this,LoginActivity.class));

//      TODO 暂时注释是为了测试
//        if(preferences.getString(Constant.USER.USER_PHONE,"-1").equals("-1")){
//            //startActivity(new Intent(this,NavigationActivity.class));
//            new Thread(loginRunnable).start();
//        }else{
//            startActivity(new Intent(this,NavigationActivity.class));
//        }

        //new Thread(loginRunnable).start();
        //overridePendingTransition(R.anim.anim_enter_2,R.anim.anim_exit_2);
    }

    private void checkUpdate(){
        mDialog = new MyDialog(this, R.style.Dialog);
        View dialogLayout = LayoutInflater.from(this).inflate(R.layout.dialog_download,null);
        progressTv = dialogLayout.findViewById(R.id.download_tv);
        //progressTv.setTextColor(getResources().getColor(android.R.color.white));
        mDialog.setMyView(dialogLayout);
        mDialog.show();

        builder = new NotificationCompat.Builder(this)
                .setContentTitle("杭派管理更新程序")
                .setContentText("This is content text")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_logo)
                .setVibrate(new long[]{0,1000,1000,1000})
                .setProgress(100,0,false)
                .setLights(Color.GREEN,1000,1000);

        PackageManager pm = getPackageManager();
        PackageInfo pi=null;
        try{
            pi = pm.getPackageInfo(getPackageName(),0);
        }catch (PackageManager.NameNotFoundException ex){
            ex.printStackTrace();
        }
        String version = pi.versionName;
        final int versionCode = pi.versionCode;

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("http://192.168.125.81:8080/HangPaiSoftCamp/apk/select")
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"request failed.");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText = response.body().string();
                AppInfo appInfo = new AppInfo();
                JsonUtils.dealAppInfo(appInfo,responseText);
                Log.d(TAG,appInfo.toString());
                if(appInfo.getAppVersion()>versionCode){
                    downloadApk(appInfo.getApkUrl());
                }else{
                    startActivity(new Intent(AdvActivity.this,LoginActivity.class));
                }
                //downloadApk("http://www.sinaimg.cn/dy/slidenews/31_img/2016_44/28379_744352_143925.jpg");
            }
        });
    }

    private void downloadApk(final String apkUrl){
        Log.d(TAG,"download apk.");
        downloadTask = new DownloadTask(new DownloadListener() {
            @Override
            public void onSuccess() {
                Log.d(TAG,"success");
                notificationManager.cancel(1);
                String directory = Environment.getExternalStoragePublicDirectory(
                        (Environment.DIRECTORY_DOWNLOADS)).getPath();
                String fileName = apkUrl.substring(apkUrl.lastIndexOf('/'));
                File file = new File(directory+fileName);
                installApk(file);
            }

            @Override
            public void onCanceled() {

            }

            @Override
            public void onPaused() {

            }

            @Override
            public void onFailed() {
                Log.d(TAG,"failed");
            }

            @Override
            public void onProgress(int progress) {
                progressTv.setText("download..."+progress+"%");
                builder.setProgress(100,progress,false);
                notificationManager.notify(1,builder.build());
            }
        }) ;
        downloadTask.execute(apkUrl);
    }

    private void installApk(File file){
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
