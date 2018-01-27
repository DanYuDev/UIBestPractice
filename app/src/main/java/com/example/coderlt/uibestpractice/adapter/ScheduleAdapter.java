package com.example.coderlt.uibestpractice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.bean.ScheduleBean;
import com.example.coderlt.uibestpractice.bean.TableData;

import java.util.List;

/**
 * Created by coderlt on 2018/1/21.
 */

public class ScheduleAdapter extends RecyclerView.Adapter<ScheduleAdapter.VH> {
    private final String TAG=getClass().getName();
    private int resId;
    private List<ScheduleBean> scheduleList;
    private Context mContext;
    private int h;
    private View ver;
    public class VH extends RecyclerView.ViewHolder{
        public TextView msgTv;
        public ViewGroup v;

        public VH(View v){
            super(v);
            this.v=(ViewGroup)v;
            msgTv=v.findViewById(R.id.node_tv);
        }
    }

    public ScheduleAdapter(List<ScheduleBean> scheduleList, int resId,Context context){
        Log.d(TAG,"on adapter constructor ");
        this.scheduleList=scheduleList;
        this.resId=resId;
        mContext=context;
    }

    @Override
    public ScheduleAdapter.VH onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Log.d(TAG,"on create VH");
        ScheduleAdapter.VH holder = new ScheduleAdapter.VH(LayoutInflater.from(mContext)
                .inflate(resId, parent, false));
//        ver=holder.v.findViewById(R.id.vertical_line);
//        ver.post(new Runnable() {
//            @Override
//            public void run() {
//                holder.v.findViewById(R.id.vertical_line);
//                h=holder.msgTv.getLayoutParams().height;
//                Log.d(TAG,"holder msgTv height is "+h);
//                RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) ver.getLayoutParams();
//                layoutParams.height=h;
//                ver.setLayoutParams(layoutParams);
//            }
//        });

//        v=holder.v.findViewById(R.id.vertical_line);
//        h=holder.msgTv.getLayoutParams().height;
//        Log.d(TAG,"holder msgTv height is "+h);
//        RelativeLayout.LayoutParams layoutParams=(RelativeLayout.LayoutParams) v.getLayoutParams();
//        layoutParams.height=h;
//        v.setLayoutParams(layoutParams);
        return holder;
    }

    @Override
    public void onBindViewHolder(ScheduleAdapter.VH holder, int position)
    {
        ScheduleBean data= scheduleList.get(position);
        //Log.d(TAG,"on bind VH");
        Log.d(TAG,"holder height is "+holder.msgTv.getHeight());
        holder.msgTv.setText(data.getMsg());
        //RelativeLayout.LayoutParams lp=(RelativeLayout.LayoutParams)holder.itemView.getLayoutParams();
        //lp.height=
        ver=holder.v.findViewById(R.id.vertical_line);
        //int h;//=holder.msgTv.getHeight();
        h=holder.msgTv.getHeight();
        Log.d(TAG,"real height is : "+h);
        ver.getLayoutParams().height=h;
        holder.v.requestLayout();
        //这是最新版本
//        holder.msgTv.post(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
        //holder.numTv.setText("¥"+data.getNum());
    }

    @Override
    public int getItemCount()
    {
        return scheduleList.size();
    }
}
