package com.example.coderlt.uibestpractice.activity;

import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends AppCompatActivity {
    private MyImgButton navigateHome,navigateDashboard,navigateUser,navigateContacts;
    private List<MyImgButton> btnList;
    private ViewPager viewPager;
    private FragmentManager fm;
    private List<Fragment> fragmentList;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    private BottomNavigationView bottomNavigationView;

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
        setContentView(R.layout.activity_navigation);

        initViews();
    }

    private void initViews(){
        bottomNavigationView=findViewById(R.id.bottom_navigation_view);
        navigateHome=new MyImgButton(this);
        navigateDashboard=new MyImgButton(this);
        navigateContacts=new MyImgButton(this);
        navigateUser = new MyImgButton(this);

        navigateHome.setSelectedImgId(R.drawable.ic_home_selected);
        navigateHome.setUnSelectedImgId(R.drawable.ic_home_unselected);
        navigateHome.setSelectedTextColor(R.color.main_blue);
        navigateHome.setUnSelectedTextColor(R.color.line_gray);
        navigateHome.setText("Home");
        navigateHome.setUnSelected();

        navigateDashboard.setSelectedImgId(R.drawable.ic_dashboard_selected);
        navigateDashboard.setUnSelectedImgId(R.drawable.ic_dashboard_unselected);
        navigateDashboard.setSelectedTextColor(R.color.main_blue);
        navigateDashboard.setUnSelectedTextColor(R.color.line_gray);
        navigateDashboard.setText("Dashboard");
        navigateDashboard.setUnSelected();

        navigateContacts.setSelectedImgId(R.drawable.ic_contacts_selected);
        navigateContacts.setUnSelectedImgId(R.drawable.ic_contacts_unselected);
        navigateContacts.setSelectedTextColor(R.color.main_blue);
        navigateContacts.setUnSelectedTextColor(R.color.line_gray);
        navigateContacts.setText("Contacts");
        navigateContacts.setUnSelected();

        navigateUser.setSelectedImgId(R.drawable.ic_user_selected);
        navigateUser.setUnSelectedImgId(R.drawable.ic_user_unselected);
        navigateUser.setSelectedTextColor(R.color.main_blue);
        navigateUser.setUnSelectedTextColor(R.color.line_gray);
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
    }
}
