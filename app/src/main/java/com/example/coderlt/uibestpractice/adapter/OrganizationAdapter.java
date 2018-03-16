package com.example.coderlt.uibestpractice.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.bean.Section;
import com.example.coderlt.uibestpractice.utils.Constant;

import java.util.List;

/**
 * Created by coderlt on 2018/3/15.
 */

public class OrganizationAdapter extends ArrayAdapter {
    private static final String TAG = "OrganizationAdapter";
    private Context mContext;
    private List<Section> sections;
    private int resId;

    public OrganizationAdapter(Context context, int resId, List<Section> sections){
        /**
         * TODO
         * 这里开始只写了 super(context,resId)
         * 无法读取数据，发现，为什么一定要把 sections 加进去呢？
         * confused
         */
        super(context,resId,sections);
        this.mContext = context;
        this.sections = sections;
        this.resId = resId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        View v;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(resId,parent,false);
        }
        v=convertView;
        TextView sectionTv = v.findViewById(R.id.section_tv);
        sectionTv.setText(sections.get(position).getSectionName());
        Log.d(TAG,"section name :"+sectionTv.getText().toString());
        return v;
    }
}
