package com.example.coderlt.uibestpractice.activity;

import android.animation.ObjectAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import com.example.coderlt.uibestpractice.R;

public class ShopOrdersActivity extends AppCompatActivity implements View.OnClickListener{
    private ImageView searchIv;
    private EditText searchEt;
    private View verticalLine;
    private ObjectAnimator searchAnimator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_orders);

        initViews();

        searchAnimator = ObjectAnimator.ofFloat(searchEt,"scaleX",0.2f,1f)
                .setDuration(600);
    }

    private void initViews(){
        searchIv = findViewById(R.id.search_iv);
        searchEt = findViewById(R.id.search_et);
        verticalLine = findViewById(R.id.vertical_line);

        searchIv.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.search_iv:
                searchIv.setVisibility(View.INVISIBLE);
                verticalLine.setVisibility(View.INVISIBLE);
                searchEt.setVisibility(View.VISIBLE);
                searchAnimator.start();
                break;
            case R.id.search_et:
                break;
            default:
                break;
        }
    }
}
