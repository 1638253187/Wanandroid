package com.example.wanandroid.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.wanandroid.bean.DaoHBean;
import com.example.wanandroid.ui.fragment.DaoListFragment;

import java.util.ArrayList;

public class VpAdapters extends FragmentStatePagerAdapter {

    private ArrayList<DaoListFragment> fragmentlist;
    private ArrayList<DaoHBean.DataBean> list;

    public VpAdapters(FragmentManager fm, ArrayList<DaoListFragment> fragmentlist, ArrayList<DaoHBean.DataBean> list) {
        super(fm);
        this.fragmentlist = fragmentlist;
        this.list = list;
    }

    @Override
    public Fragment getItem(int i) {
        return fragmentlist.get(i);
    }

    @Override
    public int getCount() {
        return fragmentlist.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return list.get(position).getName();
    }
}