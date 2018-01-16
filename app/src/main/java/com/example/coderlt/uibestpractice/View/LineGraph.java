package com.example.coderlt.uibestpractice.View;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.Arrays;

/**
 * Created by coderlt on 2017/12/30.
 */

public class LineGraph extends View {
    private final String TAG=getClass().getSimpleName();
    private int mWidth,mHeight;
    private Paint mPaint,pointPaint;
    private PointF[] dataSet;
    private int len=0;
    private float minX,maxX,minY,maxY;
    private float diffX,diffY,scaleX,scaleY;

    public LineGraph(Context context){
        this(context,null);
    }

    public LineGraph(Context context, AttributeSet attrs){
        super(context,attrs);
        init();
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldW,int oldH){
        mWidth=w;
        mHeight=h;
    }

    private void init(){
        dataSet=new PointF[20];
        mPaint=new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.BLUE);
        pointPaint=new Paint();
        pointPaint.setColor(Color.BLACK);
        pointPaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas){
        // sorted by the date
        if (len>0)
        {
            PointF temp;
            for(int i=0;i<(len-1);i++){
                for(int j=i+1;j<len;j++)
                if(dataSet[i].x>dataSet[j].x){
                    temp=dataSet[i];
                    dataSet[i]=dataSet[j];
                    dataSet[j]=temp;
                }
            }
        }
        //len=dataSet.length;

        mPaint.setStrokeWidth(7);
        canvas.translate(0,mHeight);
        canvas.scale(1,-1);
        canvas.drawLine(100,100,mWidth-200,100,mPaint);
        canvas.drawLine(100,100,100,mHeight-200,mPaint);

        minX=Float.MAX_VALUE;minY=Float.MAX_VALUE;
        maxX=-1;maxY=-1;
        for(int i=0;i<(len-1);i++){
            minX=dataSet[i].x<minX?dataSet[i].x:minX;
            minY=dataSet[i].y<minY?dataSet[i].y:minY;
            maxX=dataSet[i].x>minX?dataSet[i].x:maxX;
            maxY=dataSet[i].y>maxY?dataSet[i].y:maxY;
        }
        diffX=maxX-minX;
        diffY=maxY-minY;
        scaleX=(mWidth-300-2)/diffX;
        scaleY=(mHeight-300-2)/diffY;
        //LOg Log LogLogLogLogLogLog Log Log
        Log.d(TAG,"minX: "+minX+"  ; maxX: "+maxX+"\n"+
                "minY: "+minY+"  maxY: "+maxY);
        Log.d(TAG,"scaleX: "+scaleX+",  scaleY: "+scaleY);

        mPaint.setColor(Color.RED);
        mPaint.setStrokeWidth(4);
        float x1,x2;
        float y1,y2;
        x1=(dataSet[0].x-minX)*scaleX+100;
        y1=(dataSet[0].y-minY)*scaleY+100;

        for (int i=1;i<(len-1);i++){
            canvas.drawPoint(x1,y1,pointPaint);
            x2=(dataSet[i].x-minX)*scaleX+100;
            y2=(dataSet[i].y-minY)*scaleY+100;
            Log.d(TAG,"(x,y):"+x1+",  "+y1+"  To point(x,y): "+x2+", "+y2);
            canvas.drawLine(x1,y1,x2,y2,mPaint);
            x1=x2;
            y1=y2;
        }
    }

    public void setDataSet(PointF[] dataSet,int len ){
        this.dataSet=dataSet;
        this.len=len;
    }
}
