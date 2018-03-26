package com.example.coderlt.uibestpractice.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.coderlt.uibestpractice.MyApplication;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.activity.NavigationActivity;
import com.example.coderlt.uibestpractice.utils.Utils;

import cn.leancloud.chatkit.activity.LCIMConversationActivity;
import cn.leancloud.chatkit.activity.LCIMConversationListFragment;
import cn.leancloud.chatkit.utils.LCIMConstants;

/**
 * Created by coderlt on 2018/1/7.
 */

/**
 * Questions to be solved.
 * 1. 嵌套Fragment貌似会造成一定的卡顿
 * 2. 关于默认加载TabLayout 第一次不回调 onTabSelected 的问题
 */

public class ContactsFragment extends Fragment {
    private View view;
    private TabLayout tabLayout;
    private FrameLayout contentFrame;
    private FragmentManager fm;
    private Fragment conversationListFragment,contactListFragment;
    private String clientId;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_contacts,container,false);
        tabLayout = view.findViewById(R.id.contacts_tab_layout);
        contentFrame = view.findViewById(R.id.content_frame);
        fm = getActivity().getSupportFragmentManager();

        conversationListFragment = new LCIMConversationListFragment();
        contactListFragment = new ContactListFragment();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if(tab.getPosition()==0){
                    fm.beginTransaction()
                            .replace(R.id.content_frame,conversationListFragment)
                            .commit();
                    Utils.showToast("Tab(0) is selected.");
                    //EventBus.getDefault().post(new LCIMIMTypeMessageEvent());
                }else{
                    fm.beginTransaction()
                            .replace(R.id.content_frame,contactListFragment)
                            .commit();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        // tab 的监听必须写在 addtab 前面，才能保证第一次tab被选中执行回调函数
        tabLayout.addTab(tabLayout.newTab().setText("聊天"));
        tabLayout.addTab(tabLayout.newTab().setText("通讯录"));
        tabLayout.getTabAt(0).select();
        TextView titleTv = view.findViewById(R.id.title_tv);
        //clientId = ((NavigationActivity)(getActivity())).clientId ;
        clientId = MyApplication.clientId;
        titleTv.setText(clientId);
        ImageView more = view.findViewById(R.id.title_right);
        more.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent intent = new Intent(getContext(), LCIMConversationActivity.class);
                // 传入对方的 Id 即可
                intent.putExtra(LCIMConstants.PEER_ID, "William");
                getContext().startActivity(intent);
            }
        });
        return view;


    }
}
