package com.example.coderlt.uibestpractice.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.bean.UsedRoom;

import java.util.List;

/**
 * Created by coderlt on 2018/4/4.
 */

public class RoomCyclerAdapter extends RecyclerView.Adapter {

    public static enum ITEM_TYPE {
        ITEM_TYPE_ROOM,
        ITEM_TYPE_MORE
    }

    private final LayoutInflater mLayoutInflater;
    private final Context mContext;
    private List<UsedRoom> usedRoomList;
    private OnItemClickedListener listener = null;

    public RoomCyclerAdapter(Context context,List usedRoomList) {
        mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.usedRoomList = usedRoomList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == ITEM_TYPE.ITEM_TYPE_ROOM.ordinal()) {
            return new RoomViewHolder(mLayoutInflater.inflate(R.layout.room_item, parent, false));
        } else {
            return new MoreViewHolder(mLayoutInflater.inflate(R.layout.room_more_item, parent, false));
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        if (holder instanceof RoomViewHolder) {
            ((RoomViewHolder) holder).nameTv.setText(usedRoomList.get(position).getRoom().getName());
            ((RoomViewHolder) holder).v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                    listener.onItemClicked(position);
                }
            });
        } else if (holder instanceof MoreViewHolder) {
            ((MoreViewHolder) holder).v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener!=null)
                    listener.onItemClicked(position);
                }
            });
        }
    }

    @Override
    public int getItemViewType(int position) {
        return usedRoomList.get(position).getItem_type().ordinal();
    }

    @Override
    public int getItemCount() {
        return usedRoomList.size();
    }

    public static class RoomViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        TextView clockTv;
        View v;

        RoomViewHolder(View view) {
            super(view);
            nameTv = view.findViewById(R.id.room_name_tv);
            clockTv = view.findViewById(R.id.clock_tv);
            v=view;
        }
    }

    public static class MoreViewHolder extends RecyclerView.ViewHolder {
        View v;
        MoreViewHolder(View view) {
            super(view);
            v=view;
        }
    }

    public void setOnItemClickedListener(OnItemClickedListener listener){
        this.listener = listener;
    }

    public interface OnItemClickedListener{
        void onItemClicked(int position);
    }
}
