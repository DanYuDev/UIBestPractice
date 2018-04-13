package com.example.coderlt.uibestpractice.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.example.coderlt.uibestpractice.MyApplication;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.utils.Constant;
import com.example.coderlt.uibestpractice.utils.OkHttpUtils;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class NewShopActivity extends AppCompatActivity {
    private static final String TAG = "NewShopActivity";

    private TextView titleText;

    private EditText shopNameEt;
    private EditText shopAddressEt;
    private EditText shopDoorNumEt;
    private EditText shopTelEt;
    private EditText shopPhoneEt;

    private Button saveBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_shop);
        initViews();
    }

    private void initViews(){
        // findViews
        titleText = findViewById(R.id.title_tv);
        shopNameEt = findViewById(R.id.shop_name_et);
        shopAddressEt = findViewById(R.id.shop_address_et);
        shopTelEt = findViewById(R.id.shop_tel_et);
        shopDoorNumEt = findViewById(R.id.shop_doornum_et);
        shopPhoneEt = findViewById(R.id.shop_phone);
        saveBtn = findViewById(R.id.save_btn);

        // set listener
        saveBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                uploadShopMsg();
            }
        });
        // initDatas
        titleText.setText("新建门店");
    }

    private void uploadShopMsg(){
        OkHttpClient okHttpClient = new OkHttpClient();
        String jsonArgs;
        Map<String,Object> map = new HashMap<>();
        map.put("merchant_name",shopNameEt.getText());
        map.put("merchant_address",shopAddressEt.getText());
        map.put("merchant_boss","田云刚");
        map.put("merchant_telephone",shopTelEt.getText());
        map.put("merchant_door_number",shopDoorNumEt.getText());
        map.put("merchant_phone",shopPhoneEt.getText());
        Log.d(TAG,JSON.toJSONString(map));
        RequestBody requestBody = RequestBody.create(Constant.MEDIA_TYPE_JSON, JSON.toJSONString(map));
        Request request = new Request.Builder()
                .url(Constant.SHOP.BUILD_SHOP_URL)
                .post(requestBody)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"Reg failed!");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG,"Reg shop result "+response.body().string());
            }
        });
    }
}
