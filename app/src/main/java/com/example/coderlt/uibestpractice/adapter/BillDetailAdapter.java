package com.example.coderlt.uibestpractice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.example.coderlt.uibestpractice.bean.Bill;

import java.util.List;

/**
 * Created by coderlt on 2018/3/22.
 */

public class BillDetailAdapter extends ArrayAdapter {
    private int resId;
    private Context mContext;
    private List<Bill> bills;

    public BillDetailAdapter(Context context,int resId,List<Bill> bills){
        super(context,resId,bills);
        mContext = context;
        this.resId = resId;
        bills = bills;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        if(convertView!=null){
            convertView = LayoutInflater.from(mContext).inflate(resId,parent,false);
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
