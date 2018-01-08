package com.example.coderlt.uibestpractice.activity;

import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.coderlt.uibestpractice.bean.Msg;
import com.example.coderlt.uibestpractice.adapter.MyAdapter;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.View.MyDialog;
import com.example.coderlt.uibestpractice.View.ProgressWheel;
import com.example.coderlt.uibestpractice.View.VoiceButton;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity implements View.OnClickListener{
    private String[] machine=new String[]{"Hello","Right","Ok","always","When I was a child,I miss you so mach "
    ,"Go home pls.","下周去绍兴玩玩","喊我干嘛 = =","我是机器人","可以啊","好想吃辣条啊","我喜欢在晚上看月亮","有时候月亮躲起来了"};
    private boolean recordStatus=false;
    private File recorderFile;
    private ImageButton recordBtn;
    private RecyclerView chatRecyclerView;
    private EditText editText;
    private FrameLayout contentFrame;
    private VoiceButton voiceButton;
    private Button sendBtn;
    private MyDialog recordDialog;
    private ProgressWheel progressWheel;
    private List<Msg> msgList;
    private MyAdapter myAdapter;
    private LinearLayoutManager layoutManager=new LinearLayoutManager(this);
    private final String TAG=getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        initViews();

        msgList=new ArrayList<Msg>();
        myAdapter=new MyAdapter(msgList);
        chatRecyclerView.setLayoutManager(layoutManager);
        chatRecyclerView.setAdapter(myAdapter);
    }

    private void initViews(){
        chatRecyclerView=findViewById(R.id.chat_recycler_view);
        sendBtn=findViewById(R.id.send_btn);
        recordBtn=findViewById(R.id.record_btn);
        contentFrame=findViewById(R.id.content_layout);
        sendBtn.setOnClickListener(this);
        recordBtn.setOnClickListener(this);

        editText=new EditText(this);
        //editText.setGravity(Gravity.BOTTOM);
        editText.setPadding(0,0,0,2);
        editText.setTextSize(13);
        editText.setBackgroundResource(R.drawable.edit_bg);
        try{
        recorderFile=new File(Environment.getExternalStorageDirectory().getCanonicalPath()+
                "/HpRecorder.amr");
        }catch(IOException ex){
            ex.printStackTrace();
        }
        voiceButton=new VoiceButton(this);
        voiceButton.setFilePath(recorderFile.getAbsolutePath());

        voiceButton.setVoiceListener(new VoiceButton.VoiceListener() {
            @Override
            public void onStart() {
                Toast.makeText(ChatActivity.this,"Start Recording...",Toast.LENGTH_SHORT)
                        .show();
                //ViewGroup contentView=findViewById(R.id.chat_main_layout);
                //progressWheel=new ProgressWheel(ChatActivity.this);
                //contentView.addView(progressWheel);
                recordDialog=new MyDialog(ChatActivity.this,R.style.Dialog);
                recordDialog.setLayoutResId(R.layout.dialog_layout);
                recordDialog.show();
            }

            @Override
            public void onFinish(String audioPath) {
                recordDialog.dismiss();
                Msg msg=new Msg();
                msg.setType(Msg.VOICE);
                msg.setMsg(audioPath);
                msgList.add(msg);
                myAdapter.notifyDataSetChanged();
                chatRecyclerView.scrollToPosition(msgList.size()-1);
            }
        });

        contentFrame.addView(editText);
    }

    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.send_btn:
                String textMsg=editText.getText().toString().trim();
                if(textMsg.equals(""))
                    Toast.makeText(this,"Msg is empty!",Toast.LENGTH_SHORT)
                    .show();
                else{
                    Log.d(TAG,"You type words.");
                    Msg msg=new Msg();
                    msg.setMsg(textMsg);
                    msg.setType(Msg.MINE);
                    msgList.add(msg);

                    Msg msg1=new Msg();
                    msg1.setMsg(machine[((int)(Math.random()*10))%13]);
                    msg1.setType(Msg.OTHER);
                    editText.setText("");
                    msgList.add(msg1);
                    myAdapter.notifyDataSetChanged();
                    chatRecyclerView.scrollToPosition(msgList.size()-1);
                }
                break;
            case R.id.record_btn:
                if(recordStatus==false) {
                    recordStatus=true;
                    recordBtn.setBackgroundResource(R.drawable.ic_edit);
                    contentFrame.removeAllViews();
                    contentFrame.addView(voiceButton);
                }else{
                    recordStatus=false;
                    recordBtn.setBackgroundResource(R.drawable.ic_voice);
                    contentFrame.removeAllViews();
                    contentFrame.addView(editText);
                }
                break;
            default:
                break;
        }
    }
}
