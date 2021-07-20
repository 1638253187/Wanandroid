package com.example.wanandroid.adapter;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.wanandroid.bean.GzTabBean;
import com.example.wanandroid.bean.TiXiBean;
import com.example.wanandroid.bean.TiXiListBean;
import com.example.wanandroid.ui.fragment.ChildFragment;
import com.example.wanandroid.ui.fragment.GzListFragment;
import com.example.wanandroid.ui.fragment.TiXiListFragment;

import java.util.ArrayList;

/**
 * Created by kc on 2019/4/19.
 */

public class TiXiVpAdapter extends FragmentStatePagerAdapter {

    private ArrayList<TiXiBean.DataBean.ChildrenBean> tablist;
    private ArrayList<TiXiListFragment> fragmentlist;

    public TiXiVpAdapter(FragmentManager fm, ArrayList<TiXiBean.DataBean.ChildrenBean> tablist, ArrayList<TiXiListFragment> fragmentlist) {
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