package com.example.coderlt.uibestpractice.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.activity.SalesDetailActivity;
import com.example.coderlt.uibestpractice.bean.ImageInfo;
import com.example.coderlt.uibestpractice.utils.Utils;

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
    private OnImageSelectedListener listener = null;
    public static class ViewHolder extends RecyclerView.ViewHolder{
        ImageView iv;
        ImageView checkIv;
        public ViewHolder(View v){
            super(v);
            iv = v.findViewById(R.id.square_iv);
            checkIv = v.findViewById(R.id.check_iv);
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
        // 滑动时不加载
        if(isScrolling)return;
        final ImageInfo imageInfo = images.get(position);
        final BitmapFactory.Options options =new BitmapFactory.Options();
        final ImageView imageView = viewHolder.iv;
        final ImageView checkIv = viewHolder.checkIv;
        final String imagePath = imageInfo.getPath();

        // 应该利用线程池异步加载的

        Glide.with(mContext)
                .load(imagePath)
                .override(200,200)
                .centerCrop()
                .skipMemoryCache(true)
                .placeholder(R.drawable.place_holder)
                .into(viewHolder.iv);
        if(imageInfo.isChecked()){
            checkIv.setImageResource(R.drawable.ic_check);
        }else{
            checkIv.setImageResource(R.drawable.ic_uncheck);
        }
        checkIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageInfo.isChecked()){
                    imageInfo.setChecked(false);
                    checkIv.setImageResource(R.drawable.ic_uncheck);
                    if(listener!=null)listener.unSelected(checkIv);
                }else{
                    imageInfo.setChecked(true);
                    checkIv.setImageResource(R.drawable.ic_check);
                    if (listener!=null)listener.selected(checkIv);
                }
            }
        });

        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                if(listener!=null)
                listener.preview(imageView,imagePath);
            }
        });
    }

    public void setIsScrolling(boolean b){
        isScrolling = b;
    }

    @Override
    public int getItemCount(){
        return images.size();
    }

    public interface OnImageSelectedListener{
        void selected(ImageView v);
        void unSelected(ImageView v);
        void preview(ImageView imageView,String path);
    }

    public void setOnImageSelectedListener(OnImageSelectedListener listener){
        this.listener = listener;
    }
}
