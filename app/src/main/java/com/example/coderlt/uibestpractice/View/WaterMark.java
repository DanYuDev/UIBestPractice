package com.example.coderlt.uibestpractice.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.coderlt.uibestpractice.R;

/**
 * Created by coderlt on 2018/3/5.
 */

public class WaterMark extends View {
    private Paint mPaint ;
    private int mWidth,mHeight;
    private int cx,cy,radius;
    private Path textPath;

    public WaterMark(Context context){
        super(context);
    }

    public WaterMark(Context context, AttributeSet attrs){
        super(context,attrs);
        init();
    }

    private void init(){
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPath = new Path();
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh){
        mWidth=w;
        mHeight=h;
        cx = w/2;
        cy = h/2;
        radius = cx;
    }
    @Override
    public void onDraw(Canvas canvas){
        mWidth =getMeasuredWidth();
        mHeight=getMeasuredHeight();
        mPaint.setColor(Color.parseColor("#88BDCAFD"));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(7);
        canvas.drawCircle(cx,cy,radius-10,mPaint);
        textPath.addArc(new RectF(40,40,mWidth-40,mHeight-40),130,360);
        mPaint.setTextSize(10);
        canvas.drawTextOnPath("杭派健康管理有限公司",textPath,0,0,mPaint);
    }
}
