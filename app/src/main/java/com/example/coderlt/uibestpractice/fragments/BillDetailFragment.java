package com.example.coderlt.uibestpractice.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.adapter.BillDetailAdapter;
import com.example.coderlt.uibestpractice.bean.Bill;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by coderlt on 2018/3/22.
 */

public class BillDetailFragment extends Fragment {
    private ListView detailListView;
    private BillDetailAdapter adapter;
    private List<Bill> bills;
    private Context mContext;
    private int[] icons={R.drawable.ic_specific_clothe,R.drawable.ic_specific_course,
                        R.drawable.ic_specific_entertainment,R.drawable.ic_specific_food,
                        R.drawable.ic_specific_management};
    private String[] names = {"餐饮","娱乐","课程","运动","兼职","医疗","旅行"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = null;
        view = inflater.inflate(R.layout.fragment_bill_detail,container,false);
        initBills();
        mContext = getActivity();
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

                // * 设置其它信息
                bill.setIconId(icons[(int)Math.floor(Math.random()*5)]);
                bill.setAmount(88.88f);
                bill.setViewType(Bill.TYPE_BILL);
                bill.setName(names[(int)Math.floor(Math.random()*7)]);
                bill.setPay(true);
                if(bill.isPay()){
                    outSum+=bill.getAmount();
                }else{
                    inSum+=bill.getIconId();
                }
                bills.add(bill);
            }
            timeBill.setIn(inSum);
            timeBill.setOut(outSum);
        }
    }
}
