package com.example.coderlt.uibestpractice.interfaces;

import android.content.Intent;
import android.net.Uri;
import android.view.View;

import com.example.coderlt.uibestpractice.MyApplication;
import com.example.coderlt.uibestpractice.utils.Utils;

/**
 * Created by coderlt on 2018/3/29.
 */

public class MyOnClickListener implements View.OnClickListener {

    private static MyOnClickListener instance = null;

    private MyOnClickListener() {
    }

    public static MyOnClickListener getInstance() {
        if (instance == null)
            instance = new MyOnClickListener() ;
        return instance;
    }

    @Override
    public void onClick(View view) {
        //TODO: do something here
        Utils.showToast((String)view.getTag());
        // 不适用 ACTION_CALL直接拨打电话，避免了运行时权限的申请也能防止用户不小心操作
        Intent dailIntent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+(String)view.getTag()));
        dailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MyApplication.getContext().startActivity(dailIntent);
    }
}
