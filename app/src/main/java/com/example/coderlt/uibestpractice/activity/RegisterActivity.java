package com.example.coderlt.uibestpractice.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSONObject;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.utils.Constant;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private final String TAG=getClass().getName();

    // Okhttp
    private OkHttpClient client;
    private String responseString;

    // views
    private TextView phoneTv,passwordTv,nicknameTv,addressTv;
    private Button regBtn;

    //reg status
    private static final int REG_FAILED=0;
    private static final int REG_DISCONNET=1;
    private static final int REG_SUCCESS=2;

    // data store
    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;

    //handler
    private static class MyHandler extends Handler{
        private WeakReference<RegisterActivity> wrRegAct;
        private Context context;
        public MyHandler(RegisterActivity registerActivity){
           wrRegAct=new WeakReference<RegisterActivity>(registerActivity);
        }

        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case REG_FAILED:
                    Utils.showToast("错误的用户名或密码");
                    break;
                case REG_SUCCESS:
                    break;
                case REG_DISCONNET:
                    Utils.showToast("无法连接到服务器，请检查网络状态！");
                    break;
                default:
                    break;
            }
        }
    }

    private MyHandler mHandler=new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.main_blue));
                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
                //window.setBackgroundDrawableResource(R.drawable.shadow_gradient);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_register);

        initViews();

        preferences=getSharedPreferences(Constant.USER_PREF_NAME,MODE_PRIVATE);
        editor=preferences.edit();
    }

    public void initViews(){
        phoneTv=findViewById(R.id.phone_tv);
        Log.d(TAG,"phoneTv id is :"+R.id.phone_tv);
        passwordTv=findViewById(R.id.passwd_tv);
        nicknameTv=findViewById(R.id.nickname_tv);
        addressTv=findViewById(R.id.address_tv);
        regBtn=findViewById(R.id.reg_final);

        regBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.reg_final:
                regBoss();
                break;
        }
    }

    private void regBoss(){
        final String phone;
        String password;
        final String nickname;
        final String address;

        phone=phoneTv.getText().toString();
        password=passwordTv.getText().toString();
        nickname=nicknameTv.getText().toString();
        address=addressTv.getText().toString();

        client=new OkHttpClient.Builder()
                .connectTimeout(3000, TimeUnit.MILLISECONDS)
                .build();
        Map<String,Object> map=new HashMap<>();
        map.put("merchant_name",nickname);
        map.put("merchant_phone",phone);
        map.put("merchant_address",address);
        map.put("merchant_password",password);

        final String requestBody= JSONObject.toJSONString(map);

        Request request=new Request.Builder()
                .url(Constant.REG_URL)
                .post(RequestBody.create(Constant.MEDIA_TYPE_JSON,requestBody))
                .build();
        //client=new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg=new Message();
                msg.what=REG_DISCONNET;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message msg=new Message();
                responseString=response.body().string();
                if("success".equals(responseString.trim())){
                    editor.putString(Constant.USER.USER_NAME,nickname)
                            .putString(Constant.USER.USER_PHONE,phone)
                            .putString(Constant.USER.USER_ADDRESS,address)
                            .apply();
                    msg.what=REG_SUCCESS;
                    mHandler.sendMessage(msg);
                }
                else{
                    msg.what=REG_FAILED;
                    mHandler.sendMessage(msg);
                }
            }
        });
    }
}
