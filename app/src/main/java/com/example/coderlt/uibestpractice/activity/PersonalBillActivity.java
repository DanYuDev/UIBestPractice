package com.example.coderlt.uibestpractice.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.coderlt.uibestpractice.R;

public class PersonalBillActivity extends AppCompatActivity implements View.OnClickListener{
    private FloatingActionButton writeFab;
    private TextView titleText;
    private TabLayout billTab;

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
        // find view
        writeFab = findViewById(R.id.write_bill_fab);
        titleText = findViewById(R.id.title_tv);
        billTab = findViewById(R.id.personal_bill_tab);

        // add listener
        writeFab.setOnClickListener(this);
        billTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        // init data
        titleText.setText("记账本");
        billTab.addTab(billTab.newTab().setText("明细"));
        billTab.addTab(billTab.newTab().setText("类别报表"));
        billTab.addTab(billTab.newTab().setText("账户"));
    }
}
