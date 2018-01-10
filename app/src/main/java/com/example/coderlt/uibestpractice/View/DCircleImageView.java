package com.example.coderlt.uibestpractice.View;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.coderlt.uibestpractice.R;

/**
 * Created by coderlt on 2018/1/10.
 */

public class DCircleImageView extends View {

    private int mWidth,mHeight;
    private Bitmap mBitmap;
    private int srcId;
    private int radius;
    private Paint mPaint;
    private Path path;
    public DCircleImageView(Context context){
        this(context,null);
    }

    public DCircleImageView(Context context, AttributeSet attrs){
        super(context,attrs);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs){
        mPaint=new Paint();
        path=new Path();
        mPaint.setStyle(Paint.Style.STROKE);
        if(attrs!=null){
            TypedArray array=null;
            try{
                array=getContext().obtainStyledAttributes(attrs, R.styleable.DCircleImageView);
                srcId=array.getResourceId(R.styleable.DCircleImageView_src,-1);
                //radius=array.getInteger(R.styleable.DCircleImageView_radius,-1);
            }finally {
                if(array!=null)
                    array.recycle();
            }
        }

        mBitmap= BitmapFactory.decodeResource(getResources(),srcId);
        int bitmapWidth=mBitmap.getWidth();
        int bitmapHeight=mBitmap.getHeight();
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh){
        mWidth=w;
        mHeight=h;
        mBitmap=Bitmap.createScaledBitmap(mBitmap,mWidth,mHeight,true);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.translate(mWidth/2,mHeight/2);
        radius=(mWidth>mHeight?mHeight:mWidth)/2;
        path.addRoundRect(new RectF(-radius,radius,radius,-radius),radius, radius,Path.Direction.CCW);
        canvas.clipPath(path);

        /*这里的left和top指的是 bitmap这张图片里面的 left和top，即起始位置
        而不是 canvas 画图的起始位置，那么如何操控 画图的起始位置呢 ？
        采用 canvas.drawBitmap(mBitmap,SrcRect,dstRect,mPaint) 函数即可，Bitmap的缩放应该事先完成
        而不是在这个函数里去完成*/
        Rect rect=new Rect(0,0,radius*2,radius*2);
        //canvas.drawBitmap(mBitmap,0,0,mPaint);
        canvas.drawBitmap(mBitmap,new Rect(mWidth/2-radius,(mHeight/2-radius),
                mWidth/2+radius,(mHeight/2+radius)),
                new Rect(-radius,-radius,radius,radius), mPaint);
        //canvas.drawBitmap(mBitmap,0,0,mPaint);
        //canvas.drawBitmap(mBitmap,rect,new Rect(-radius,-radius,radius,radius),mPaint);
    }
}
