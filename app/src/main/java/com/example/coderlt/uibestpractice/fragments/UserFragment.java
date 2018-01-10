package com.example.coderlt.uibestpractice.fragments;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.activity.NavigationActivity;
import com.example.coderlt.uibestpractice.activity.RegisterActivity;
import com.example.coderlt.uibestpractice.adapter.FuncRecyclerAdapter;
import com.example.coderlt.uibestpractice.bean.Option;
import com.example.coderlt.uibestpractice.utils.Constant;
import com.example.coderlt.uibestpractice.utils.JsonUtils;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by coderlt on 2018/1/7.
 */

public class UserFragment extends Fragment {

    private final String TAG=getClass().getName();
    private View view;
    private WebView webView;
    private RecyclerView funcRecycler;
    private List<Option> options;
    private FuncRecyclerAdapter funcAdapter;
    private OkHttpClient client;

    private static final int REQUEST_FUNC_SUCCESS=0;

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case REQUEST_FUNC_SUCCESS:
                    Log.d(TAG,"on msg received");
                    funcAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }

    private MyHandler mHandler=new MyHandler();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_user,container,false);
        options=new ArrayList<>();
        funcRecycler=view.findViewById(R.id.func_recycler_view);
        funcAdapter=new FuncRecyclerAdapter(options,R.layout.func_item,getActivity());
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),3);
        funcRecycler.setLayoutManager(layoutManager);
        funcRecycler.setAdapter(funcAdapter);
        requestUserInfo();

        webView=view.findViewById(R.id.user_webview);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("https://www.baidu.com/");
        return view;
    }

    private void requestUserInfo(){
        Request request=new Request.Builder()
                .url(Constant.CONFIG_URL)
                .build();
        client=new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String responseText;
                responseText=response.body().string();
                Log.d(TAG,"onResponse success, ResponseText is :"+responseText);
                JsonUtils.DealConfig(responseText,options);

                Message msg=new Message();
                msg.what=REQUEST_FUNC_SUCCESS;
                mHandler.sendMessage(msg);
            }
        });
    }
}
