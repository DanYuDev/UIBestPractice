package com.example.coderlt.uibestpractice.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.example.coderlt.uibestpractice.MyApplication;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.utils.BlurBitmap;
import com.example.coderlt.uibestpractice.utils.Constant;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.internal.Util;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private RelativeLayout loginLayout;
    private Button  loginButton;
    private Button  regButton;
    private RadioButton verificationRadio,passwordRadio;
    private RadioGroup radioGroup;
    private LinearLayout loginContentLayout;
    private LinearLayout verificationLayout,passwordLayout;
    private int loginPattern;
    private OkHttpClient client;
    private SharedPreferences preferences;
    private String responseText;

    private final String TAG=getClass().getName();
    private static final int LOGIN_FAILED=0;
    private static final int LOGIN_SUCCESS=1;
    private static final int PASSWORD_ERROR=3;
    private static final int CONNECTION_ERROR=4;


/*  I wrote that debugging code because of a couple of memory leaks I
    found in the Android codebase. Like you said, a Message has a
    reference to the Handler which, when it's inner and non-static, has a
    reference to the outer this (an Activity for instance.) If the Message
    lives in the queue for a long time, which happens fairly easily when
    posting a delayed message for instance, you keep a reference to the
    Activity and "leak" all the views and resources. It gets even worse
    when you obtain a Message and don't post it right away but keep it
    somewhere (for instance in a static structure) for later use. */

// 代码优化，防止内存泄漏
    private static class MyHandler extends Handler{
        private WeakReference<LoginActivity> wr;
        private Context context;

        public MyHandler(LoginActivity activity){
            wr=new WeakReference<LoginActivity>(activity);
            context=wr.get();  // TODO 貌似有问题，又变成一个强引用了
        }

        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case LOGIN_FAILED:
                    if(msg.arg1==CONNECTION_ERROR)
                        Utils.showToast("无法连接到服务器");
                    else
                        Utils.showToast("用户名或密码错误");
                    break;
                case LOGIN_SUCCESS:
                    Toast.makeText(context,"Login Success",Toast.LENGTH_SHORT)
                            .show();
                    //((LoginActivity)context).editSharedpreference();
                    context.startActivity(new Intent(context,NavigationActivity.class));
                    ((LoginActivity)context).overridePendingTransition(R.anim.anim_enter_2,
                            R.anim.anim_exit_2);
                    break;
                default:
                    break;

            }
        }
    }

    private MyHandler mHandler=new MyHandler(LoginActivity.this);

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

        setContentView(R.layout.activity_login);

        // 设置状态栏颜色
        setBg();

        // 初始化控件，绑定事件
        initViews();
    }

    public void setBg(){
        loginLayout=findViewById(R.id.login_layout);
        Bitmap bitmap= BitmapFactory.decodeResource(getResources(),R.drawable.login_bg);
        //Bitmap srcBitmap=Bitmap.createScaledBitmap(bitmap,300,600,true);
        Bitmap blurResult= BlurBitmap.blur(this,bitmap);
        loginLayout.setBackground(new BitmapDrawable(blurResult));
    }

    public void initViews(){
        loginButton=findViewById(R.id.login_btn);
        regButton=findViewById(R.id.reg_btn);
        radioGroup=findViewById(R.id.login_group);
        verificationRadio=findViewById(R.id.verification_login_radio);
        passwordRadio=findViewById(R.id.password_login_radio);
        loginContentLayout=findViewById(R.id.login_content_layout);
        ImageView loginWechat=findViewById(R.id.login_wechat);

        loginButton.setOnClickListener(this);
        regButton.setOnClickListener(this);
        loginWechat.setOnClickListener(this);

        // 放在外面加载，这样的话不会重复加载布局，影响速度
        verificationLayout=(LinearLayout) LayoutInflater.from(LoginActivity.this)
                .inflate(R.layout.verification_login_layout,loginContentLayout,
                        false);
        passwordLayout=(LinearLayout)LayoutInflater.from(LoginActivity.this)
                .inflate(R.layout.password_login_layout,loginContentLayout,false);

        // radioGroup管理两种不同的登陆方式
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                client=new OkHttpClient.Builder()
                        .connectTimeout(4000, TimeUnit.MILLISECONDS)
                        .build();
                switch (checkedId){
                    case R.id.verification_login_radio:
                        loginContentLayout.removeAllViews();
                        loginContentLayout.addView(verificationLayout);
                        loginPattern= Constant.LOGIN_BY_VERIFICATION;
                        break;
                    case R.id.password_login_radio:
                        loginContentLayout.removeAllViews();
                        loginContentLayout.addView(passwordLayout);
                        loginPattern=Constant.LOGIN_BY_PASSWORD;
                        break;
                    default:
                        break;
                }
            }
        });
        radioGroup.check(R.id.password_login_radio);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.login_btn:
                if(loginPattern==Constant.LOGIN_BY_VERIFICATION)
                    loginByVerification();
                else
                    loginByPassword();
                break;
            case R.id.reg_btn:
                startActivity(new Intent(this,RegisterActivity.class));
                overridePendingTransition(R.anim.anim_enter_1,R.anim.anim_exit_1);
                break;
            case R.id.login_wechat:
                final EditText wechatTv = new EditText(this);
                wechatTv.setHint("请输入wechat_id");
                AlertDialog alertDialog =new  AlertDialog.Builder(this)
                        .setView(wechatTv)
                        .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(LoginActivity.this,NavigationActivity.class);
                                String wechatId = wechatTv.getText().toString().trim();
                                MyApplication.clientId = wechatId;
                                intent.putExtra(Constant.USER.USER_ID,wechatId);
                                Log.d(TAG,"WeChat_ID: "+wechatId);
                                startActivity(intent);
                            }
                        })
                        .show();
                overridePendingTransition(R.anim.anim_enter_1,R.anim.anim_exit_1);
                break;
            default:
                break;
        }
    }

    private void loginByVerification(){
        Utils.showToast("暂不支持验证码登陆");
    }

    private void loginByPassword(){
        TextView passwordTv=passwordLayout.findViewById(R.id.password_tv);
        TextView accountTv=passwordLayout.findViewById(R.id.account_tv);
        String account=accountTv.getText().toString();
        String password=passwordTv.getText().toString();

        // 组织POST 参数
        Map<String,String> loginMap=new HashMap<>();
        loginMap.put("admin_number",account);
        loginMap.put("admin_pass",password);

        MediaType MEDIA_TYPE_JSON
                = MediaType.parse("application/json; charset=utf-8");
        Log.d(TAG,"Json Args is :"+JSONObject.toJSONString(loginMap));
        Request request=new Request.Builder()
                .url(Constant.LOGIN_URL)
                .post(RequestBody.create(MEDIA_TYPE_JSON,JSONObject.toJSONString(loginMap)))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg=new Message();
                msg.what=LOGIN_FAILED;
                msg.arg1=CONNECTION_ERROR;
                mHandler.sendMessage(msg);
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Message msg=new Message();

                responseText=response.body().string();
                Log.d(TAG,"ResponseText :"+responseText);
                if(responseText.trim().equals("error")){
                    msg.what=LOGIN_FAILED;
                    msg.arg1=PASSWORD_ERROR;
                    mHandler.sendMessage(msg);
                }else{
                    msg.what=LOGIN_SUCCESS;
                    mHandler.sendMessage(msg);
                    JSONObject object=JSONObject.parseObject(responseText);
                    editSharedpreference(object);
                }

            }
        });
    }


    private void editSharedpreference(JSONObject object){
        preferences=getSharedPreferences(Constant.USER_PREF_NAME,MODE_PRIVATE);
        SharedPreferences.Editor editor=preferences.edit()
                .putString(Constant.USER.USER_ID,object.getString("user_id"))
                .putString(Constant.USER.USER_ACCOUNT,object.getString("user_phone"))
                .putString(Constant.USER.USER_CREDIT,object.getString("user_credit"))
                .putString(Constant.USER.USER_NAME,object.getString("user_name"))
                .putString(Constant.USER.USER_PHONE,object.getString("user_phone"));
        editor.apply();
    }

}
