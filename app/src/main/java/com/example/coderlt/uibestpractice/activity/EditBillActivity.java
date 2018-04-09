package com.example.coderlt.uibestpractice.activity;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.View.NumericalKeyboard;
import com.example.coderlt.uibestpractice.adapter.SpecificCyclerAdapter;
import com.example.coderlt.uibestpractice.bean.Bill;
import com.example.coderlt.uibestpractice.bean.SpecificGroup;
import com.example.coderlt.uibestpractice.database.MyDatabaseHelper;
import com.example.coderlt.uibestpractice.utils.Constant;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import okhttp3.internal.Util;

public class EditBillActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "EditBillActivity";
    private ImageView titleLeft;
    private TextView titleText;
    private TabLayout outInTabLayout;
    private List<SpecificGroup> incomeList = new ArrayList<>();
    private List<SpecificGroup> disbursementList = new ArrayList<>();
    private List<SpecificGroup> balanceList = new ArrayList<>();
    private RecyclerView specificCycler;
    private SpecificCyclerAdapter adapter;
    private NumericalKeyboard keyboard;
    private ImageView deleteIv;
    private ImageView cameraIv;
    private TextView  caculateTv;
    private ImageView noteIv;
    private TextView noteTv;
    private ImageView cancelIv;
    private StringBuilder sb = null;
    private MyDatabaseHelper dbHelper;
    private Bill editBill;

    private SpecificGroup[] specificDisbursementGroups =
            {       new SpecificGroup("衣服",R.drawable.ic_specific_clothe,SpecificGroup.TYPE_OUT),
                    new SpecificGroup("课程",R.drawable.ic_specific_course,SpecificGroup.TYPE_OUT),
                    new SpecificGroup("娱乐",R.drawable.ic_specific_entertainment,SpecificGroup.TYPE_OUT),
                    new SpecificGroup("饮食",R.drawable.ic_specific_food,SpecificGroup.TYPE_OUT),
                    new SpecificGroup("零食",R.drawable.ic_specific_snack,SpecificGroup.TYPE_OUT),
                    new SpecificGroup("运动",R.drawable.ic_specific_sport,SpecificGroup.TYPE_OUT),
                    new SpecificGroup("理财",R.drawable.ic_specific_management,SpecificGroup.TYPE_OUT)
            };
    private SpecificGroup[] specificIncomeGroups =
            {       new SpecificGroup("工资",R.drawable.ic_specific_salary,SpecificGroup.TYPE_IN),
                    new SpecificGroup("兼职",R.drawable.ic_specific_parttime,SpecificGroup.TYPE_IN),
                    new SpecificGroup("红包",R.drawable.ic_specific_redpacket,SpecificGroup.TYPE_IN),
                    new SpecificGroup("股票",R.drawable.ic_specific_stock,SpecificGroup.TYPE_IN),
                    new SpecificGroup("礼金",R.drawable.ic_specific_gift,SpecificGroup.TYPE_IN)
            };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_bill);
        initViews();
        initCycler();
        outInTabLayout.getTabAt(1).select();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.title_left:
                finish();
                break;
            case R.id.camera_iv:
                Glide.with(this).load(R.drawable.ic_app).into(noteIv);
                noteIv.setVisibility(View.VISIBLE);
                cancelIv.setVisibility(View.VISIBLE);
                break;
            case R.id.delete_iv:
                Utils.showToast("delete num");
                if(sb.length()==0)break;
                sb = new StringBuilder(sb.substring(0,sb.length()-1));
                caculateTv.setText(sb);
                break;
            case R.id.cancel_iv:
                noteIv.setVisibility(View.INVISIBLE);
                cancelIv.setVisibility(View.INVISIBLE);
                break;
            default:
                break;
        }
    }

    private void initViews(){
        titleLeft = findViewById(R.id.title_left);
        titleText = findViewById(R.id.title_tv);
        outInTabLayout = findViewById(R.id.out_in_tablayout);
        specificCycler = findViewById(R.id.specific_cycler);
        keyboard = findViewById(R.id.numerical_keyboard);
        cameraIv = findViewById(R.id.camera_iv);
        caculateTv = findViewById(R.id.caculate_tv);
        deleteIv = findViewById(R.id.delete_iv);
        cancelIv = findViewById(R.id.cancel_iv);
        noteIv = findViewById(R.id.note_iv);
        noteTv = findViewById(R.id.note_tv);

        titleLeft.setOnClickListener(this);
        cameraIv.setOnClickListener(this);
        cancelIv.setOnClickListener(this);
        deleteIv.setOnClickListener(this);
        noteIv.setOnClickListener(this);
        noteTv.setOnClickListener(this);


        outInTabLayout.addTab(outInTabLayout.newTab());
        outInTabLayout.addTab(outInTabLayout.newTab());
        outInTabLayout.getTabAt(0).setText("收入");
        outInTabLayout.getTabAt(1).setText("支出");
        titleText.setText("随手记");

        // init database.
        dbHelper = new MyDatabaseHelper(this,"HpStore.db",null,4);
        final SQLiteDatabase db = dbHelper.getWritableDatabase();

        editBill = new Bill();

        caculateTv.setText("0.00");
        sb = new StringBuilder("");

        /**
         * 处理键盘输入
         */
        keyboard.setOnItemClickedListener(new NumericalKeyboard.OnItemClickedListener() {
            @Override
            public void onItemClicked(String str) {
                if(str.equals("确定")){
                    if(sb.length()==0) Utils.showToast("请输入金额");
                    else if(editBill.getSpecificGroup()==null) Utils.showToast("请选择消费类型");
                    else {
                        editBill.setAmount(Double.parseDouble(sb.toString()));
                        editBill.setDate(new Date());
                        // 只有当 editbill 完全设置时，才可以存入数据库
                        saveToSql(db);
                        finish();
                    }
                }else{
                    if(str.equals(".") && sb.toString().contains("."))return;
                    sb.append(str);
                    caculateTv.setText(sb);
                }
            }
        });

        /**
         * 处理Tab切换
         */
        outInTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                TextView title = (TextView)(((LinearLayout) ((LinearLayout) outInTabLayout.getChildAt(0)).
                        getChildAt(tab.getPosition())).getChildAt(1));
                title.setBackgroundResource(R.drawable.tab_tv_bg);
                title.setPadding(45,15,45,15);
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
                TextView title = (TextView)(((LinearLayout) ((LinearLayout) outInTabLayout.getChildAt(0)).
                        getChildAt(tab.getPosition())).getChildAt(1));
                title.setBackground(null);
            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
    }

    /**
     * 初始化消费类型列表
     */
    private void initCycler(){
        adapter = new SpecificCyclerAdapter(balanceList,R.layout.func_item,this);
        // 监听 type 列表，设置 bill 的 specific
        adapter.setOnSpecificSelectedListener(new SpecificCyclerAdapter.OnSpecificSelectedListener() {
            @Override
            public void onSpecificSelected(SpecificGroup specificGroup) {
                editBill.setSpecificGroup(specificGroup);
            }
        });
        GridLayoutManager layoutManager = new GridLayoutManager(this,3);
        specificCycler.setLayoutManager(layoutManager);
        specificCycler.setAdapter(adapter);
    }

    /**
     * 键盘点击确定之后，将账单数据存入数据库
     * @param n 消费金额
     */
    private void saveToSql(SQLiteDatabase db){
        sb.setLength(0);
        caculateTv.setText("0");
        Utils.showToast("Save to sql.");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        /** 执行向数据库添加数据的操作
         *  全局变量 editbill 分散各处收集你输入的或者选择的账单信息
         *  在此处 editbill 应该是完全体，并将其分散，提交给数据库
         */
        Log.d(TAG,editBill.toString());
        ContentValues values = new ContentValues();
        values.put(Constant.BILL.ICON,editBill.getSpecificGroup().getIconId());
        values.put(Constant.BILL.NAME,editBill.getSpecificGroup().getName());
        values.put(Constant.BILL.TYPE,editBill.getSpecificGroup().getMoneyType());
        values.put(Constant.BILL.NOTEPATH,editBill.getNotePath());
        values.put(Constant.BILL.NOTETEXT,editBill.getNoteText());
        values.put(Constant.BILL.MONEY,editBill.getAmount());
        //TODO 值得注意
        values.put(Constant.BILL.DATE,sdf.format(editBill.getDate()).toString());

        db.insert("Bill",null,values);
    }
}
