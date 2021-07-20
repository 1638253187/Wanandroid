package com.example.byebur;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.byebur.fragment.ByeFragment;
import com.example.byebur.util.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private NoScrollViewPager mNoScrollViewPager;
    private BottomNavigationView mBottomNavigationView;
    private Toolbar mToolbar;
    private CoordinatorLayout mActivityMain;
    private List<Fragment> fragList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        initView ();
        initViews ();
        fragList = new ArrayList<> ();
        fragList.add (new ByeFragment ());
        fragList.add (new ByeFragment ());
        fragList.add (new ByeFragment ());
        fragList.add (new ByeFragment ());
        mNoScrollViewPager.setAdapter (new FragmentPagerAdapter (getSupportFragmentManager ()) {
            @Override
            public Fragment getItem(int position) {
                return fragList.get (position);
            }

            @Override
            public int getCount() {
                return fragList.size ();
            }
        });


    }


    private void initViews() {
        initBottomNavigationView ();
    }




    private void initBottomNavigationView() {
        mBottomNavigationView.setOnNavigationItemSelectedListener (new BottomNavigationView.OnNavigationItemSelectedListener () {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId ()) {
                    case R.id.tab1:
                        mNoScrollViewPager.setCurrentItem (0);
                        break;
                    case R.id.tab2:
                        mNoScrollViewPager.setCurrentItem (1);
                        break;
                    case R.id.tab3:
                        mNoScrollViewPager.setCurrentItem (2);
                        break;
                    case R.id.tab4:
                        mNoScrollViewPager.setCurrentItem (3);
                        break;
                }
                return true;
            }
        });
    }


    private void initView() {
        mNoScrollViewPager = (NoScrollViewPager) findViewById (R.id.noScrollViewPager);
        mBottomNavigationView = (BottomNavigationView) findViewById (R.id.bottom_navigation_view);
        mToolbar = (Toolbar) findViewById (R.id.toolbar);
        mActivityMain = (CoordinatorLayout) findViewById (R.id.activity_main);
    }
}
