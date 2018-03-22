package com.example.coderlt.uibestpractice.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.coderlt.uibestpractice.R;

/**
 * Created by coderlt on 2018/3/22.
 */

public class BillDetailFragment extends Fragment {
    private ListView detailListView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = null;
        view = inflater.inflate(R.layout.fragment_bill_detail,container,false);
        return view;
    }
}
