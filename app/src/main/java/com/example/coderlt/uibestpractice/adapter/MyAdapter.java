package com.example.coderlt.uibestpractice.adapter;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coderlt.uibestpractice.bean.Msg;
import com.example.coderlt.uibestpractice.R;

import java.io.IOException;
import java.util.List;

/**
 * Created by coderlt on 2017/12/28.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder>{
    private List<Msg> mMsgList;
    Bitmap leftBitmap,rightBitmap;
    private MediaPlayer player;

    static class ViewHolder extends RecyclerView.ViewHolder{
        View v;
        TextView  msgTextLeft;
        TextView  msgTextRight;
        ViewGroup layoutLeft,layoutRight;

        public ViewHolder(View view){
            super(view);
            msgTextLeft = (TextView)view.findViewById(R.id.left_tv);
            msgTextRight=(TextView)view.findViewById(R.id.right_tv);
            layoutLeft=(ViewGroup) view.findViewById(R.id.layout_left);
            layoutRight=(ViewGroup) view.findViewById(R.id.layout_right);
            v=view;
        }
    }
    public MyAdapter(List<Msg> msgList){
        leftBitmap=BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.message_left);
        rightBitmap=BitmapFactory.decodeResource(Resources.getSystem(),R.drawable.message_right);
        mMsgList=msgList;
    }

    //----------------------------------------重写这三个方法---------------------------------------------
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
        // 感觉这里设计不合理，应该由用户传layout过来
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item,parent,false);
        ViewHolder holder=new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder,int position){
        final Msg msg=mMsgList.get(position);
        if(msg.getType()==Msg.OTHER){
            holder.layoutRight.setBackground(null);
            holder.msgTextRight.setText(null);
            holder.layoutLeft.setBackgroundResource(R.drawable.message_left);
            holder.msgTextLeft.setText(msg.getMsg());
        }else if(msg.getType()==Msg.MINE){
            holder.layoutLeft.setBackground(null);
            holder.msgTextLeft.setText(null);
            holder.layoutRight.setBackgroundResource(R.drawable.message_right);
            holder.msgTextRight.setText(msg.getMsg());
        }else{
            holder.layoutLeft.setBackground(null);
            holder.msgTextLeft.setText(null);
            holder.layoutRight.setBackgroundResource(R.drawable.message_right);
            holder.msgTextRight.setText("点击播放语音");
        }
        holder.v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(msg.getType()==Msg.VOICE){
                    player=new MediaPlayer();
                    try{
                        player.setDataSource(msg.getMsg());
                        player.prepare();
                        player.start();
                    }catch (IOException ex){
                        ex.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public int getItemCount(){
        return mMsgList.size();
    }
    //--------------------------------------------------------------------------------------------------
}

