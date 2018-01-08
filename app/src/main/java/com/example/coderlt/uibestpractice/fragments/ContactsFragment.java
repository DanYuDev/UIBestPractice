package com.example.coderlt.uibestpractice.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.coderlt.uibestpractice.R;

/**
 * Created by coderlt on 2018/1/7.
 */

public class ContactsFragment extends Fragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contacts,container,false);
        return view;
    }
}
