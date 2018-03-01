package com.example.coderlt.uibestpractice.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.coderlt.uibestpractice.R;

public class PersonalBillActivity extends AppCompatActivity implements View.OnClickListener{
    private FloatingActionButton writeFab;
    private TextView titleText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_bill);
        initViews();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.write_bill_fab:
                startActivity(new Intent(this,EditBillActivity.class));
                break;
            default:
                break;
        }
    }

    private void initViews(){
        writeFab = findViewById(R.id.write_bill_fab);
        titleText = findViewById(R.id.title_tv);

        writeFab.setOnClickListener(this);

    }
}
