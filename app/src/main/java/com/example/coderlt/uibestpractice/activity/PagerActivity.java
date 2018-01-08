package com.example.coderlt.uibestpractice.activity;

import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.View.MyTab;
import com.example.coderlt.uibestpractice.adapter.MyPagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class PagerActivity extends AppCompatActivity {
    private static float MIN_SCALE = 0.65f;

    private View v1,v2,v3;
    private List<View> viewList;
    private List<String> titleList;
    private ViewPager viewPager;  //pager的意思是呼叫机，页面等程序
    private MyTab myTab;
    private MyPagerAdapter myPagerAdapter;
    private final String TAG=getClass().getName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.main_blue));
                //底部导航栏
                //window.setNavigationBarColor(activity.getResources().getColor(colorResId));
                //window.setBackgroundDrawableResource(R.drawable.shadow_gradient);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        setContentView(R.layout.activity_pager);

        v1= LayoutInflater.from(this).inflate(R.layout.pager_layout_1,null);
        v2= LayoutInflater.from(this).inflate(R.layout.pager_layout_2,null);
        v3= LayoutInflater.from(this).inflate(R.layout.pager_layout_3,null);
        v1.setTag("v1");
        v2.setTag("v2");
        v3.setTag("v3");

        viewList=new ArrayList<>();
        viewList.add(v1);
        viewList.add(v2);
        viewList.add(v3);

        titleList=new ArrayList<>();
        titleList.add("董事部");
        titleList.add("事业发展部");
        titleList.add("科技研发部");

        viewPager=findViewById(R.id.view_pager);
        myTab=findViewById(R.id.my_tab);
        myTab.setTitleList(titleList);
        myTab.setBackgroundResource(R.color.main_blue);
        myTab.setSelectColor(R.drawable.tab_bg);
        myTab.selectTab(0);
        myPagerAdapter=new MyPagerAdapter(viewList);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset,
                                       int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                myTab.selectTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        myTab.setOnTabSelectedListener(new MyTab.OnTabSelectedListenr() {
            @Override
            public void onTabSelected(int position) {
                viewPager.setCurrentItem(position);
            }
        });


        viewPager.setPageTransformer(true, new ViewPager.PageTransformer() {
            @Override
            public void transformPage(View view, float position) {

                int pageWidth = view.getWidth();
                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0);
                } else if (position <= 0) { // [-1,0]
                    // Use the default slide transition when moving to the left page
                    view.setAlpha(1);
                    view.setTranslationX(0);
                    view.setScaleX(1);
                    view.setScaleY(1);
                } else if (position <= 1) { // (0,1]
                    // Fade the page out.
                    view.setAlpha(1 - position);
                    // Counteract the default slide transition
                    //view.setTranslationX(pageWidth * -position);
                    // Scale the page down (between MIN_SCALE and 1)
                    float scaleFactor = MIN_SCALE
                            + (1 - MIN_SCALE) * (1 - Math.abs(position));
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);
                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.setAlpha(0);
                }

                /*
                view.setTranslationX(view.getWidth() * -position);

                if(position <= -1.0F || position >= 1.0F) {
                    view.setAlpha(0.0F);
                } else if( position == 0.0F ) {
                    view.setAlpha(1.0F);
                } else {
                    // position is between -1.0F & 0.0F OR 0.0F & 1.0F
                    view.setAlpha(1.0F - Math.abs(position));
                }*/
            }
        });
        //viewPager.setPageTransformer(true,new ZoomInTransform());

        viewPager.setAdapter(myPagerAdapter);

    }
}
