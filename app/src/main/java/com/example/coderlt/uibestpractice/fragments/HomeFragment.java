package com.example.coderlt.uibestpractice.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.View.MyDialog;
import com.example.coderlt.uibestpractice.View.MyImgButton;
import com.example.coderlt.uibestpractice.activity.ApprovalActivity;
import com.example.coderlt.uibestpractice.activity.FeedbackActivity;
import com.example.coderlt.uibestpractice.activity.HonourActivity;
import com.example.coderlt.uibestpractice.activity.NewShopActivity;
import com.example.coderlt.uibestpractice.activity.PersonalBillActivity;
import com.example.coderlt.uibestpractice.activity.PublishTaskActivity;
import com.example.coderlt.uibestpractice.activity.RuleActivity;
import com.example.coderlt.uibestpractice.activity.ScheduleActivity;
import com.example.coderlt.uibestpractice.activity.SchemeActivity;
import com.example.coderlt.uibestpractice.activity.SectionTrainingActivity;
import com.example.coderlt.uibestpractice.activity.ShopActivity;
import com.example.coderlt.uibestpractice.activity.ShopOrdersActivity;
import com.example.coderlt.uibestpractice.adapter.FuncRecyclerAdapter;
import com.example.coderlt.uibestpractice.bean.Option;
import com.example.coderlt.uibestpractice.bean.ShopInfo;
import com.example.coderlt.uibestpractice.utils.Constant;
import com.example.coderlt.uibestpractice.utils.FileUtil;
import com.example.coderlt.uibestpractice.utils.JsonUtils;
import com.example.coderlt.uibestpractice.utils.Utils;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by coderlt on 2018/1/7.
 */

public class HomeFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "HomeFragment";
    private SharedPreferences preferences;
    private TextView titleTv;
    private View view;
    private Context mContext;
    private TextView shopNameTv;
    private TextView shopManagerTv;
    private RelativeLayout shopLayout;
    private MyImgButton ruleBtn,honourBtn,feedbackBtn;
    private MyImgButton newShopBtn,switchBtn;
    // 店铺选择对话框
    private MyDialog mDialog;
    private ListView shopListView;
    private List<String> shopNameList;
    private List<ShopInfo> shopList;
    private List<Option> options;
    private RecyclerView optionCycler;
    private FuncRecyclerAdapter funcAdapter;
    private File cacheFile;
    private String cacheFilePath;
    private OkHttpClient client;
    private static final int REQUEST_FUNC_SUCCESS = 0;
    private static final int REQUEST_FUNC_FAILED  = 1;

    private class MyHandler extends Handler {
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case REQUEST_FUNC_SUCCESS:
                    Log.d(TAG,"on msg received");
                    funcAdapter.notifyDataSetChanged();
                    break;
                case REQUEST_FUNC_FAILED:
                    funcAdapter.notifyDataSetChanged();
                    break;
                default:
                    break;
            }
        }
    }

    private MyHandler mHandler=new MyHandler();

    /**
     * IO operation .
     */
    private Runnable readCacheRunnable = new Runnable() {
        @Override
        public void run() {
            cacheFile = FileUtil.getDiskCacheDir(mContext,"homeOptions");
            List<Option> temps;
            if(!cacheFile.exists()){
                try{
                    cacheFile.createNewFile();
                }catch (IOException ex){
                    ex.printStackTrace();
                }
            }
            Log.d(TAG,"cacheFile isn't exist.");
            cacheFilePath = cacheFile.getPath();
            temps = (List<Option>) FileUtil.loadObjectFromFile(cacheFilePath);
            if(temps==null){
                Log.d(TAG,"Options is null .");
                requestUserInfo();
            }else{
                options.addAll(temps);
                //funcAdapter.notifyDataSetChanged();
                Message msg = new Message();
                msg.what = REQUEST_FUNC_SUCCESS;
                mHandler.sendMessage(msg);
            }
        }
    };

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home,container,false);
        mContext=getActivity();
        logics();
        return view;
    }

    private void logics(){
        preferences = mContext.getSharedPreferences(Constant.USER_PREF_NAME,Context.MODE_PRIVATE);
        options = new ArrayList<>();
        initViews();
        new Thread(readCacheRunnable).start();
    }

    private void initViews(){
        // find Views
        titleTv = view.findViewById(R.id.title_tv);
        shopLayout = view.findViewById(R.id.shop_title);
        shopNameTv = view.findViewById(R.id.shop_name);
        shopManagerTv = view.findViewById(R.id.shop_manager);
        optionCycler = view.findViewById(R.id.option_cycler);
        ruleBtn = view.findViewById(R.id.rule_btn);
        honourBtn = view.findViewById(R.id.honour_btn);
        feedbackBtn = view.findViewById(R.id.feedback_btn);
        newShopBtn = view .findViewById(R.id.new_shop_btn);
        switchBtn = view.findViewById(R.id.switch_btn);

        // set listener
        shopLayout.setOnClickListener(this);
        shopNameTv.setText(preferences.getString(Constant.SHOP.SHOP_ID,"暂未绑定"));
        ruleBtn.setOnClickListener(this);
        honourBtn.setOnClickListener(this);
        feedbackBtn.setOnClickListener(this);
        newShopBtn.setOnClickListener(this);
        switchBtn.setOnClickListener(this);
        funcAdapter = new FuncRecyclerAdapter(options, R.layout.func_item, mContext, new FuncRecyclerAdapter.OnItemClickedListener() {
            @Override
            public void onItemClicked(int position) {
                //Utils.showToast(options.get(position).getName());
                switch (options.get(position).getName()){
                    case "传达任务":
                        startActivity(new Intent(mContext, PublishTaskActivity.class));
                        break;
                    case "店铺订单":
                        startActivity(new Intent(mContext,ShopOrdersActivity.class));
                        break;
                    case "记一笔":
                        startActivity(new Intent(mContext,PersonalBillActivity.class));
                        break;
                    case "日程":
                        startActivity(new Intent(mContext, ScheduleActivity.class));
                        break;
                    case "审批":
                        startActivity(new Intent(mContext, ApprovalActivity.class));
                        break;
                    case "营销策划":
                        startActivity(new Intent(mContext, SchemeActivity.class));
                        break;
                    case "组织架构":
                        startActivity(new Intent(mContext,ShopActivity.class));
                        break;
                    case "部门培训":
                        startActivity(new Intent(mContext, SectionTrainingActivity.class));
                        break;
                    default:
                        Utils.showToast("攻城狮正在努力Coding...");
                }
            }
        });
        RecyclerView.LayoutManager gridLayoutManager = new GridLayoutManager(mContext,4);
        optionCycler.setLayoutManager(gridLayoutManager);
        optionCycler.setAdapter(funcAdapter);

        // init data
        bindShop();
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.rule_btn:
                startActivity(new Intent(mContext, RuleActivity.class));
                break;
            case R.id.honour_btn:
                startActivity(new Intent(mContext, HonourActivity.class));
                break;
            case R.id.feedback_btn:
                startActivity(new Intent(mContext, FeedbackActivity.class));
                break;
            case R.id.new_shop_btn:
                startActivity(new Intent(mContext, NewShopActivity.class));
                break;
            case R.id.switch_btn:
                Utils.showToast("显示切换门店的 Dialog .");
                switchShop();
                break;
            case R.id.shop_title:
                startActivity(new Intent(mContext, ShopActivity.class));
                break;
            default:
                break;
        }
    }

    private void requestUserInfo(){
        options.clear();
        Request request=new Request.Builder()
                .url(Constant.HOME_CONFIG_URL)
                .build();
        client=new OkHttpClient();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Message msg = new Message();
                msg.what = REQUEST_FUNC_FAILED;
                mHandler.sendMessage(msg);
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

    private void switchShop(){
        shopNameList = new ArrayList<>();
        shopList = new ArrayList<>();
        shopList = Arrays.asList(new ShopInfo[]
                {       new ShopInfo("杭派金地店","0001","方勤"),
                        new ShopInfo("水尚都府店","0002","勾霞"),
                        new ShopInfo("诸暨康乐园","0003","佚名")}
                        );
        shopNameList=Arrays.asList(new String[]{"杭派金地店", "水尚都府店", "诸暨康乐园"});
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.dialog_shop_select,null);
        shopListView = dialogView.findViewById(R.id.shop_listview);
        ArrayAdapter adapter = new ArrayAdapter(mContext,R.layout.my_simple_item,shopNameList);
        shopListView.setAdapter(adapter);

        mDialog = new MyDialog(mContext,R.style.Dialog);
        // mDialog.setTitle("选择已有店铺");
        mDialog.setMyView(dialogView);
        mDialog.show();

        // 绑定店铺，思考绑定在哪里
        shopListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                preferences.edit()
                        .putString(Constant.SHOP.SHOP_ID,shopList.get(position).getShopId())
                        .putString(Constant.SHOP.SHOP_MANAGER,shopList.get(position).getManagerName())
                        .putString(Constant.SHOP.SHOP_NAME,shopList.get(position).getShopName())
                        .apply();
                bindShop();
                mDialog.dismiss();
            }
        });
    }

    /**
     * 绑定店铺信息
     */
    private void bindShop(){
        shopNameTv.setText(preferences.getString(Constant.SHOP.SHOP_ID,"000"));
        shopManagerTv.setText(preferences.getString(Constant.SHOP.SHOP_MANAGER,"---"));
        titleTv.setText(preferences.getString(Constant.SHOP.SHOP_NAME,"XX店铺"));
    }
}
