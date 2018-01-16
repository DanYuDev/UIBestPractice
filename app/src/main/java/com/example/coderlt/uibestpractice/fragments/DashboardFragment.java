package com.example.coderlt.uibestpractice.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.View.LineGraphFinal;

/**
 * Created by coderlt on 2018/1/7.
 */

public class DashboardFragment extends Fragment {
    private float[] dataSet={58.3f,73.9f,68.7f,90f,23.8f,57f,73.8f};
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_dashboard,container,false);
        LineGraphFinal lineGraph=view.findViewById(R.id.line_graph);
        lineGraph.setDataSet(dataSet);
        return view;
    }
}
