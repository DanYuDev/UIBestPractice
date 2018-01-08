package com.example.coderlt.uibestpractice.View;


import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.media.MediaRecorder;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;

/**
 * Created by coderlt on 2017/12/30.
 * 封装一个 VoiceButton。松耦合
 */

public class VoiceButton extends View{
    private final String TAG=getClass().getSimpleName();

    private int mWidth,mHeight;
    private Paint mPaint;
    private RectF rectF;
    private Paint textPaint;
    private String mText;

    private boolean pressed=false;
    private boolean isRecording=false;

    private String filePath=null;
    private MediaRecorder recorder;
    private VoiceListener mListener=null;

    public interface VoiceListener{
        void onStart();
        void onFinish(String audioPath);
    };

    public void setVoiceListener(VoiceListener listener){
        mListener=listener;
    }
    public VoiceButton(Context context){
        this(context,null);
    }

    public VoiceButton(Context context, AttributeSet attrs){
        super(context,attrs);
        //init();
    }

    private void init(){
        recorder=new MediaRecorder();
        recorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        recorder.setOutputFormat(MediaRecorder.OutputFormat.AMR_NB);
        recorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
    }

    @Override
    protected void onSizeChanged(int w,int h,int oldw,int oldh){
        mWidth=w;
        mHeight=h;
    }

    public void setFilePath(String path){
        filePath=path;
    }

    @Override
    protected void onDraw(Canvas canvas){
        mPaint=new Paint();
        textPaint=new Paint();
        rectF=new RectF(0,0,mWidth,mHeight);
        if(pressed==false)

        mPaint.setColor(Color.WHITE);
        else
            mPaint.setColor(Color.parseColor("#BBBBBB"));

        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);
        //canvas.drawRoundRect(0,0,mWidth,mHeight,10,10,mPaint);
        canvas.drawRoundRect(rectF,14,14,mPaint);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(3);
        mPaint.setColor(Color.LTGRAY);
        canvas.drawRoundRect(rectF,10,10,mPaint);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(30);
        textPaint.setAntiAlias(true);
        textPaint.setColor(Color.DKGRAY);
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        float baseline = (rectF.bottom + rectF.top - fontMetrics.bottom - fontMetrics.top) / 2;

        if(pressed==false)
            mText="长按说话";
        else {
            mText="松开结束录音";
        }
        canvas.drawText(mText,rectF.centerX(),baseline,textPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                pressed=true;
                init();
                if(startRecorder()==false)
                    return true;
                invalidate();
                break;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                pressed=false;
                stopRecord();
                invalidate();
                break;
        }
        return true;
    }

    private boolean startRecorder(){
        if (filePath!=null){
            Log.e(TAG,"set the filePath.");
            recorder.setOutputFile(filePath);
        }else{
            Toast.makeText(getContext(),"Filepath is empty.",Toast.LENGTH_SHORT)
                    .show();
            return false;
        }

        try{
            Log.e(TAG,"prepare the recorder.");
            recorder.prepare();
        }catch(IOException ex){
            ex.printStackTrace();
        }
        recorder.start();
        if(mListener!=null)
        mListener.onStart();
        isRecording=true;
        return true;
    }

    private void stopRecord(){
        if(isRecording){
            recorder.stop();
            if(mListener!=null)
            mListener.onFinish(filePath);
            recorder.reset();
            //recorder.release();
        }
        isRecording=false;
    }

}
