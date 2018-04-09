package com.example.coderlt.uibestpractice.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.coderlt.uibestpractice.R;

public class IntroductionActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView titleLeft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);

        initViews();
    }

    private void initViews(){
        titleLeft = findViewById(R.id.title_left);

        titleLeft.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.title_left:
                finish();
                break;
            default:
                break;
        }
    }
}
