package com.example.coderlt.uibestpractice.activity;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.utils.Constant;
import com.example.coderlt.uibestpractice.utils.Utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

public class InvitationActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG = "InvitationActivity";
    private ImageView titleLeft;
    private Button buildQrBtn;
    private ImageView qrIv;
    private Bitmap qrBitmap = null;
    private static final int SET_QR_BITMAP = 0x0000;
    private StringBuilder qrMessage;
    private SharedPreferences preferences;

    static class MyHandler extends Handler {
        WeakReference<InvitationActivity> wr ;

        public MyHandler(InvitationActivity act){
            wr = new WeakReference<InvitationActivity>(act);
        }

        @Override
        public void handleMessage(Message msg){
            switch (msg.what){
                case SET_QR_BITMAP:
                    wr.get().qrIv.setImageBitmap(wr.get().qrBitmap);
            }
        }
    }

    private MyHandler mHandler = new MyHandler(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation);

        preferences = getPreferences(MODE_PRIVATE);
        qrMessage = new StringBuilder();
        qrMessage.append(preferences.getString(Constant.USER.USER_ID,"").trim());
        qrMessage.append(preferences.getString(Constant.USER.USER_PHONE,"").trim());
        qrMessage.append("余丹 15068159967");
        initViews();
    }

    private void initViews(){
        titleLeft = findViewById(R.id.title_left);
        buildQrBtn = findViewById(R.id.build_qr_btn);
        qrIv = findViewById(R.id.qr_iv);

        titleLeft.setOnClickListener(this);
        buildQrBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.build_qr_btn:
                if(qrMessage.toString().isEmpty()){
                    Utils.showToast("请登陆或完善您的手机信息");
                    return;
                }
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        qrBitmap = generateBitmap(qrMessage.toString(),
                                400,400);
                        Message msg = new Message();
                        msg.what = SET_QR_BITMAP;
                        mHandler.sendMessage(msg);
                    }
                }).start();
                break;
            case R.id.title_left:
                finish();
                break;
            default:
                break;
        }
    }

    private Bitmap generateBitmap(String content, int width, int height) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        Map<EncodeHintType, String> hints = new HashMap<>();
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            BitMatrix encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints);
            int[] pixels = new int[width * height];
            for (int i = 0; i < height; i++) {
                for (int j = 0; j < width; j++) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000;
                    } else {
                        pixels[i * width + j] = 0xffffffff;
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565);
        } catch (WriterException e) {
            e.printStackTrace();
        }
        return null;
    }
}
