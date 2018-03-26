package com.example.coderlt.uibestpractice.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.adapter.BillDetailAdapter;
import com.example.coderlt.uibestpractice.bean.Bill;
import com.example.coderlt.uibestpractice.bean.SpecificGroup;
import com.example.coderlt.uibestpractice.database.MyDatabaseHelper;
import com.example.coderlt.uibestpractice.utils.Constant;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by coderlt on 2018/3/22.
 * the code is great! enjoy it .
 */

public class BillDetailFragment extends Fragment {
    private static final String TAG = "BillDetailFragment";
    private MyDatabaseHelper mDbHelper;
    private ListView detailListView;
    private BillDetailAdapter adapter;
    private List<Bill> bills;
    private List<Bill> dbBills;
    private Context mContext;
    private int[] icons={R.drawable.ic_specific_clothe,R.drawable.ic_specific_course,
                        R.drawable.ic_specific_entertainment,R.drawable.ic_specific_food,
                        R.drawable.ic_specific_management};
    private String[] names = {"餐饮","娱乐","课程","运动","兼职","医疗","旅行"};
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = null;
        view = inflater.inflate(R.layout.fragment_bill_detail,container,false);
        initBills();
        mContext = getActivity();
        //readDB();
        adapter = new BillDetailAdapter(mContext,R.layout.bill_detail_item,bills);
        detailListView = view.findViewById(R.id.bill_detail_list_view);
        detailListView.setAdapter(adapter);
        return view;
    }

    private void initBills(){
        bills = new ArrayList<>();
        Calendar time = Calendar.getInstance();
        time.clear();
        time.set(Calendar.YEAR,2018);
        time.set(Calendar.MONTH,3);
        for(int i=1;i<28;i++){
            // 时间戳是头部
            Bill timeBill = new Bill();
            timeBill.setViewType(Bill.TYPE_TIME);
            bills.add(timeBill);
            time.set(Calendar.DATE,i);
            timeBill.setDate(time.getTime());
            // 五个订单为一天
            float outSum = 0;
            float inSum = 0;
            for(int j=1; j<5;j++){

                // 为 Bill 设置时间
                Bill bill = new Bill();
                bill.setDate(time.getTime());

                // 设置 头部信息 图标名称账单属性（支出还是收入）等
                SpecificGroup specificGroup  =new SpecificGroup();
                specificGroup.setIconId(icons[(int)Math.floor(Math.random()*5)]);
                specificGroup.setName(names[(int)Math.floor(Math.random()*7)]);
                specificGroup.setMoneyType(SpecificGroup.TYPE_OUT);
                bill.setSpecificGroup(specificGroup);

                // * 设置其它信息
                bill.setViewType(Bill.TYPE_BILL);
                if(bill.getSpecificGroup().getMoneyType() == SpecificGroup.TYPE_OUT){
                    outSum+=bill.getAmount();
                }else{
                    inSum+=bill.getAmount();
                }
                bills.add(bill);
            }
            timeBill.setIn(inSum);
            timeBill.setOut(outSum);
        }
    }

    /**
     * 本地 IO 通常比较耗时，建议放入 异步线程 操作
     */
    private void readDB(){
        mDbHelper = new MyDatabaseHelper(mContext,"HpStore.db",null,4);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        Cursor cursor = db.query("Bill",
                null,null,null,null,null,null);
        dbBills = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Bill bill = new Bill();
                SpecificGroup sp = new SpecificGroup();

                sp.setName(cursor.getString(cursor.getColumnIndex(Constant.BILL.NAME)));
                sp.setMoneyType(cursor.getInt(cursor.getColumnIndex(Constant.BILL.TYPE)));
                sp.setIconId(cursor.getInt(cursor.getColumnIndex(Constant.BILL.ICON)));

                bill.setSpecificGroup(sp);
                bill.setAmount(cursor.getDouble(cursor.getColumnIndex(Constant.BILL.MONEY)));
                bill.setNotePath(cursor.getString(cursor.getColumnIndex(Constant.BILL.NOTEPATH)));
                bill.setNoteText(cursor.getString(cursor.getColumnIndex(Constant.BILL.NOTETEXT)));
                try{
                    // 这里主要是对字符串转换成 date 对象
                    bill.setDate(sdf.parse(cursor.getString(cursor.getColumnIndex(Constant.BILL.DATE))));
                }catch (ParseException ex){ ex.printStackTrace();}

                Log.d(TAG,bill.toString());
                dbBills.add(bill);
            }while (cursor.moveToNext());
        }
        cursor.close();
    }

    /**
     * 避免activity返回之后数据不加载，因为 onCreate 没有执行，故而数据不回家在
     * 但是 onStart 会继续执行
     */
    @Override
    public void onStart(){
        super.onStart();
        readDB();
    }
}
