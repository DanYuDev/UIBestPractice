package com.example.coderlt.uibestpractice.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCreatedCallback;
import com.avos.avoscloud.im.v2.messages.AVIMTextMessage;
import com.example.coderlt.uibestpractice.CustomUserProvider;
import com.example.coderlt.uibestpractice.MyApplication;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.adapter.ContactsListAdapter;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.util.Arrays;
import java.util.List;
import cn.leancloud.chatkit.LCChatKitUser;
import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.utils.LCIMConstants;

/**
 * Created by coderlt on 2018/3/8.
 */

public class ContactListFragment extends Fragment {
    private View view;
    private Context mContext;
    private ListView contactsListView;
    private ContactsListAdapter adapter;
    private List<LCChatKitUser> users;
    private AVIMClient client;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contacts_list,container,false);
        mContext=getActivity();
        //Utils.showToast("Fragment list is selected");
        initListView();
        return view;
    }

    private void initListView(){
        contactsListView = view.findViewById(R.id.contacts_list_view);
        users = CustomUserProvider.getInstance().getAllUsers();
        adapter = new ContactsListAdapter(mContext,R.layout.contacts_list_item,
                users);
        contactsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                final LCChatKitUser user = users.get(position);
                client = AVIMClient.getInstance(MyApplication.clientId);
                client.open(new AVIMClientCallback() {
                    @Override
                    public void done(AVIMClient avimClient, AVIMException e) {
                        if(null == e){
                            avimClient.createConversation(Arrays.asList(user.getUserName()),
                                    MyApplication.clientId+"with"+user.getUserName(),null,
                                    new AVIMConversationCreatedCallback(){
                                        @Override
                                        public void done(AVIMConversation conversation,AVIMException e){
                                            if(e==null){
                                                Intent intent = new Intent(mContext, LCIMConversationActivity.class);
                                                intent.putExtra(LCIMConstants.PEER_ID, user.getUserId());
                                                getContext().startActivity(intent);
                                            }
                                        }
                                    });
                        }
                    }
                });
            }
        });
        contactsListView.setAdapter(adapter);

    }
}
