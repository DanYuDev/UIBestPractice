package com.example.coderlt.uibestpractice.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.View.EditSpinnerText;
import com.example.coderlt.uibestpractice.utils.Constant;

import java.io.IOException;
import java.util.Arrays;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 *  To support different entry for different Authroity.
 */
public class EntryActivity extends AppCompatActivity {
    private EditSpinnerText editSpinnerText;
    private String[] array = {"Boss","Manager","Technician"};
    private Button enterBtn;
    private OkHttpClient okHttpClient;
    private MediaType jsonType = MediaType.parse("application/json,utf-8");
    private String params;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entry);
        initViews();
    }

    private void initViews(){
        editSpinnerText = findViewById(R.id.identification_tv);
        editSpinnerText.setSpinnerList(Arrays.asList(array));
        enterBtn = findViewById(R.id.enter_btn);

        enterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog = new ProgressDialog(EntryActivity.this);
                dialog.setTitle("身份信息上传中...");
                dialog.show();
                Intent intent;
                if("Technician".equals(editSpinnerText.getText().toString())){
                    intent = new Intent(EntryActivity.this,TechnicianNavigationActivity.class);
                }else{
                    intent = new Intent(EntryActivity.this,NavigationActivity.class);
                }
                intent.putExtra(Constant.USER.USER_ID,getIntent().getStringExtra(Constant.USER.USER_ID));
                startActivity(intent);
                dialog.dismiss();
                //uploadIdentification();
            }
        });
    }

    private void uploadIdentification(){
        okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(Constant.REG_URL)
                .post(RequestBody.create(jsonType,params))
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        });
    }
}
