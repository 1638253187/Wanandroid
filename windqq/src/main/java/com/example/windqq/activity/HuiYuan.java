package com.example.windqq.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.example.windqq.R;
import com.example.windqq.activity.fragment.Hui_Fragment_Five;
import com.example.windqq.activity.fragment.Hui_Fragment_Fow;
import com.example.windqq.activity.fragment.Hui_Fragment_One;
import com.example.windqq.activity.fragment.Hui_Fragment_Three;
import com.example.windqq.activity.fragment.Hui_Fragment_Tow;

import java.util.ArrayList;

public class HuiYuan extends AppCompatActivity implements View.OnClickListener {

    private TabLayout mTab;
    private ViewPager mVp;
    private ImageView mIvBack;
    private ImageView mIvUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_hui_yuan);
        initView ();
    }

    private void initView() {
        mTab = (TabLayout) findViewById (R.id.tab);
        mVp = (ViewPager) findViewById (R.id.vp);
        mIvBack = (ImageView) findViewById (R.id.iv_back);
        mIvUser = (ImageView) findViewById (R.id.iv_user);
        mIvUser.setOnClickListener (this);
        mIvBack.setOnClickListener (this);
        final ArrayList<Fragment> fragments = new ArrayList<> ();
        fragments.add (new Hui_Fragment_One ());
        fragments.add (new Hui_Fragment_Tow ());
        fragments.add (new Hui_Fragment_Three ());
        fragments.add (new Hui_Fragment_Fow ());
        fragments.add (new Hui_Fragment_Five ());
        mVp.setAdapter (new FragmentPagerAdapter (getSupportFragmentManager ()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get (i);
            }

            @Override
            public int getCount() {
                return fragments.size ();
            }
        });
        mTab.setupWithViewPager (mVp);
        mTab.getTabAt (0).setText ("装扮");
        mTab.getTabAt (1).setText ("功能");
        mTab.getTabAt (2).setText ("游戏");
        mTab.getTabAt (3).setText ("书漫");
        mTab.getTabAt (4).setText ("影视");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId ()) {
            case R.id.iv_back:
                finish ();
                break;
            case R.id.iv_user:
                String url = "mqqapi://card/show_pslcard?src_type=internal&version=1&uin=" + 1362318797
                        + "&card_type=person&source=qrcode";
                startActivity (new Intent (Intent.ACTION_VIEW, Uri.parse (url)));
                finish ();
                break;
        }
    }
}
