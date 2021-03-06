package com.example.coderlt.uibestpractice.activity;

import android.content.res.Resources;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.View.BottomNavigationView;
import com.example.coderlt.uibestpractice.View.MyImgButton;
import com.example.coderlt.uibestpractice.adapter.MyFragmentPagerAdapter;
import com.example.coderlt.uibestpractice.fragments.ContactsFragment;
import com.example.coderlt.uibestpractice.fragments.DashboardFragment;
import com.example.coderlt.uibestpractice.fragments.HomeFragment;
import com.example.coderlt.uibestpractice.fragments.UserFragment;
import com.example.coderlt.uibestpractice.utils.Constant;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends AppCompatActivity {
    private static final String TAG = "NavigationActivity";
    private MyImgButton navigateHome,navigateDashboard,navigateUser,navigateContacts;
    private List<MyImgButton> btnList;
    private ViewPager viewPager;
    private FragmentManager fm;
    private List<Fragment> fragmentList;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private BottomNavigationView bottomNavigationView;
    private static final float MIN_SCALE=0.7f;
    public String clientId ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d("TimeTest","Time 0:"+System.currentTimeMillis());
        super.onCreate(savedInstanceState);
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(getResources().getColor(R.color.main_blue));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        setContentView(R.layout.activity_navigation);
        Utils.showToast("欢迎进入老板端");
        Log.d("TimeTest","Time 1:"+System.currentTimeMillis());
        clientId = getIntent().getStringExtra(Constant.USER.USER_ID);
        Log.d(TAG,"clientId is : "+clientId);
        initViews();
        Log.d("TimeTest","Time 5 :"+System.currentTimeMillis());
        // 测试 SDK 是否正常工作的代码
//        AVObject testObject = new AVObject("TestObject");
//        testObject.put("words","Hello World!");
//        testObject.put("name","DanYuDev");
//        testObject.saveInBackground(new SaveCallback() {
//            @Override
//            public void done(AVException e) {
//                if(e == null){
//                    Log.d("saved","success!");
//                }
//            }
//        });
    }

    private void initViews(){
        Log.d("TimeTest","Time 2:"+System.currentTimeMillis());
        bottomNavigationView=findViewById(R.id.bottom_navigation_view);
        navigateHome=new MyImgButton(this);
        navigateDashboard=new MyImgButton(this);
        navigateContacts=new MyImgButton(this);
        navigateUser = new MyImgButton(this);

        Resources res = getResources();
        //int selectedColor=getResources().getColor(R.color.main_blue);
        int selectedColor = Color.parseColor("#1E8BE8");
        int unselectedColor=Color.parseColor("#CCCCCC");

        navigateHome.setSelectedImgId(R.drawable.ic_home_selected);
        navigateHome.setUnSelectedImgId(R.drawable.ic_home_unselected);
        navigateHome.setSelectedTextColor(selectedColor);
        navigateHome.setUnSelectedTextColor(unselectedColor);
        navigateHome.setText("主页");
        navigateHome.setUnSelected();

        navigateDashboard.setSelectedImgId(R.drawable.ic_dashboard_selected);
        navigateDashboard.setUnSelectedImgId(R.drawable.ic_dashboard_unselected);
        navigateDashboard.setSelectedTextColor(selectedColor);
        navigateDashboard.setUnSelectedTextColor(unselectedColor);
        navigateDashboard.setText("报表");
        navigateDashboard.setUnSelected();

        navigateContacts.setSelectedImgId(R.drawable.ic_contacts_selected);
        navigateContacts.setUnSelectedImgId(R.drawable.ic_contacts_unselected);
        navigateContacts.setSelectedTextColor(selectedColor);
        navigateContacts.setUnSelectedTextColor(unselectedColor);
        navigateContacts.setText("聊天");
        navigateContacts.setUnSelected();

        navigateUser.setSelectedImgId(R.drawable.ic_user_selected);
        navigateUser.setUnSelectedImgId(R.drawable.ic_user_unselected);
        navigateUser.setSelectedTextColor(selectedColor);
        navigateUser.setUnSelectedTextColor(unselectedColor);
        navigateUser.setText("个人");
        navigateUser.setUnSelected();

        btnList=new ArrayList<>();
        btnList.add(navigateHome);
        btnList.add(navigateDashboard);
        btnList.add(navigateContacts);
        btnList.add(navigateUser);

        bottomNavigationView.setBtnList(btnList);
        bottomNavigationView.setCurrentItem(0);

        Log.d("TimeTest","Time 3:"+System.currentTimeMillis());
        // init fragmentList
        viewPager=findViewById(R.id.navigation_view_pager);
        HomeFragment fh=new HomeFragment();
        DashboardFragment fd=new DashboardFragment();
        ContactsFragment fc=new ContactsFragment();
        //LCIMConversationListFragment fc = new LCIMConversationListFragment();
        UserFragment fu=new UserFragment();

        Log.d("TimeTest","Time 4:"+System.currentTimeMillis());

        fragmentList=new ArrayList<Fragment>();
        fragmentList.add(fh);
        fragmentList.add(fd);
        fragmentList.add(fc);
        fragmentList.add(fu);

        fm=getSupportFragmentManager();
        myFragmentPagerAdapter=new MyFragmentPagerAdapter(fm,fragmentList);
        viewPager.setAdapter(myFragmentPagerAdapter);
        viewPager.setCurrentItem(0);

        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                //
            }

            @Override
            public void onPageSelected(int position) {
                bottomNavigationView.setCurrentItem(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                //
            }
        });

        bottomNavigationView.setOnBtnSelectedListener(new BottomNavigationView.OnBtnSelectedListenr() {
            @Override
            public void onBtnSelected(int position) {
                viewPager.setCurrentItem(position,true);
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
            }
        });
        //viewPager.setPageTransformer(true,new ZoomInTransform());
    }

    @Override
    public void onResume(){
        super.onResume();
        Log.d("TimeTest","onResume current time:"+System.currentTimeMillis());
    }
}
