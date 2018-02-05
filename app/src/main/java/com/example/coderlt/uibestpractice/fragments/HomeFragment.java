package com.example.coderlt.uibestpractice.fragments;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.View.MyImgButton;
import com.example.coderlt.uibestpractice.activity.SalesDetailActivity;
import com.example.coderlt.uibestpractice.adapter.ScheduleAdapter;
import com.example.coderlt.uibestpractice.adapter.ScheduleArrayAdapter;
import com.example.coderlt.uibestpractice.bean.ScheduleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coderlt on 2018/1/7.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{
    private View view;
    private Context mContext;
    private MyImgButton salesButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        mContext=getActivity();
        logics();
        return view;
    }

    private void logics(){
        initViews();
    }

    private void initViews(){
        salesButton = view.findViewById(R.id.sales_img_btn);

        salesButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.sales_img_btn:
                startActivity(new Intent(mContext, SalesDetailActivity.class));
                break;
            default:
                break;
        }
    }
}
