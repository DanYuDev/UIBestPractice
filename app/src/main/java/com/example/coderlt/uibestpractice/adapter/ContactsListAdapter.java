package com.example.coderlt.uibestpractice.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.util.List;

import cn.leancloud.chatkit.LCChatKitUser;

/**
 * Created by coderlt on 2018/3/9.
 */

public class ContactsListAdapter extends ArrayAdapter {
    private Context mContext;
    private int resId;
    private List<LCChatKitUser> users;

    public ContactsListAdapter(Context context, int resId, List<LCChatKitUser> dataList){
        super(context,resId,dataList);
        mContext = context;
        this.resId = resId;
        this.users = dataList;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v;
        final LCChatKitUser user = users.get(position);
        LayoutInflater inflater = LayoutInflater.from(mContext);
        if(convertView==null){
            v=inflater.inflate(resId,parent,false);
        }else{
            v=convertView;
        }
        ImageView userAvatar = v.findViewById(R.id.user_avatar);
        TextView  userName = v.findViewById(R.id.user_name);
        Glide.with(mContext).load(user.getAvatarUrl()).into(userAvatar);
        userName.setText(user.getUserName());
        /*v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.showToast("talk with "+user.getUserName());
            }
        });*/
        return v;
    }
}
