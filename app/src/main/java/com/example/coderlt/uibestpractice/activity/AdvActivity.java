package com.example.coderlt.uibestpractice.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.utils.Constant;

public class AdvActivity extends AppCompatActivity {
    SharedPreferences preferences;

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
}
