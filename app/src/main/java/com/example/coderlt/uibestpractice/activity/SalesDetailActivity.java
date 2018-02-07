package com.example.coderlt.uibestpractice.activity;

import android.app.Activity;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.bean.Goods;
import com.example.coderlt.uibestpractice.bean.SalesRecord;
import com.example.coderlt.uibestpractice.utils.Constant;
import com.example.coderlt.uibestpractice.utils.JsonUtils;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

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
    private ProgressBar progressBar ;

    static class MyHandler extends Handler{
        private WeakReference<SalesDetailActivity> wr;

        public MyHandler(SalesDetailActivity act){
            wr = new WeakReference<SalesDetailActivity>(act);
        }

        @Override
        public void handleMessage(Message msg){
            switch(msg.what){
                case UPDATE_SALES:
                    wr.get().progressBar.setVisibility(View.INVISIBLE);
                    break;
                case USE_DEFAULT:
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
    }

    private void initViews(){
        salesTable = findViewById(R.id.sales_table);
        //salesTable.setBackgroundColor(Color.LTGRAY);
    }

    private void setSalesTable(){
        requestSalesInfo();
        bindSalesData();
    }

    private void bindSalesData(){
        TextView nameTv,priceTv,countTv;
        for(int i=0;i<salesList.size();i++){
            SalesRecord record = salesList.get(i);

            TableRow.LayoutParams lp =
                    new TableRow.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT,1);
            TableRow row = new TableRow(this);
            nameTv = new TextView(this);
            priceTv = new TextView(this);
            countTv = new TextView(this);

            row.addView(nameTv,lp);
            row.addView(priceTv,lp);
            row.addView(countTv,lp);

            nameTv.setText(record.getGoods().getName());
            priceTv.setText(record.getGoods().getPrice()+"");
            countTv.setText(record.getCount()+"");
            nameTv.setTextColor(Color.RED);
            priceTv.setTextColor(Color.RED);
            nameTv.setTextColor(Color.RED);
            TableLayout.LayoutParams tlp = new TableLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            //row.setBackgroundColor(0x29CC00+0x222222*i);
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
        client = new OkHttpClient();
        progressBar = new ProgressBar(this);

        Request request = new Request.Builder()
                .url(Constant.SALES_URL)
                .build();
        progressBar.setVisibility(View.VISIBLE);
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
