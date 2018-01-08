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
import com.example.coderlt.uibestpractice.View.MyImgButton;
import com.example.coderlt.uibestpractice.adapter.MyFragmentPagerAdapter;
import com.example.coderlt.uibestpractice.fragments.ContactsFragment;
import com.example.coderlt.uibestpractice.fragments.DashboardFragment;
import com.example.coderlt.uibestpractice.fragments.HomeFragment;
import com.example.coderlt.uibestpractice.fragments.UserFragment;

import java.util.ArrayList;
import java.util.List;

public class NavigationActivity extends AppCompatActivity implements View.OnClickListener{
    private MyImgButton navigateHome,navigateDashboard,navigateUser,navigateContacts;
    private MyImgButton selectedButton;
    private ViewPager viewPager;
    private FragmentManager fm;
    private List<Fragment> fragmentList;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;

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
        navigateHome=findViewById(R.id.img_button_home);
        navigateDashboard=findViewById(R.id.img_button_dashboard);
        navigateUser=findViewById(R.id.img_button_user);
        navigateContacts=findViewById(R.id.img_button_contacts);

        navigateHome.setOnClickListener(this);
        navigateDashboard.setOnClickListener(this);
        navigateUser.setOnClickListener(this);
        navigateContacts.setOnClickListener(this);

        navigateHome.setSelected();
        selectedButton=navigateHome;

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
    }
    @Override
    public void onClick(View v){
        switch (v.getId()){
            case R.id.img_button_home:
                navigateChange(v);
                break;
            case R.id.img_button_dashboard:
                navigateChange(v);
                break;
            case R.id.img_button_user:
                navigateChange(v);
                break;
            case R.id.img_button_contacts:
                navigateChange(v);
                break;
            default:
                break;
        }
    }

    private void navigateChange(View v){
        MyImgButton btn=(MyImgButton)v;
        selectedButton.setUnSelected();
        btn.setSelected();
        selectedButton=btn;
    }
}
