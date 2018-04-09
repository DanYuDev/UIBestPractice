package com.example.coderlt.uibestpractice.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.View.EditSpinnerText;
import com.example.coderlt.uibestpractice.View.MyDialog;
import com.example.coderlt.uibestpractice.activity.IntroductionActivity;
import com.example.coderlt.uibestpractice.adapter.RoomCyclerAdapter;
import com.example.coderlt.uibestpractice.bean.Room;
import com.example.coderlt.uibestpractice.bean.UsedRoom;
import com.example.coderlt.uibestpractice.utils.DrawableUtils;
import com.example.coderlt.uibestpractice.utils.Utils;
import com.yzq.zxinglibrary.android.CaptureActivity;
import com.yzq.zxinglibrary.common.Constant;
import java.util.ArrayList;
import java.util.List;
import static android.app.Activity.RESULT_OK;

/**
 * Created by coderlt on 2018/3/20.
 */

public class ManagementFragment extends Fragment implements View.OnClickListener{
    private int REQUEST_CODE_SCAN = 0x111;
    private View view;
    private Context mContext;
    private RecyclerView roomCycler;
    private RoomCyclerAdapter adapter;
    private RecyclerView.LayoutManager layoutManager;
    private List<UsedRoom> usedRoomList;
    private MyDialog mDialog;
    private ImageView titleRight;
    private EditSpinnerText sortEt,typeEt;

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
        titleRight = view.findViewById(R.id.title_right);
        sortEt = view.findViewById(R.id.sort_filter_spinnertv);
        typeEt = view.findViewById(R.id.type_filter_spinnertv);

        titleRight.setOnClickListener(this);

        titleRight.setImageResource(R.drawable.ic_qa);
        layoutManager = new GridLayoutManager(mContext,3);
        adapter = new RoomCyclerAdapter(mContext,usedRoomList);
        roomCycler.setLayoutManager(layoutManager);
        roomCycler.setAdapter(adapter);

        DrawableUtils.setRightDrawableBounds(sortEt,30,30);
        DrawableUtils.setRightDrawableBounds(typeEt,30,30);

        adapter.setOnItemClickedListener(new RoomCyclerAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                if(position==(usedRoomList.size()-1)){
                    Utils.showToast("添加房间");
                    Room room = new Room("8310",10,"足疗房");
                    UsedRoom usedRoom = new UsedRoom();
                    usedRoom.setRoom(room);
                    usedRoom.setItem_type(RoomCyclerAdapter.ITEM_TYPE.ITEM_TYPE_ROOM);
                    usedRoomList.add(position-1,usedRoom);
                    adapter.notifyDataSetChanged();
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
        usedRoomList = new ArrayList<>();
        Room room = new Room("8310",10,"足疗房");
        UsedRoom usedRoom = new UsedRoom();
        usedRoom.setRoom(room);
        usedRoom.setItem_type(RoomCyclerAdapter.ITEM_TYPE.ITEM_TYPE_ROOM);
        usedRoomList.add(usedRoom);
        usedRoom = new UsedRoom();
        usedRoom.setItem_type(RoomCyclerAdapter.ITEM_TYPE.ITEM_TYPE_MORE);
        usedRoomList.add(usedRoom);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.scan_pay:
                Utils.showToast("扫码结账");
                mDialog.dismiss();
                Intent intent = new Intent(mContext, CaptureActivity.class);
                //intent.putExtra(Constant.INTENT_ZXING_CONFIG, config);
                startActivityForResult(intent, REQUEST_CODE_SCAN);
                break;
            case R.id.title_right:
                startActivity(new Intent(mContext,IntroductionActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // 扫描二维码/条码回传
        if (requestCode == REQUEST_CODE_SCAN && resultCode == RESULT_OK) {
            if (data != null) {

                String content = data.getStringExtra(Constant.CODED_CONTENT);
                Utils.showToast("Scan result:\n"+content);
            }
        }
    }
}
