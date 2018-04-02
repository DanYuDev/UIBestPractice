package com.example.coderlt.uibestpractice.View;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.RectF;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.util.List;

/**
 * Created by coderlt on 2018/3/24.
 * TODO 遗憾的是，这个封装的控件没办法处理wrap_content的情况
 *      item固定高度为 50，那么4行就是200，写300高度就会多出来一大块
 *      想办法解决这个问题，先设置为固定200好了
 * TODO 设置Divider
 */
public class NumericalKeyboard extends RelativeLayout {
    private static final String TAG = "NumericalKeyboard";
    private Context mContext;
    private RecyclerView recyclerView;
    private String[] dataList={"1","2","3","4","5","6","7","8","9",".","0","确定"};
    private OnItemClickedListener listener;
    private final int BUTTON_PRESSED = 0x1111;
    private final int BUTTON_RELEASED = 0x2222;
    private float delta=40;
    private float downX,downY;

    public interface OnItemClickedListener{
        void onItemClicked(String str);
    }

    private class MyHandler extends Handler{
        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case BUTTON_PRESSED:

                    break;
                case BUTTON_RELEASED:
                    break;
            }
        }
    }

    private MyHandler mHandler = new MyHandler();

    public NumericalKeyboard(Context context){
        super(context,null);
    }

    public NumericalKeyboard(Context context, AttributeSet attrs){
        super(context,attrs);
        mContext = context;
        initViews();
    }

    private void initViews(){
        recyclerView = new RecyclerView(mContext);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        recyclerView.setLayoutParams(layoutParams);
        addView(recyclerView);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(mContext,3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(new KeyboardAdapter());
    }

    @Override
    public void onSizeChanged(int w,int h,int oldw,int oldh){

    }

    private  class KeyboardAdapter extends RecyclerView.Adapter<KeyboardAdapter.ViewHolder> {

        class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv;

            public ViewHolder(View view) {
                super(view);
                tv = view.findViewById(R.id.keyboard_tv);
            }
        }

        public KeyboardAdapter() {
            super();
        }

        //----------------------------------------重写这三个方法---------------------------------------------
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType) {
            // 感觉这里设计不合理，应该由用户传layout过来
            View v = LayoutInflater.from(mContext).
                    inflate(R.layout.key_board_item, null);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position) {
            final TextView tv = holder.tv;
            Resources resources = getResources();
            if (position < 9) {
                tv.setText(String.valueOf(position + 1));
            } else if (position == 9) {
                tv.setText(".");
                tv.setTextSize(30);
                tv.setTextColor(resources.getColor(android.R.color.white));
                tv.setBackgroundResource(R.drawable.button_bg_mid_blue);
            } else if (position == 10) {
                tv.setText("0");
            } else {
                tv.setBackgroundResource(R.drawable.button_bg_mid_blue);
                tv.setTextColor(resources.getColor(android.R.color.white));
                tv.setText("确定");
            }

            /**
             * TODO 存在严重的滑动取消问题
             */
            tv.setOnTouchListener(new OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    switch (event.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            Log.d(TAG,"Action Down...............");
                            downX = event.getX();
                            downY = event.getY();
                            if(position!=9 && position!=11)
                                tv.setBackgroundResource(R.drawable.button_bg_mid_blue);
                            else
                                tv.setBackgroundResource(R.drawable.button_bg);
                            break;
                        case MotionEvent.ACTION_MOVE:
                            Log.d(TAG,"Action Move.."+event.getX()+","+event.getY());
                            if(isCancel(event.getX(),event.getY(),tv)){
                                if(position!=9 && position!=11)
                                    tv.setBackgroundResource(R.drawable.button_bg_gray);
                                else
                                    tv.setBackgroundResource(R.drawable.button_bg_mid_blue);
                            }
                            break;
                        case MotionEvent.ACTION_UP:
                            Log.d(TAG,"Action Up.................");
                            if(position!=9 && position!=11)
                                tv.setBackgroundResource(R.drawable.button_bg_gray);
                            else
                                tv.setBackgroundResource(R.drawable.button_bg_mid_blue);
                            listener.onItemClicked(dataList[position]);
                            break;
                        default:
                            break;
                    }
                    return true;
                }
            });

        }

        @Override
        public int getItemCount() {
            return dataList.length;
        }
    }

    public void setOnItemClickedListener(OnItemClickedListener listener){
        this.listener = listener;
    }

    private boolean isCancel(float x, float y,View v) {
        //判断手的位置 是否移动到控件上/下方50的位置 进行取消(微信好像只判断移动到按钮上方)
        float value = ViewConfiguration.get(getContext()).getScaledTouchSlop();
        if( Math.abs(downX-x)>=value||
                Math.abs(downY-y)>=value)
            return true;
        return false;
    }
}
