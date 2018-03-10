package com.example.coderlt.uibestpractice.activity;

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
import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.SaveCallback;
import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMClientCallback;
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
import cn.leancloud.chatkit.LCChatKit;

public class NavigationActivity extends AppCompatActivity {
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
        clientId = getIntent().getStringExtra(Constant.USER.USER_ID);
        initViews();
        contactsLogin();

        // 测试 SDK 是否正常工作的代码
        AVObject testObject = new AVObject("TestObject");
        testObject.put("words","Hello World!");
        testObject.put("name","DanYuDev");
        testObject.saveInBackground(new SaveCallback() {
            @Override
            public void done(AVException e) {
                if(e == null){
                    Log.d("saved","success!");
                }
            }
        });
    }

    private void contactsLogin(){
        LCChatKit.getInstance().open(clientId, new AVIMClientCallback() {
            @Override
            public void done(AVIMClient avimClient, AVIMException e) {
                if (null == e) {
                    Utils.showToast(clientId+" logined.");
                } else {
                    Utils.showToast(clientId+" logined failed.\n"+e.toString());
                }
            }
        });
    }

    private void initViews(){

        bottomNavigationView=findViewById(R.id.bottom_navigation_view);
        navigateHome=new MyImgButton(this);
        navigateDashboard=new MyImgButton(this);
        navigateContacts=new MyImgButton(this);
        navigateUser = new MyImgButton(this);

        int selectedColor=getResources().getColor(R.color.main_blue);
        int unselectedColor=getResources().getColor(R.color.line_gray);

        navigateHome.setSelectedImgId(R.drawable.ic_home_selected);
        navigateHome.setUnSelectedImgId(R.drawable.ic_home_unselected);
        navigateHome.setSelectedTextColor(selectedColor);
        navigateHome.setUnSelectedTextColor(unselectedColor);
        navigateHome.setText("Home");
        navigateHome.setUnSelected();

        navigateDashboard.setSelectedImgId(R.drawable.ic_dashboard_selected);
        navigateDashboard.setUnSelectedImgId(R.drawable.ic_dashboard_unselected);
        navigateDashboard.setSelectedTextColor(selectedColor);
        navigateDashboard.setUnSelectedTextColor(unselectedColor);
        navigateDashboard.setText("Dashboard");
        navigateDashboard.setUnSelected();

        navigateContacts.setSelectedImgId(R.drawable.ic_contacts_selected);
        navigateContacts.setUnSelectedImgId(R.drawable.ic_contacts_unselected);
        navigateContacts.setSelectedTextColor(selectedColor);
        navigateContacts.setUnSelectedTextColor(unselectedColor);
        navigateContacts.setText("Contacts");
        navigateContacts.setUnSelected();

        navigateUser.setSelectedImgId(R.drawable.ic_user_selected);
        navigateUser.setUnSelectedImgId(R.drawable.ic_user_unselected);
        navigateUser.setSelectedTextColor(selectedColor);
        navigateUser.setUnSelectedTextColor(unselectedColor);
        navigateUser.setText("User");
        navigateUser.setUnSelected();

        btnList=new ArrayList<>();
        btnList.add(navigateHome);
        btnList.add(navigateDashboard);
        btnList.add(navigateContacts);
        btnList.add(navigateUser);

        bottomNavigationView.setBtnList(btnList);
        bottomNavigationView.setCurrentItem(0);
        // init fragmentList
        viewPager=findViewById(R.id.navigation_view_pager);
        HomeFragment fh=new HomeFragment();
        DashboardFragment fd=new DashboardFragment();
        ContactsFragment fc=new ContactsFragment();
        //LCIMConversationListFragment fc = new LCIMConversationListFragment();
        UserFragment fu=new UserFragment();

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
    }
}
