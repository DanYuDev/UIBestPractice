package com.example.coderlt.uibestpractice.View;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.coderlt.uibestpractice.R;

/**
 * Created by coderlt on 2017/12/30.
 */

public class MyDialog extends Dialog{
    private static final String  TAG = "MyDialog";
    private Context mContext;
    int     resId;
    public MyDialog(Context context) {
        super(context);
        mContext=context;
    }

    public MyDialog(Context context, int themeResId) {
        super(context, themeResId);
        mContext=context;

    }

    protected MyDialog(Context context, boolean cancelable
            , DialogInterface.OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        mContext=context;

    }

    public void setLayoutResId(int resId){
        Log.d(TAG,"setLayoutResId() start");
        this.resId=resId;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG,"onCreate .");
        super.onCreate(savedInstanceState);
        View view = View.inflate(mContext, resId,null);
        setContentView(view);
    }
}
