package com.example.coderlt.uibestpractice.adapter;

import android.content.Context;
import android.graphics.Path;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coderlt.uibestpractice.MyApplication;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.activity.NavigationActivity;
import com.example.coderlt.uibestpractice.bean.Option;
import com.example.coderlt.uibestpractice.utils.Constant;

import java.util.List;

/**
 * Created by coderlt on 2018/1/10.
 */

public class FuncRecyclerAdapter extends RecyclerView.Adapter<FuncRecyclerAdapter.ViewHolder> {
    private final String TAG=getClass().getName();
    private int resId;
    private List<Option> options;
    private Context mContext;
    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv;
        public TextView tv;

        public ViewHolder(View v){
            super(v);
            iv=v.findViewById(R.id.func_iv);
            tv=v.findViewById(R.id.func_tv);
        }
    }

    public FuncRecyclerAdapter(List<Option> optionList, int resId,Context context){
        Log.d(TAG,"on adapter constructor ");
        options=optionList;
        this.resId=resId;
        mContext=context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        Log.d(TAG,"on create VH");
        ViewHolder holder = new ViewHolder(LayoutInflater.from(mContext)
                .inflate(resId, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        Option option= options.get(position);
        Log.d(TAG,"on bind VH");
        holder.tv.setText(option.getName());
        Glide.with(mContext).load(option.getImgUrl()).into(holder.iv);
    }

    @Override
    public int getItemCount()
    {
        return options.size();
    }

}
