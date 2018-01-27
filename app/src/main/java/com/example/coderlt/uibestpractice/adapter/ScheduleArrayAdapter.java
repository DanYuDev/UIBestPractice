package com.example.coderlt.uibestpractice.adapter;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.bean.ScheduleBean;

import java.util.List;

/**
 * Created by coderlt on 2018/1/27.
 */

public class ScheduleArrayAdapter extends ArrayAdapter<ScheduleBean> {
    private Context mContext;
    private int resId;
    private final String TAG=getClass().getSimpleName();
    private TextView tv;
    private View line;

    public ScheduleArrayAdapter(Context context, int resId, List<ScheduleBean> objects){
        super(context,resId,objects);
        this.resId=resId;
        mContext=context;
    }

    @Override
    public View getView(int position , View convertView, ViewGroup parent){
        ScheduleBean bean=getItem(position);
        View view= LayoutInflater.from(getContext()).inflate(resId,parent,false);


        tv=view.findViewById(R.id.node_tv);
        tv.setText(bean.getMsg());
        line=view.findViewById(R.id.vertical_line);
        ViewGroup.LayoutParams lp=line.getLayoutParams();

        final DisplayMetrics dm = mContext.getResources().getDisplayMetrics();
        view.measure(
                View.MeasureSpec.makeMeasureSpec(dm.widthPixels, View.MeasureSpec.EXACTLY),
                View.MeasureSpec.makeMeasureSpec(dm.heightPixels, View.MeasureSpec.UNSPECIFIED));

        final int width = view.getMeasuredWidth();
        final int height = view.getMeasuredHeight();
        Log.d(TAG,"Measured Height :"+height);
        lp.height=height;
        line.setLayoutParams(lp);

        return view;
    }
}
