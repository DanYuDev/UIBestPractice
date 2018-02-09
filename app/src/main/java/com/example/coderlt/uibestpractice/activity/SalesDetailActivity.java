package com.example.coderlt.uibestpractice.activity;

import android.app.Activity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.View.MyDialog;
import com.example.coderlt.uibestpractice.adapter.ImageWallAdapter;
import com.example.coderlt.uibestpractice.bean.Goods;
import com.example.coderlt.uibestpractice.bean.ImageInfo;
import com.example.coderlt.uibestpractice.bean.SalesRecord;
import com.example.coderlt.uibestpractice.utils.Constant;
import com.example.coderlt.uibestpractice.utils.JsonUtils;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SalesDetailActivity extends AppCompatActivity {
    private static final String TAG = "SalesDetalActivity";
    private static final int UPDATE_SALES = 0x101010;
    private static final int USE_DEFAULT = 0x101011;
    private static final int PROGRESS_END = 0x110011;
    private TableLayout salesTable;
    private List<SalesRecord> salesList = new ArrayList<>();
    private OkHttpClient client;
    private String responseText;
    private MyDialog progressDialog;
    private List<ImageInfo> imgList = new ArrayList<>();
    private RecyclerView imgCycler;
    private ImageWallAdapter adapter;

    static class MyHandler extends Handler{
        private WeakReference<SalesDetailActivity> wr;

        public MyHandler(SalesDetailActivity act){
            wr = new WeakReference<SalesDetailActivity>(act);
        }

        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case UPDATE_SALES:
                    wr.get().progressDialog.dismiss();
                    wr.get().bindSalesData();
                    break;
                case USE_DEFAULT:
                    wr.get().progressDialog.dismiss();
                    wr.get().useDefaultSalesList();
                    break;

            }
        }
    }

    private MyHandler mHandler = new MyHandler(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_detail);

        initViews();
        setSalesTable();
        setImageCycler();
    }

    private void initViews(){
        salesTable = findViewById(R.id.sales_table);
        imgCycler = findViewById(R.id.img_recycler);
    }


    //TODO 列表卡顿
    private void setImageCycler(){
        getImgList();
        adapter = new ImageWallAdapter(this,R.layout.image_wall_item,imgList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(this,3);
        imgCycler.setLayoutManager(layoutManager);
        imgCycler.setAdapter(adapter);
        imgCycler.addItemDecoration(new
                DividerItemDecoration(this,
                DividerItemDecoration.VERTICAL|DividerItemDecoration.HORIZONTAL));

        imgCycler.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState==RecyclerView.SCROLL_STATE_IDLE){
                    adapter.setIsScrolling(true);
                }else{
                    adapter.setIsScrolling(false);
                }
            }
        });
    }

    private void getImgList(){
        ContentResolver cr = getContentResolver();
        Cursor cursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,null,null,null);
        if(cursor!=null){
            while(cursor.moveToNext()){
                String name = cursor.getString(cursor.getColumnIndex(
                        MediaStore.Images.Media.DISPLAY_NAME));
                String path = cursor.getString(cursor.getColumnIndex(
                        MediaStore.Images.Media.DATA));
                imgList.add(new ImageInfo(name,path));
            }
        }
    }

    private void setSalesTable(){
        requestSalesInfo();
    }

    private void bindSalesData(){
        TextView nameTv,priceTv,countTv;
        TableRow row;
        TextView tv;
        SalesRecord record = null;

        // add table heade
        // -----------------

        for(int i=0;i<=salesList.size();i++){
            if(i != 0)
                record = salesList.get(i-1);

            TableRow.LayoutParams lp =
                    new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1);
            row = new TableRow(this);
            row.setDividerDrawable(getResources().getDrawable(R.drawable.table_horizental_divider));
            row.setShowDividers(LinearLayout.SHOW_DIVIDER_BEGINNING |
                                LinearLayout.SHOW_DIVIDER_MIDDLE |
                                LinearLayout.SHOW_DIVIDER_END);

            for(int j= 0;j<3;j++ ){
                tv = new TextView(this);
                tv.setTextColor(Color.parseColor("#666666"));
                tv.setTextSize(16);
                tv.setPadding(20,10,20,10);
                row.addView(tv,lp);
                switch (j){
                    case 0:
                        if(i==0){
                            tv.setText("商品名");
                            tv.setTextColor(Color.WHITE);
                            tv.setBackgroundColor(Color.parseColor("#BEB0F9"));
                        }else{
                            tv.setText(record.getGoods().getName());
                        }
                        break;
                    case 1:
                        if(i==0){
                            tv.setText("单价");
                            tv.setTextColor(Color.WHITE);
                            tv.setBackgroundColor(Color.parseColor("#BEB0F9"));
                        }else{
                            tv.setText(record.getGoods().getPrice()+"");
                        }
                        break;
                    case 2:
                        if(i==0){
                            tv.setText("数量");
                            tv.setTextColor(Color.WHITE);
                            tv.setBackgroundColor(Color.parseColor("#BEB0F9"));
                        }else{
                            tv.setText(record.getCount()+"");
                        }
                        break;

                }
            }

            TableLayout.LayoutParams tlp = new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);

            Log.d(TAG,"add a table row");
            salesTable.addView(row,tlp);
        }
    }

    private void useDefaultSalesList(){
        Log.d(TAG,"Use default sales list");
        salesList.add(new SalesRecord(new Goods("苏静丸",120),5));
        salesList.add(new SalesRecord(new Goods("刮痧丸",120),5));
        salesList.add(new SalesRecord(new Goods("龙精油",120),5));
        salesList.add(new SalesRecord(new Goods("冰可乐",120),5));
        bindSalesData();
    }

    private void requestSalesInfo(){
        client = new OkHttpClient.Builder()
                .connectTimeout(1200, TimeUnit.MILLISECONDS)
                .build();

        progressDialog = new MyDialog(this,R.style.Dialog);
        progressDialog.setLayoutResId(R.layout.dialog_layout);

        Request request = new Request.Builder()
                .url(Constant.SALES_URL)
                .build();
        progressDialog.show();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG,"request network failed");
                Message msg = new Message();
                msg.what = USE_DEFAULT;
                mHandler.sendMessage(msg);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.d(TAG,"Request network success");
                responseText = response.body().string();
                JsonUtils.DealSales(responseText,salesList);
                Message msg = new Message();
                msg.what = UPDATE_SALES;
                mHandler.sendMessage(msg);
            }
        });
    }
}
