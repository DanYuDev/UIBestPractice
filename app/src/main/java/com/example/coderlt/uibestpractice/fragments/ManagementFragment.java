package com.example.coderlt.uibestpractice.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.View.MyDialog;
import com.example.coderlt.uibestpractice.adapter.RoomCyclerAdapter;
import com.example.coderlt.uibestpractice.bean.Room;
import com.example.coderlt.uibestpractice.bean.UsedRoom;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coderlt on 2018/3/20.
 */

public class ManagementFragment extends Fragment implements View.OnClickListener{
    private View view;
    private Context mContext;
    private RecyclerView roomCycler;
    private RoomCyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<UsedRoom> usedRoomList;
    private MyDialog mDialog;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_management,container,false);
        mContext=getActivity();
        generateData();
        initViews(view);
        return view;
    }

    private void initViews(View view){
        roomCycler = view.findViewById(R.id.room_cycler_view);

        layoutManager = new GridLayoutManager(mContext,3);
        adapter = new RoomCyclerAdapter(mContext,usedRoomList);
        roomCycler.setLayoutManager(layoutManager);
        roomCycler.setAdapter(adapter);

        adapter.setOnItemClickedListener(new RoomCyclerAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                if(position==(usedRoomList.size()-1)){
                    Utils.showToast("添加房间");
                }else{
                    mDialog = new MyDialog(mContext,R.style.Dialog);
                    View v = LayoutInflater.from(mContext).inflate(R.layout.dialog_kfc_operation,null);
                    TextView scanPay = v.findViewById(R.id.scan_pay);
                    scanPay.setOnClickListener(ManagementFragment.this);

                    mDialog.setMyView(v);
                    mDialog.show();
                }
            }
        });
    }

    private void generateData(){
        Room room = new Room("8310",10,"足疗房");
        UsedRoom usedRoom = new UsedRoom();
        usedRoom.setRoom(room);
        usedRoom.setItem_type(RoomCyclerAdapter.ITEM_TYPE.ITEM_TYPE_ROOM);

        UsedRoom usedRoom1 = new UsedRoom();
        usedRoom1.setItem_type(RoomCyclerAdapter.ITEM_TYPE.ITEM_TYPE_MORE);

        usedRoomList = new ArrayList<>();
        usedRoomList.add(usedRoom);
        usedRoomList.add(usedRoom1);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.scan_pay:
                Utils.showToast("扫码结账");
                mDialog.dismiss();
                break;
            default:
                break;
        }
    }
}
