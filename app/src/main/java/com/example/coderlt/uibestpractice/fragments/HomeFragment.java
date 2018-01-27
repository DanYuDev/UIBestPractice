package com.example.coderlt.uibestpractice.fragments;

import android.content.Context;
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
import com.example.coderlt.uibestpractice.adapter.ScheduleAdapter;
import com.example.coderlt.uibestpractice.adapter.ScheduleArrayAdapter;
import com.example.coderlt.uibestpractice.bean.ScheduleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coderlt on 2018/1/7.
 */

public class HomeFragment extends Fragment {
    private View view;
    private Context mContext;
    private ListView scheduleListView;
    private ScheduleArrayAdapter scheduleArrayAdapter;
    private List<ScheduleBean> scheduleList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        mContext=getActivity();
        initViews();
        initScheduleListView();
        return view;
    }

    private void initViews(){
        scheduleListView=view.findViewById(R.id.schedule_listview);
    }

    private void initScheduleListView(){
        scheduleList=new ArrayList<>();
        for(int i=0;i<200;i++){
            ScheduleBean bean = new ScheduleBean();
            StringBuilder sb = new StringBuilder("");
            for(int j=0;j<i*20;j++){
                sb.append("*");
            }
            bean.setMsg(sb.toString());
            scheduleList.add(bean);
        }
        scheduleArrayAdapter=new ScheduleArrayAdapter(mContext,R.layout.schedule_item,scheduleList);
        scheduleListView.setAdapter(scheduleArrayAdapter);
    }
}
