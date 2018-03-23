package com.example.coderlt.uibestpractice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.bean.Bill;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by coderlt on 2018/3/22.
 */

public class BillDetailAdapter extends ArrayAdapter {
    private int resId;
    private Context mContext;
    private List<Bill> bills;
    private SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

    public BillDetailAdapter(Context context,int resId,List<Bill> bills){
        super(context,resId,bills);
        mContext = context;
        this.resId = resId;
        this.bills = bills;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        Bill bill = bills.get(position);
        int viewType = bill.getViewType();
        if(convertView == null){
            if(viewType == Bill.TYPE_BILL){
                convertView = LayoutInflater.from(mContext).inflate(resId,parent,false);
                ImageView billIv = convertView.findViewById(R.id.bill_type_iv);
                TextView billName = convertView.findViewById(R.id.bill_detail_tv);
                TextView billAmount = convertView.findViewById(R.id.bill_amount_tv);

                Glide.with(mContext).load(bill.getIconId()).into(billIv);
                billName.setText(bill.getName());
                billAmount.setText(bill.getAmount()+"");
            }else{
                convertView = LayoutInflater.from(mContext)
                        .inflate(R.layout.bill_time_item,parent,false);
                TextView dateTv = convertView.findViewById(R.id.bill_date_tv);
                TextView sumTv = convertView.findViewById(R.id.bill_sum__tv);

                dateTv.setText(sdf.format(bill.getDate()));
                sumTv.setText("支出:"+bill.getOut()+"  收入:"+bill.getIn());
            }
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        return bills.get(position).getViewType();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }
}
