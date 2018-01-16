package com.example.coderlt.uibestpractice.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Arrays;

/**
 * Created by coderlt on 2017/12/30.
 */

public class LineGraphFinal extends View {
    private final String TAG=getClass().getSimpleName();
    private int mWidth,mHeight;
    private Paint mPaint,pointPaint;
    private Path linePath;
    private Paint pathPaint;
    private float[] dataSet;
    private float[] xSet;
    private float xGap;
    private float yRatio;
    private float max;
    private boolean isSetData=false;

    public LineGraphFinal(Context context){
        super(context);
    }

    public LineGraphFinal(Context context,float[] dataSet){
        super(context);
    }

    public LineGraphFinal(Context context, AttributeSet attrs){
        super(context,attrs);
    }

    private float findMax(float[] dataSet){
        float max= Float.MIN_VALUE;
        for(int i=0;i<7;i++)
            max=max>dataSet[i]?max:dataSet[i];
        return max;
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldW,int oldH){
        mWidth=w;
        mHeight=h;
        xGap=w/8;
        yRatio=(h-100)/max;
        xSet=new float[7];
        for(int i=0;i<7;i++){
            xSet[i]=(i+1)*xGap;
        }
    }

    private void init(){
        Log.d(TAG,"xSet :"+Arrays.toString(xSet));
        max=findMax(dataSet);
        mPaint=new Paint();
        pathPaint=new Paint();
        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(1);
        pathPaint.setColor(Color.WHITE);
        pathPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(4);
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.translate(0,mHeight);
        canvas.scale(1,-1);
        if(isSetData==false)return ;
        linePath=new Path();
        Log.d(TAG,"Gap is "+xGap);
        float x,y;
        for(int i=0;i<7;i++){
            Log.d(TAG,"x is :"+xSet[i]);
            x=xSet[i];
            y=dataSet[i]*yRatio;
            canvas.drawPoint(x,y,mPaint);
            canvas.drawLine(x,0,x,10,pathPaint);
            if(i==0)
                linePath.moveTo(x,y);
            linePath.lineTo(x,y);
        }
        canvas.drawPath(linePath,pathPaint);
    }

    public void setDataSet(float[] dataSet){
        this.dataSet=dataSet;
        isSetData=true;
        init();
        invalidate();
    }
}
