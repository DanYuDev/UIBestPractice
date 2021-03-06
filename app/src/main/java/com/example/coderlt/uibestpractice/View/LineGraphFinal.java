package com.example.coderlt.uibestpractice.View;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.coderlt.uibestpractice.R;

import java.util.Arrays;

/**
 * Created by coderlt on 2017/12/30.
 */

public class LineGraphFinal extends View {
    private final String TAG=getClass().getSimpleName();
    private int mWidth,mHeight;
    private Paint mPaint,pointPaint,coordinatePaint;
    private Path linePath;
    private Paint pathPaint,textPaint;
    private float[] dataSet;
    private float[] xSet;
    private float xGap;
    private float yRatio;
    private float max;
    private int maxIndex;
    private boolean isSetData=false;
    private String[] title={"一月","二月","三月","四月","五月","六月","七月"};

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
        maxIndex = 0;
        for(int i=0;i<7;i++){
            if(max<dataSet[i]){
                max=dataSet[i];
                maxIndex = i;
            }
        }
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
        textPaint=new Paint();

        pathPaint.setStyle(Paint.Style.STROKE);
        pathPaint.setStrokeWidth(1);
        pathPaint.setColor(Color.WHITE);
        pathPaint.setAntiAlias(true);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        mPaint.setColor(Color.WHITE);
        mPaint.setAntiAlias(true);

        textPaint=new Paint();
        textPaint.setTextSize(20);
        textPaint.setColor(Color.WHITE);
        textPaint.setAntiAlias(true);

        coordinatePaint = new Paint();
        coordinatePaint.setColor(Color.WHITE);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if(isSetData==false)return ;
        canvas.drawLine(10,0,10,mHeight,coordinatePaint);
        DashPathEffect effect = new DashPathEffect(new float[]{5,10},0);
        coordinatePaint.setPathEffect(effect);
        setLayerType(LAYER_TYPE_SOFTWARE,coordinatePaint);
        canvas.drawLine(10,(mHeight-max*yRatio),xSet[maxIndex],mHeight-max*yRatio,
                coordinatePaint);
        linePath=new Path();
        //Log.d(TAG,"Gap is "+xGap);
        float x,y;
        for(int i=0;i<7;i++){
            Log.d(TAG,"x is :"+xSet[i]);
            x=xSet[i];
            y=(mHeight-dataSet[i]*yRatio);
            //TODO 注意下面这个方法，设置线段两端的点的形状，在画point的时候表现为点的形状
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawPoint(x,y,mPaint);
            canvas.drawLine(x,mHeight,x,mHeight-10,mPaint);
            canvas.drawText(title[i],x-20,mHeight-20,textPaint);
            if(i==0)
                linePath.moveTo(x,y);
            linePath.lineTo(x,y);
        }
//        canvas.drawBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.ic_logo_white),
//                0,0,mPaint);
        canvas.drawPath(linePath,pathPaint);
        canvas.drawLine(0,mHeight,mWidth,mHeight,mPaint);
    }

    public void setDataSet(float[] dataSet){
        this.dataSet=dataSet;
        isSetData=true;
        init();
        invalidate();
    }
}
