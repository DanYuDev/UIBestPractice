package com.example.coderlt.uibestpractice.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ProgressBar;

import com.example.coderlt.uibestpractice.R;

/**
 * Created by coderlt on 2017/12/30.
 */

public class ProgressWheel extends View {
    private int mWidth,mHeight;
    private int contentWidth,contentHeight;
    private Bitmap contentBitmap;
    private Bitmap scaledBitmap;
    private RectF rectF;
    private Paint mPaint,pointPaint;
    private int progress;

    public ProgressWheel (Context context){
        this(context,null);
    }

    public ProgressWheel(Context context, AttributeSet attrs){
        super(context,attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldW,int oldH){
        mWidth=w;
        mHeight=h;
        rectF=new RectF(0,0,mWidth,mHeight);
        //scaledBitmap=Bitmap.createBitmap(mWidth/2,mHeight/2, Bitmap.Config.ARGB_8888);
        scaledBitmap=Bitmap.createScaledBitmap(contentBitmap,mWidth,mHeight,true);
    }

    public void init(){
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
        pointPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.WHITE);
        mPaint.setStrokeWidth(6);
        pointPaint.setColor(Color.RED);
        pointPaint.setStyle(Paint.Style.FILL);
        progress=0;
        contentBitmap=BitmapFactory.decodeResource(getResources(), R.drawable.bitmap_loading);
    }

    //打算实现一个太极图 旋转 进度条
    @Override
    protected void onDraw(Canvas canvas){
        //mPaint.setColor(Color.RED);
        //mPaint.setStyle(Paint.Style.STROKE);
        //mPaint.setStrokeWidth(2);

        //mPaint.setStyle(Paint.Style.STROKE);
        //canvas.drawCircle(mWidth/2,mHeight/2,60,mPaint);
        canvas.translate(mWidth/2,mHeight/2);
        canvas.rotate(5*progress++);
        //canvas.drawBitmap(scaledBitmap,-mWidth/2,-mHeight/2,mPaint);
        canvas.drawCircle(0,0,mWidth/2-20,mPaint);
        canvas.drawCircle(mWidth/2-20,0,10,pointPaint);

        postInvalidateDelayed(20);
    }

}
