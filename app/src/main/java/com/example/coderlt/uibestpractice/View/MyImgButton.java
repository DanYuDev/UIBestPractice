package com.example.coderlt.uibestpractice.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.coderlt.uibestpractice.R;

/**
 * Created by coderlt on 2018/1/5.
 */

/** TODO
 * 做完才发现应该给 MyImgButton 做一个容器，这样才好处理监听，
 * 获取到它在容器中的的 position
 * 以便处理它和 ViewPager 的交互
 * 所以以后做项目应该先好好架构，避免不必要的重构
 */

public class MyImgButton extends RelativeLayout {

    private ImageView mImgView = null;
    private TextView mTextView = null;
    private Context mContext;
    private int unSelectedImgId,selectedImgId;
    private int unSelectedTextColor,selectedTextColor;
    private String text;

    public MyImgButton(Context context){
        this(context,null);
    }

    public MyImgButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        // TODO Auto-generated constructor stub
        LayoutInflater.from(context).inflate(R.layout.img_btn_layout, this, true);
        mContext = context;
        mImgView = (ImageView)findViewById(R.id.my_view_img);
        mTextView = (TextView)findViewById(R.id.my_view_text);
        mImgView.setImageResource(unSelectedImgId);
        mTextView.setText(text);
        mTextView.setTextColor(unSelectedTextColor);
        //mTextView.setGravity(Gravity.CENTER);
    }

    private void initAttrs(AttributeSet attrs){
        if(attrs!=null){
            TypedArray array=null;
            try{
                array=getContext().obtainStyledAttributes(attrs,R.styleable.MyImgButton);
                selectedImgId=array.getResourceId(R.styleable.MyImgButton_src_selected,1);
                unSelectedImgId=array.getResourceId(R.styleable.MyImgButton_src_unselected,1);
                selectedTextColor=array.getColor(R.styleable.MyImgButton_text_selected_color,1);
                unSelectedTextColor=array.getColor(R.styleable.MyImgButton_text_unselected_color,1);
                text=array.getString(R.styleable.MyImgButton_text);
            }finally {
                if(array!=null)
                    array.recycle();
            }
        }
    }

    /*设置图片接口*/
    public void setImageResource(int resId){
        mImgView.setImageResource(resId);
    }

    /*设置文字接口*/
    public void setText(String str){
        mTextView.setText(str);
    }
    /*设置文字大小*/
    public void setTextSize(float size){
        mTextView.setTextSize(size);
    }

    public void setSelected(){
        mTextView.setTextColor(selectedTextColor);
        mImgView.setImageResource(selectedImgId);
    }

    public void setUnSelected(){
        mTextView.setTextColor(unSelectedTextColor);
        mImgView.setImageResource(unSelectedImgId);
    }

//     /*设置触摸接口*/
//    public void setOnTouch(OnTouchListener listen){
//        mImgView.setOnTouchListener(listen);
//        //mTextView.setOnTouchListener(listen);
//    }

}
