package com.example.coderlt.uibestpractice.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coderlt.uibestpractice.R;

public class PublishTaskActivity extends AppCompatActivity {
    private ImageView moreIv;
    private TextView titleText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish_task);
        moreIv = findViewById(R.id.title_right);
        moreIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PublishTaskActivity.this,ChatActivity.class));
            }
        });

        initViews();
    }

    private void initViews(){
        titleText = findViewById(R.id.title_tv);

        titleText.setText("新建任务");
    }
}
