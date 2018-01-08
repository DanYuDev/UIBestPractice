package com.example.coderlt.uibestpractice.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;

import com.example.coderlt.uibestpractice.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private Button chatButton;
    private Button popBtn;
    private Button graphBtn;
    private Button pagerBtn;
    private Button fragmentPagerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chatButton=(Button)findViewById(R.id.chat_skip_btn);
        popBtn=(Button)findViewById(R.id.pop_skip_btn);
        graphBtn=(Button)findViewById(R.id.skip_graph);
        pagerBtn=findViewById(R.id.skip_pager);
        fragmentPagerButton=findViewById(R.id.skip_fragment_pager);

        chatButton.setOnClickListener(this);
        popBtn.setOnClickListener(this);
        graphBtn.setOnClickListener(this);
        pagerBtn.setOnClickListener(this);
        fragmentPagerButton.setOnClickListener(this);

        Animation animation= AnimationUtils.loadAnimation(this,R.anim.anim_exit_1);
        //pagerBtn.startAnimation(animation);
    }

    @Override
    public void onClick(View v){
        switch(v.getId()){
            case R.id.chat_skip_btn:
                startActivity(new Intent(this,ChatActivity.class));
                overridePendingTransition(R.anim.anim_enter_2,R.anim.anim_exit_2);
                break;
            case R.id.pop_skip_btn:
                startActivity(new Intent(this,PopwndActivity.class));
                break;
            case R.id.skip_graph:
                startActivity(new Intent(this,GraphActivity.class));
                break;
            case R.id.skip_pager:
                startActivity(new Intent(this,PagerActivity.class));
                overridePendingTransition(R.anim.anim_enter_1,R.anim.anim_exit_1);
                break;
            case R.id.skip_fragment_pager:
                startActivity(new Intent(this,FragmentActivity.class));
                break;
            default:
                break;
        }
    }

    @Override
    public void finish(){
        super.finish();
        overridePendingTransition(R.anim.anim_enter_1,R.anim.anim_exit_1);
    }
}
