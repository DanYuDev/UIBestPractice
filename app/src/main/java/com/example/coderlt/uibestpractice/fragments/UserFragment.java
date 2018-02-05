package com.example.coderlt.uibestpractice.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.View.RentalsSunHeaderView;
import com.example.coderlt.uibestpractice.activity.NavigationActivity;
import com.example.coderlt.uibestpractice.activity.RegisterActivity;
import com.example.coderlt.uibestpractice.adapter.FuncRecyclerAdapter;
import com.example.coderlt.uibestpractice.bean.Option;
import com.example.coderlt.uibestpractice.utils.Constant;
import com.example.coderlt.uibestpractice.utils.FileUtil;
import com.example.coderlt.uibestpractice.utils.JsonUtils;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by coderlt on 2018/1/7.
 */

public class UserFragment extends Fragment {
    private Context mContext;
    private final String TAG=getClass().getName();
    private View view;
    private WebView webView;
    private RecyclerView funcRecycler;
    private List<Option> options;
    private FuncRecyclerAdapter funcAdapter;
    private String cacheFilePath;
    private File cacheFile;
    private OkHttpClient client;
    private PtrFrameLayout ptrFrameLayout;

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
        mContext=getActivity();
        view = inflater.inflate(R.layout.fragment_user,container,false);
        funcRecycler=view.findViewById(R.id.func_recycler_view);

        //----------- check if cache options exist ---------------------------------
        cacheFile = FileUtil.getDiskCacheDir(mContext,"options");
        if(!cacheFile.exists()){
            try{
                cacheFile.createNewFile();
            }catch (IOException ex){
                ex.printStackTrace();
            }
        }
        Log.d(TAG,"cacheFile isn't exist.");
        cacheFilePath = cacheFile.getPath();
        options = (List<Option>) FileUtil.loadObjectFromFile(cacheFilePath);
        Log.d(TAG,"Options form file: "+options);
        if(options == null){
            Log.d(TAG,"Options is null .");
            options = new ArrayList<>();
            requestUserInfo();
        }
        //-------------------------------------------------------------------------

        funcAdapter=new FuncRecyclerAdapter(options,R.layout.func_item,getActivity());
        GridLayoutManager layoutManager=new GridLayoutManager(getActivity(),3);
        funcRecycler.setLayoutManager(layoutManager);
        funcRecycler.setAdapter(funcAdapter);


        //--------------------  set refresh layout -----------------------------------
        ptrFrameLayout=view.findViewById(R.id.ptr_frame);
        RentalsSunHeaderView header=new RentalsSunHeaderView(mContext);
        header.setUp(ptrFrameLayout);
        ptrFrameLayout.addPtrUIHandler(header);
        ptrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return true;
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
                requestUserInfo();
                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        frame.refreshComplete();
                    }
                },2000);
            }
        });
        ptrFrameLayout.setHeaderView(header);
        //--------------------------------------------------------------------------------
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

                //String path = FileUtil.getDiskCacheDir(mContext,"options").getPath();
                FileUtil.saveObjectToFile(options,cacheFilePath);

                Message msg=new Message();
                msg.what=REQUEST_FUNC_SUCCESS;
                mHandler.sendMessage(msg);
            }
        });
    }

}
