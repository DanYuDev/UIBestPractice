package com.example.coderlt.uibestpractice.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridView;
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

public class DashboardFragment extends Fragment {
    private final String TAG=getClass().getSimpleName();
    private Context mContext;
    private float[] dataSet={58.3f,73.9f,68.7f,90f,23.8f,57f,73.8f};
    private String[] spinnerArray={"年","月","日"};
    private ArrayAdapter<String> adapter;
    private Spinner spinner;
    private PopupWindow timeWnd;

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
        setSpinner();
        setOverRecycler();
        return view;
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
