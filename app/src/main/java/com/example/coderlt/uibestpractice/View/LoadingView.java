package com.example.coderlt.uibestpractice.View;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by coderlt on 2018/4/17.
 */

public class LoadingView extends View {
    private static final String TAG = "LoadingView";
    private int mWidth;
    private int mHeight;
    private int min;
    private int cx,cy;
    private int radius;
    private Paint mPaint;
    private Shader shader;
    private int count=0;

    public LoadingView(Context context){
        super(context,null);
    }

    public LoadingView(Context context, AttributeSet attrs){
        super(context,attrs);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(10);
        //mPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh){
        super.onSizeChanged(w,h,oldw,oldh);
        mWidth = w;
        mHeight = h;
        min = Math.min(mWidth,mHeight);
        radius=w/2-20;
        cx = mWidth/2;
        cy = mHeight/2;
        shader = new SweepGradient(0,0,Color.parseColor("#DDDDDD"),Color.parseColor("#5E5E5E"));
        mPaint.setShader(shader);
    }

    @Override
    public void onDraw(Canvas canvas){
        canvas.translate(cx,cy);
        canvas.rotate(4*count++);
        canvas.drawCircle(0,0,radius,mPaint);
        postInvalidateDelayed(1);
    }
}
