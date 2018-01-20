package com.example.coderlt.uibestpractice.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.Spinner;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.View.LineGraphFinal;
import com.example.coderlt.uibestpractice.adapter.TableAdapter;
import com.example.coderlt.uibestpractice.bean.TableData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by coderlt on 2018/1/7.
 */

public class DashboardFragment extends Fragment implements View.OnClickListener{
    private final String TAG=getClass().getSimpleName();
    private Context mContext;
    private float[] dataSet={58.3f,73.9f,68.7f,90f,23.8f,57f,73.8f};
    private String[] spinnerArray={"年","月","日"};
    private ArrayAdapter<String> adapter;

    //-----------------控件-----------------------------------
    private Spinner spinner;
    private PopupWindow moreWnd;
    private ImageView moreIv;
    private ImageView backIv;
    private View popView;

    //----------------表格组件--------------------------------
    private RecyclerView overRecycler,vipRecycler,detailRecycler;
    private List<TableData> overList,vipList,detailList;
    private TableAdapter overAdapter,vipAdapter;
    private RecyclerView.LayoutManager layoutManager;


    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext=getActivity();
        view = inflater.inflate(R.layout.fragment_dashboard,container,false);
        LineGraphFinal lineGraph=view.findViewById(R.id.line_graph);
        lineGraph.setDataSet(dataSet);
        initViews();
        setSpinner();
        setOverRecycler();
        setPopwnd();
        return view;
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.more_iv:
                moreWnd.showAtLocation(view, Gravity.BOTTOM|Gravity.CENTER,0,0);
                popView.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        moreWnd.dismiss();
                    }
                },2000);
                break;
            case R.id.title_left:
                getActivity().finish();
                break;
        }
    }

    private void initViews(){
        moreIv=view.findViewById(R.id.more_iv);
        backIv=view.findViewById(R.id.title_left);

        moreIv.setOnClickListener(this);
        backIv.setOnClickListener(this);
    }
    private void setPopwnd(){
        popView=LayoutInflater.from(mContext).inflate(R.layout.pop_wnd_layout,null);
        moreWnd=new PopupWindow(popView,ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        //moreWnd.setClippingEnabled(false);
        moreWnd.setAnimationStyle(R.style.popwin_anim_style);
    }

    private void setSpinner(){
        adapter=new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner=view.findViewById(R.id.spinner);
        spinner.setAdapter(adapter);
    }

    private void setOverRecycler(){
        initOverList();
        overRecycler=view.findViewById(R.id.overview_recycler);
        overAdapter=new TableAdapter(overList,R.layout.form_item,mContext);
        layoutManager=new GridLayoutManager(mContext,4);
        overRecycler.setLayoutManager(layoutManager);
//        overRecycler.addItemDecoration(
//                new DividerItemDecoration(mContext, DividerItemDecoration.VERTICAL));
        View header=LayoutInflater.from(mContext).inflate(R.layout.recycler_header,null);

        overRecycler.setAdapter(overAdapter);
    }

    private void initOverList(){
        TableData data1=new TableData("营业收入",68.23f);
        TableData data2=new TableData("售卡收入",34.65f);
        TableData data3=new TableData("订单数  ",132.0f);
        TableData data4=new TableData("总客次  ",876.0f);

        overList=new ArrayList<>();
        overList.add(data1);
        overList.add(data2);
        overList.add(data3);
        overList.add(data4);
    }
}
