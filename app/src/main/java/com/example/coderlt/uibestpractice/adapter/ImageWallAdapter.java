package com.example.coderlt.uibestpractice.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.activity.SalesDetailActivity;
import com.example.coderlt.uibestpractice.bean.ImageInfo;

import java.io.File;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * Created by coderlt on 2018/2/9.
 */

public class ImageWallAdapter extends RecyclerView.Adapter <ImageWallAdapter.ViewHolder>{
    private static final int BIND_BITMAP = 0;
    private Context mContext;
    private int resId;
    private List<ImageInfo> images;
    private boolean isScrolling = false;
    private ExecutorService executor = Executors.newFixedThreadPool(10);

    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        public ViewHolder(View v){
            super(v);
            iv = v.findViewById(R.id.square_iv);
        }
    }

    public ImageWallAdapter(Context context,int resId ,List<ImageInfo> images){
        mContext =context;
        this.resId = resId;
        this.images = images;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,int viewType){
        View v;
        v= LayoutInflater.from(mContext).inflate(resId,null);

        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position){
        if(isScrolling)return;
        final ImageInfo imageInfo = images.get(position);
        final BitmapFactory.Options options =new BitmapFactory.Options();

        // 应该利用线程池异步加载的
        Glide.with(mContext)
                .load(imageInfo.getPath())
                .override(200,200)
                .centerCrop()
                .skipMemoryCache(false)
                .placeholder(R.drawable.place_holder)
                .into(viewHolder.iv);
    }

    public void setIsScrolling(boolean b){
        isScrolling = b;
    }

    @Override
    public int getItemCount(){
        return images.size();
    }
}
