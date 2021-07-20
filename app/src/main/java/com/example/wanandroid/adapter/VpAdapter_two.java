package com.example.wanandroid.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.wanandroid.bean.GzTabBean;
import com.example.wanandroid.bean.XmBean;
import com.example.wanandroid.ui.fragment.GzListFragment;
import com.example.wanandroid.ui.fragment.XmListFragment;

import java.util.ArrayList;

/**
 * Created by kc on 2019/4/19.
 */

public class VpAdapter_two extends FragmentStatePagerAdapter {

    private ArrayList<XmBean.DataBean> tablist;
    private ArrayList<XmListFragment> fragmentlist;

    public VpAdapter_two(FragmentManager fm, ArrayList<XmBean.DataBean> tablist, ArrayList<XmListFragment> fragmentlist) {
        super (fm);
        this.tablist = tablist;
        this.fragmentlist = fragmentlist;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentlist.get (i);
    }

    @Override
    public int getCount() {
        return fragmentlist.size ();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return tablist.get (position).getName ();
    }
}