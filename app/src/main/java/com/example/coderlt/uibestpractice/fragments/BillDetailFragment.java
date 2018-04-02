package com.example.coderlt.uibestpractice.fragments;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.adapter.BillDetailAdapter;
import com.example.coderlt.uibestpractice.bean.Bill;
import com.example.coderlt.uibestpractice.bean.SpecificGroup;
import com.example.coderlt.uibestpractice.database.MyDatabaseHelper;
import com.example.coderlt.uibestpractice.utils.Constant;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
        Log.d(TAG,"......................onCreateView......................");
        View view = null;
        view = inflater.inflate(R.layout.fragment_bill_detail,container,false);
        //initBills();
        mContext = getActivity();
        dbBills = new ArrayList<>();
        detailListView = view.findViewById(R.id.bill_detail_list_view);
        adapter = new BillDetailAdapter(mContext,R.layout.bill_detail_item,dbBills);
        detailListView.setAdapter(adapter);
        return view;
    }

    /**
     * 本地 IO 通常比较耗时，建议放入 异步线程 操作
     * 没什么用，我们需要 经过处理的 readDB (process DB)
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
     * 加工数据库的数据
     * 从DB中提取对应月份的数据
     * 计算每天的收支总和，并形成时间戳节点，插入list
     * @param month  select the data of the month
     */
    private void processDB(String month){
        Log.d(TAG,"process db.");
        dbBills.clear();
        mDbHelper = new MyDatabaseHelper(mContext,"HpStore.db",null,4);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();

        @SuppressWarnings(value={"all"})
        Cursor cursor = db.rawQuery("SELECT * FROM Bill WHERE substr(bill_date,1,7)=?",
                new String[]{month});

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
                bill.setViewType(Bill.TYPE_BILL);
                try{
                    // 这里主要是对字符串转换成 date 对象
                    bill.setDate(sdf.parse(cursor.getString(cursor.getColumnIndex(Constant.BILL.DATE))));
                }catch (ParseException ex){
                    ex.printStackTrace();
                }
                Log.d(TAG,bill.toString());
                dbBills.add(bill);
            }while (cursor.moveToNext());
        }
        cursor.close();

        // process dbBills，Not effective.
        Bill previous = new Bill();
        if(dbBills.size()==0)return;
        previous.setDate(dbBills.get(0).getDate());
        Bill current = null;
        float sumIn = 0;
        float sumOut = 0;
        int    addIndex=0;
        for(int i=0;i<=dbBills.size();i++){
            if (i==dbBills.size()){
                Bill newBill = new Bill();
                newBill.setDate(previous.getDate());
                newBill.setIn(sumIn);
                newBill.setOut(sumOut);
                newBill.setViewType(Bill.TYPE_TIME);
                dbBills.add(addIndex,newBill);
                Log.d(TAG,">>>>>>>>>>>>>>>>>>>>.add a new node at index "+addIndex+" .<<<<<<<<<");
                break;
            }
            current = dbBills.get(i);
            if(current.getDate().equals(previous.getDate())){
                Log.d(TAG,"_________.current date is :" +sdf.format(current.getDate())+
                        "  The same as previous bill._______");
                if(current.getSpecificGroup().getMoneyType()==SpecificGroup.TYPE_IN)
                    sumIn+=current.getAmount();
                else
                    sumOut+=current.getAmount();
            }else {
                Log.d(TAG,">>>>>>>>>.add a new node at "+addIndex+".<<<<<<<<<<<<<");
                Bill newBill = new Bill();
                newBill.setDate(previous.getDate());
                newBill.setIn(sumIn);
                newBill.setOut(sumOut);
                newBill.setViewType(Bill.TYPE_TIME);
                dbBills.add(addIndex,newBill);
                addIndex = i+1;
                // 新增了 elemnt，所以整体后移一位,重新计算收支和
                if(current.getSpecificGroup().getMoneyType()==SpecificGroup.TYPE_IN)
                    sumIn=(float)current.getAmount();
                else
                    sumOut=(float)current.getAmount();
                i++;
            }

            previous = current;
        }
        Log.d(TAG,"................ notify data .............");
        for(int i=0;i<dbBills.size();i++){
            Log.d(TAG,dbBills.get(i).toString());
            // 验证 dbBills 正常
        }
    }

    /**
     * 避免activity返回之后数据不加载，因为 onCreate 没有执行，故而数据不会加载
     * 但是 onStart 会继续执行
     */
    @Override
    public void onStart(){
        super.onStart();
        //readDB();
        Log.d(TAG,".........................onStart..................");
        processDB("2018-04");
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    public void refreshData(String month){
        Log.d(TAG,"refresh data "+month);
        processDB(month);
        adapter.notifyDataSetChanged();
    }
}
