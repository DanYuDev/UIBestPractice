package com.example.coderlt.uibestpractice.View;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
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
    private Context mContext;
    private RecyclerView recyclerView;
    private String[] dataList={"1","2","3","4","5","6","7","8","9",".","0","确定"};
    private OnItemClickedListener listener;

    public interface OnItemClickedListener{
        void onItemClicked(String str);
    }

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

    private  class KeyboardAdapter extends RecyclerView.Adapter<KeyboardAdapter.ViewHolder>{

        class ViewHolder extends RecyclerView.ViewHolder{
            TextView tv;
            public ViewHolder(View view){
                super(view);
                tv = view.findViewById(R.id.keyboard_tv);
            }
        }
        public KeyboardAdapter(){
            super();
        }

        //----------------------------------------重写这三个方法---------------------------------------------
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int ViewType){
            // 感觉这里设计不合理，应该由用户传layout过来
            View v = LayoutInflater.from(mContext).
                    inflate(R.layout.key_board_item,null);
            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, final int position){
            TextView tv = holder.tv;
            Resources resources = getResources();
            if(position<9){
                tv.setText(String.valueOf(position+1));
            }else if(position == 9){
                tv.setText(".");
                tv.setTextSize(30);
                tv.setTextColor(resources.getColor(android.R.color.white));
                tv.setBackgroundColor(resources.getColor(android.R.color.darker_gray));
            }else if(position == 10){
                tv.setText("0");
            }else{
                tv.setBackgroundColor(resources.getColor(android.R.color.darker_gray));
                tv.setTextColor(resources.getColor(android.R.color.white));
                tv.setText("确定");
            }
            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(listener!=null)
                        listener.onItemClicked(dataList[position]);
                }
            });
        }

        @Override
        public int getItemCount(){
            return dataList.length;
        }
    }

    public void setOnItemClickedListener(OnItemClickedListener listener){
        this.listener = listener;
    }
}
