package com.example.coderlt.uibestpractice.activity;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.coderlt.uibestpractice.R;
import com.example.coderlt.uibestpractice.adapter.MyFragmentPagerAdapter;
import com.example.coderlt.uibestpractice.fragments.BlankFragment;
import com.example.coderlt.uibestpractice.fragments.BlankFragment2;

import java.util.ArrayList;
import java.util.List;

/**
 * learn from jianshu
 * @link https://www.jianshu.com/p/e5abbda4a71c
 */

public class FragmentActivity extends AppCompatActivity implements
        BlankFragment.OnFragmentInteractionListener,BlankFragment2.OnFragmentInteractionListener{
    private Fragment f1,f2;
    private FragmentManager fm;
    private List<Fragment> fragments;
    private ViewPager viewPager;
    private MyFragmentPagerAdapter myFragmentPagerAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment);

        f1= new BlankFragment();
        f2= new BlankFragment2();
        fragments=new ArrayList<>();
        fragments.add(f1);
        fragments.add(f2);


        fm=getSupportFragmentManager();
        viewPager=findViewById(R.id.fragment_view_pager);
        myFragmentPagerAdapter=new MyFragmentPagerAdapter(fm,fragments);
        viewPager.setAdapter(myFragmentPagerAdapter);
    }

    @Override
    public void onFragmentInteraction(Uri uri){
       // switch..  more than one fragment associate with this activity
    }

}
