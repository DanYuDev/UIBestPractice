package com.example.coderlt.uibestpractice.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;

import com.example.coderlt.uibestpractice.R;

public class PopwndActivity extends AppCompatActivity implements View.OnClickListener{
    PopupWindow popWnd;
    private Button popBtn;
    private Button popDrop;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popwnd);
        showWnd();
    }

    private void showWnd(){
        popBtn=(Button)findViewById(R.id.pop_btn);
        popDrop=(Button)findViewById(R.id.pop_drop);

        //有个严重的问题，这个activity_main得到以后，还是 activityMain里面的那个布局么？
        rootView=LayoutInflater.from(this).inflate(R.layout.activity_main,null);
        View view= LayoutInflater.from(this).inflate(R.layout.pop_wnd_layout,null);

        popWnd=new PopupWindow(view, ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        //popWnd.showAtLocation(rootView);
        popBtn.setOnClickListener(this);
        popDrop.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.pop_btn:
                //不设置x，y的话，默认使位于水平中间的
                popWnd.showAtLocation(new LinearLayout(this), Gravity.BOTTOM, 0, 0);
                break;
            case R.id.pop_drop:
                //这个窗口一定会判断anchor的位置，它一定是紧贴左或者右界面的，看anchor来判断是左还是右
                popWnd.showAsDropDown(popDrop);
                break;
            default:
                break;
        }
    }
}
