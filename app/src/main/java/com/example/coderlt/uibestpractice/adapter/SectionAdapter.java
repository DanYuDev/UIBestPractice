package com.example.coderlt.uibestpractice.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.bean.Employee;
import com.example.coderlt.uibestpractice.bean.Section;
import com.example.coderlt.uibestpractice.interfaces.MyOnClickListener;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by coderlt on 2018/3/17.
 */

public class SectionAdapter extends ArrayAdapter {
    private static final String TAG = "SectionAdapter";
    private Context mContext;
    private List<Employee> employees;
    private int resId;

    public SectionAdapter(Context context, int resId, List<Employee> employees){
        /**
         * TODO
         * 这里开始只写了 super(context,resId)
         * 无法读取数据，发现，为什么一定要把 sections 加进去呢？
         * confused
         */
        super(context,resId,employees);
        this.mContext = context;
        this.employees = employees;
        this.resId = resId;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        View v;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(resId,parent,false);
        }
        v=convertView;
        ImageView employeePhotoIv = v.findViewById(R.id.identification_photo);
        TextView employeeNameTv = v.findViewById(R.id.employee_name_tv);
        TextView employeeGradeTv = v.findViewById(R.id.employee_grade_tv);
        TextView employeeAgeTv = v.findViewById(R.id.employee_age_tv);
        ImageView callIv = v.findViewById(R.id.call_iv);

        Employee employee = employees.get(position);
        employeeNameTv.setText("姓名: " +employee.getName());
        employeeGradeTv.setText("职务: "+employee.getJobGrade());
        employeeAgeTv.setText("年龄: "+employee.getAge()+"");
        Log.d(TAG,"Identification photo url :"+employee.getAvatarUrl());
        Glide.with(mContext).load(employee.getAvatarUrl()).centerCrop().into(employeePhotoIv);

        callIv.setOnClickListener(MyOnClickListener.getInstance());
        callIv.setTag(employee.getPhoneNumber());
        return v;
    }
}
