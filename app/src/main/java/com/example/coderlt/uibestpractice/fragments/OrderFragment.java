package com.example.coderlt.uibestpractice.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coderlt.uibestpractice.R;

/**
 * Created by coderlt on 2018/3/20.
 */

public class OrderFragment extends Fragment {
    private View view;
    private Context mContext;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_order,container,false);
        mContext=getActivity();
        return view;
    }
}
