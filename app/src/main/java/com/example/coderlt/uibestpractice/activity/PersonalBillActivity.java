package com.example.coderlt.uibestpractice.activity;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.fragments.BillDetailFragment;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.util.Date;

public class PersonalBillActivity extends AppCompatActivity implements View.OnClickListener{
    private FloatingActionButton writeFab;
    private TextView titleText;
    private ImageView monthIv;
    private TabLayout billTab;
    private Fragment  billDetailFragment;
    private FragmentManager fm;
    private FragmentTransaction fmt;

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
            case R.id.month_select_iv:
                Utils.showToast("Select Month.");
                // TODO 回头自己写一个日期选择动画，从底部弹出 window 那种，比较优雅
                TimePickerView pvTime = new TimePickerBuilder(PersonalBillActivity.this,
                        new OnTimeSelectListener() {
                            @Override
                            public void onTimeSelect(Date date, View v) {
                                Utils.showToast("Date picked: "+date);
                            }
                        }).isDialog(true)
                        .build();
                pvTime.show();
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
        monthIv = findViewById(R.id.month_select_iv);

        // init fragments
        fm = getSupportFragmentManager();

        // add listener
        monthIv.setOnClickListener(this);
        writeFab.setOnClickListener(this);
        billTab.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()){
                    case 0:
                        billDetailFragment = new BillDetailFragment();
                        fmt = fm.beginTransaction();
                        fmt.replace(R.id.fragment_content,billDetailFragment)
                                .commit();
                        break;
                    default:
                        Utils.showToast("攻城狮正在努力 Coding...");
                        //时间选择器
                        break;
                }
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
