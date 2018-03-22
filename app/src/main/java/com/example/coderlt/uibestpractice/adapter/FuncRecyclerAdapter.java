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
import com.example.coderlt.uibestpractice.utils.Utils;

import java.util.List;

/**
 * Created by coderlt on 2018/1/10.
 */

public class FuncRecyclerAdapter extends RecyclerView.Adapter<FuncRecyclerAdapter.ViewHolder> {
    private final String TAG=getClass().getName();
    private int resId;
    private List<Option> options;
    private Context mContext;
    private OnItemClickedListener listener;

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView iv;
        public TextView tv;
        public View v;

        public ViewHolder(View v){
            super(v);
            this.v = v;
            iv=v.findViewById(R.id.func_iv);
            tv=v.findViewById(R.id.func_tv);
        }
    }

    public FuncRecyclerAdapter(List<Option> optionList, int resId,Context context,OnItemClickedListener listener){
        Log.d(TAG,"on adapter constructor ");
        options=optionList;
        this.resId=resId;
        mContext=context;
        this.listener = listener;
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
    public void onBindViewHolder(ViewHolder holder, final int position)
    {
        Option option= options.get(position);
        Log.d(TAG,"on bind VH ");
        holder.tv.setText(option.getName());
        Glide.with(mContext).load(option.getImgUrl()).into(holder.iv);
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(listener!=null){
                    listener.onItemClicked(position);
                }
            }
        });
    }

    @Override
    public int getItemCount()
    {
        return options.size();
    }

    public interface OnItemClickedListener{
        void onItemClicked(int position);
    }

}
