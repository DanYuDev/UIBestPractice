package com.example.coderlt.uibestpractice.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.adapter.MyFragmentPagerAdapter;
import com.example.coderlt.uibestpractice.fragments.ContactsFragment;
import com.example.coderlt.uibestpractice.fragments.HomeFragment;
import com.example.coderlt.uibestpractice.fragments.OrderFragment;
import com.example.coderlt.uibestpractice.fragments.UserFragment;
import com.example.coderlt.uibestpractice.utils.Utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class TechnicianNavigationActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener{
    private BottomNavigationView  bottomNavigationView;
    private ViewPager viewPager;
    private List<Fragment> fragments;
    private MyFragmentPagerAdapter adapter;
    private FragmentManager fm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_technician_navigation);
        Utils.showToast("Welcome Technician Part.");

        initViews();
    }

    private void initViews(){
        bottomNavigationView = findViewById(R.id.bottom_navigation_view);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        final View[] menuItems = new View[4];
        menuItems[0]=bottomNavigationView.findViewById(R.id.home);
        menuItems[1]=bottomNavigationView.findViewById(R.id.contacts);
        menuItems[2]=bottomNavigationView.findViewById(R.id.order);
        menuItems[3]=bottomNavigationView.findViewById(R.id.user);
        viewPager = findViewById(R.id.view_pager);
        fm = getSupportFragmentManager();
        fragments = new ArrayList<>();
        HomeFragment homeFragment = new HomeFragment();
        OrderFragment orderFragment = new OrderFragment();
        ContactsFragment contactsFragment = new ContactsFragment();
        UserFragment userFragment = new UserFragment();
        fragments.add(homeFragment);
        fragments.add(contactsFragment);
        fragments.add(orderFragment);
        fragments.add(userFragment);
        adapter = new MyFragmentPagerAdapter(fm,fragments);
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                menuItems[position].performClick();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item){
        switch (item.getItemId()){
            case R.id.home:
                viewPager.setCurrentItem(0);
                break;
            case R.id.contacts:
                viewPager.setCurrentItem(1);
                break;
            case R.id.order:
                viewPager.setCurrentItem(2);
                break;
            case R.id.user:
                viewPager.setCurrentItem(3);
                break;
            default:
                break;
        }
        return true;
    }
}
