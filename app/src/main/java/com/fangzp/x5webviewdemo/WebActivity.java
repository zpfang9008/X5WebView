package com.fangzp.x5webviewdemo;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.util.SparseIntArray;
import android.view.MenuItem;

import com.fangzp.x5webviewdemo.fragment.WebFileFragment;
import com.fangzp.x5webviewdemo.fragment.WebJsFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WebActivity extends AppCompatActivity {

    private BottomNavigationView mNavigation;
    private ViewPager mViewPager;

    private List<Fragment> mFragmentArrayList = new ArrayList<>();
    private SparseIntArray mSparseIntArray = new SparseIntArray();

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mViewPager.setCurrentItem(0, false);
                    return true;
                case R.id.navigation_dashboard:
                    mViewPager.setCurrentItem(1, false);
                    return true;
                case R.id.navigation_notifications:
                    mViewPager.setCurrentItem(2, false);
                    return true;
                default:
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        mNavigation = findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        initFragments();
    }

    private void initFragments() {
        mFragmentArrayList.add(WebJsFragment.newInstance());
        mFragmentArrayList.add(WebFileFragment.newInstance());
        mFragmentArrayList.add(WebJsFragment.newInstance());

        mSparseIntArray.put(0, R.id.navigation_home);
        mSparseIntArray.put(1, R.id.navigation_dashboard);
        mSparseIntArray.put(2, R.id.navigation_notifications);

        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        for (Fragment fragment : mFragmentArrayList) {
            viewPagerAdapter.addFragment(fragment, fragment.getTag());
        }

        mViewPager = findViewById(R.id.vp_main);
        mViewPager.setAdapter(viewPagerAdapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                mNavigation.setSelectedItemId(mSparseIntArray.get(position));
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
