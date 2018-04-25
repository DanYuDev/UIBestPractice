package com.example.coderlt.uibestpractice.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import android.graphics.SweepGradient;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by coderlt on 2018/4/18.
 */

public class LoadingView2 extends View {
    private static final String TAG = "LoadingView2";
    private int mWidth,mHeight;
    private int radius;
    private Paint mPaint;
    private Path progressPath;
    private final float PI = 3.1415926f;
    private Shader shader;
    private int count=0;

    public LoadingView2(Context context){
        super(context,null);
    }

    public LoadingView2(Context context, AttributeSet attrs){
        super(context,attrs);
        init();
    }

    private void init(){
        shader = new SweepGradient(0,0,Color.BLACK,Color.WHITE);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(6);
        mPaint.setShader(shader);
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldWidth,int oldHeight){
        mWidth = w;
        mHeight = h;
        radius = w/2-10;

        progressPath = new Path();
        float angle=0;
        for(int i=0;i<40;i++){
            Path subPath = new Path();
            subPath.moveTo((int)((radius-30)*Math.cos(angle)),(int)((radius-30)*Math.sin(angle)));
            subPath.lineTo((int)((radius)*Math.cos(angle)),(int)((radius)*Math.sin(angle)));
            angle += (2*PI/360)*20;
            progressPath.addPath(subPath);
//            mPaint.setColor((int)(Color.BLACK*0.8));
//            canvas.drawLine(70,0,100,0,mPaint);
//            canvas.rotate(20);
        }
    }

    @Override
    public void onDraw(Canvas canvas){
        canvas.translate(mWidth/2,mHeight/2);
        canvas.rotate((-20)*count++);
        canvas.drawPath(progressPath,mPaint);
        postInvalidateDelayed(1);
    }
}
