package com.example.coderlt.uibestpractice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.bean.TableData;
import java.util.List;

/**
 * Created by coderlt on 2018/1/19.
 */

public class TableAdapter extends RecyclerView.Adapter<TableAdapter.VH> {
    private final String TAG=getClass().getName();
    private int resId;
    private List<TableData> tableList;
    private Context mContext;
    public class VH extends RecyclerView.ViewHolder{
        public TextView titleTv;
        public TextView numTv;

        public VH(View v){
            super(v);
            titleTv=v.findViewById(R.id.item_title);
            numTv=v.findViewById(R.id.item_num);
        }
    }

    public TableAdapter(List<TableData> tableList, int resId,Context context){
        Log.d(TAG,"on adapter constructor ");
        this.tableList=tableList;
        this.resId=resId;
        mContext=context;
    }

    @Override
    public TableAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Log.d(TAG,"on create VH");
        TableAdapter.VH holder = new TableAdapter.VH(LayoutInflater.from(mContext)
                .inflate(resId, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(TableAdapter.VH holder, int position)
    {
        TableData tableData= tableList.get(position);
        Log.d(TAG,"on bind VH");
        holder.titleTv.setText(tableData.getTitle());
        holder.numTv.setText("¥"+tableData.getNum());
    }

    @Override
    public int getItemCount()
    {
        return tableList.size();
    }
}
