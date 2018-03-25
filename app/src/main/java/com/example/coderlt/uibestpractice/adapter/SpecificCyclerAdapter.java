package com.example.coderlt.uibestpractice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.bean.SpecificGroup;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.util.List;

/**
 * Created by coderlt on 2018/3/1.
 */

public class SpecificCyclerAdapter extends RecyclerView.Adapter <SpecificCyclerAdapter.ViewHolder>{
    private final String TAG=getClass().getName();
    private int resId;
    private List<SpecificGroup> options;
    private Context mContext;
    private OnSpecificSelectedListener listener=null;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv;
        public TextView tv;

        public ViewHolder(View v){
            super(v);
            iv=v.findViewById(R.id.func_iv);
            tv=v.findViewById(R.id.func_tv);
        }
    }

    public SpecificCyclerAdapter(List<SpecificGroup> specificList, int resId, Context context){
        options = specificList;
        mContext = context;
        this.resId = resId;
    }

    @Override
    public SpecificCyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=LayoutInflater.from(mContext).inflate(resId,parent,false) ;
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        final SpecificGroup option= options.get(position);
        Log.d(TAG,"on bind VH ");
        holder.tv.setText(option.getName());
        Glide.with(mContext).load(option.getIconId()).into(holder.iv);
        holder.iv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener!=null){
                    listener.onSpecificSelected(option);
                }else{
                    Utils.showToast("Should set on listener.");
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return options.size();
    }

    public void setOnSpecificSelectedListener(OnSpecificSelectedListener listener){
        this.listener = listener;
    }

    public interface OnSpecificSelectedListener{
        void onSpecificSelected(SpecificGroup specificGroup);
    }
}
