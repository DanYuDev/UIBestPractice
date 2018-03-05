package com.example.coderlt.uibestpractice.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.activity.MainActivity;
import com.example.coderlt.uibestpractice.utils.Utils;

import cn.leancloud.chatkit.LCChatKit;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;

/**
 * Created by coderlt on 2018/1/7.
 */

public class ContactsFragment extends Fragment {
    private View view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contacts,container,false);
        ImageView more = view.findViewById(R.id.more_iv);
        more.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getContext(), LCIMConversationActivity.class);
                // 传入对方的 Id 即可
                intent.putExtra(LCIMConstants.PEER_ID, "Bob");
                getContext().startActivity(intent);
            }
        });
        return view;


    }
}
