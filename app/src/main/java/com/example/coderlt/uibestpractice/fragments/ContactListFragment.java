package com.example.coderlt.uibestpractice.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.utils.Utils;

/**
 * Created by coderlt on 2018/3/8.
 */

public class ContactListFragment extends Fragment {
    private View view;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contacts_list,container,false);
        mContext=getActivity();
        Utils.showToast("Fragment list is selected");
        return view;
    }
}
