package com.example.coderlt.uibestpractice.activity;

import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.View.NumericalKeyboard;
import com.example.coderlt.uibestpractice.adapter.SpecificCyclerAdapter;
import com.example.coderlt.uibestpractice.bean.SpecificGroup;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EditBillActivity extends AppCompatActivity {
    private TextView titleText;
    private TabLayout outInTabLayout;
    private List<SpecificGroup> incomeList = new ArrayList<>();
    private List<SpecificGroup> disbursementList = new ArrayList<>();
    private List<SpecificGroup> balanceList = new ArrayList<>();
    private RecyclerView specificCycler;
    private SpecificCyclerAdapter adapter;
    private NumericalKeyboard keyboard;
    private SpecificGroup[] specificDisbursementGroups =
            {       new SpecificGroup("衣服",R.drawable.ic_specific_clothe),
                    new SpecificGroup("课程",R.drawable.ic_specific_course),
                    new SpecificGroup("娱乐",R.drawable.ic_specific_entertainment),
                    new SpecificGroup("饮食",R.drawable.ic_specific_food),
                    new SpecificGroup("零食",R.drawable.ic_specific_snack),
                    new SpecificGroup("运动",R.drawable.ic_specific_sport),
                    new SpecificGroup("理财",R.drawable.ic_specific_management)
            };
    private SpecificGroup[] specificIncomeGroups =
            {       new SpecificGroup("工资",R.drawable.ic_specific_salary),
                    new SpecificGroup("兼职",R.drawable.ic_specific_parttime),
                    new SpecificGroup("红包",R.drawable.ic_specific_redpacket),
                    new SpecificGroup("股票",R.drawable.ic_specific_stock),
                    new SpecificGroup("礼金",R.drawable.ic_specific_gift)
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bill);
        initViews();
        initCycler();
        outInTabLayout.getTabAt(1).select();
    }

    private void initViews(){
        titleText = findViewById(R.id.title_tv);
        outInTabLayout = findViewById(R.id.out_in_tablayout);
        specificCycler = findViewById(R.id.specific_cycler);
        keyboard = findViewById(R.id.numerical_keyboard);

        outInTabLayout.addTab(outInTabLayout.newTab());
        outInTabLayout.addTab(outInTabLayout.newTab());
        outInTabLayout.getTabAt(0).setText("收入");
        outInTabLayout.getTabAt(1).setText("支出");
        titleText.setText("随手记");

        outInTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if("收入".equals(tab.getText())){
                    balanceList.clear();
                    balanceList.addAll(Arrays.asList(specificIncomeGroups));
                    adapter.notifyDataSetChanged();
                }
                else{
                    balanceList.clear();
                    balanceList.addAll(Arrays.asList(specificDisbursementGroups));
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initCycler(){
        adapter = new SpecificCyclerAdapter(balanceList,R.layout.func_item,this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        specificCycler.setLayoutManager(layoutManager);
        specificCycler.setAdapter(adapter);
    }
}
