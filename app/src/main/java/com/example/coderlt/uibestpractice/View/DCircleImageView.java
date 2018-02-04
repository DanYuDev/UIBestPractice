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
import com.example.coderlt.uibestpractice.utils.BitmapUtil;

/**
 * 值得完善的地方，关于自定义属性，如果继承 ImageView ，src属性怎么获得 Bitmap
 * Created by coderlt on 2018/1/10.
 */

public class DCircleImageView extends View {

    private int mWidth,mHeight;
    private int bitmapWidth,bitmapHeight;
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
        mPaint=new Paint(Paint.ANTI_ALIAS_FLAG);
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
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh){
        mWidth=w;
        mHeight=h;
        mBitmap = BitmapUtil.decodeSampleBitmapFromResource(getResources(),srcId,w,h);
        float rate = Math.min(mBitmap.getWidth()/mWidth,mBitmap.getHeight()/mHeight);
        bitmapWidth =(int) (mBitmap.getWidth()/rate);
        bitmapHeight =(int) (mBitmap.getHeight()/rate);
        mBitmap = Bitmap.createScaledBitmap(mBitmap,bitmapWidth,bitmapHeight,true);
    }

    @Override
    protected void onDraw(Canvas canvas){
        canvas.translate(mWidth/2,mHeight/2);
        radius=mWidth/2;
        path.addRoundRect(new RectF(-radius,-radius,radius,radius),radius, radius,Path.Direction.CCW);
        canvas.clipPath(path);
        canvas.drawBitmap( mBitmap,new Rect(bitmapWidth/2-radius,bitmapHeight/2-radius,
                bitmapWidth/2+radius,bitmapHeight/2+radius),
                new Rect(-radius,-radius,radius,radius),mPaint);
    }
}
