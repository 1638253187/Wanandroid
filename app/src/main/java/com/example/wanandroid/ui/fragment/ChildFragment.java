package com.example.wanandroid.ui.fragment;


import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.TiXiVpAdapter;
import com.example.wanandroid.base.BaseMvpFargment;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.TiXiBean;
import com.example.wanandroid.bean.TiXiListBean;
import com.example.wanandroid.model.TiXiListModel;
import com.example.wanandroid.presenter.TiXiListPresenter;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class ChildFragment extends BaseMvpFargment<TiXiListPresenter, TiXiListModel, BaseView<TiXiListBean, String>> implements BaseView<TiXiListBean, String> {


    @BindView(R.id.tab)
    TabLayout tab;
    Unbinder unbinder;
    @BindView(R.id.vp)
    ViewPager vp;
    private ArrayList<TiXiListBean.DataBean.DatasBean> tablist;
    private ArrayList<TiXiBean.DataBean> list;
    private ArrayList<TiXiListFragment> tiXiListFragments;
    private TiXiVpAdapter adapter;

    @Override
    protected void initView() {
        super.initView ();
        int id = getArguments ().getInt ("id");
        tab.setupWithViewPager (vp);
        tablist = new ArrayList<> ();
        list = new ArrayList<> ();
        tiXiListFragments = new ArrayList<> ();
    }

    @Override
    protected void initData() {
        super.initData ();
    }

    @Override
    protected void initListener() {
        super.initListener ();
    }


    @Override
    protected BaseView<TiXiListBean, String> initMvpView() {
        return this;
    }

    @Override
    protected TiXiListModel initMvpModel() {
        return new TiXiListModel ();
    }

    @Override
    protected TiXiListPresenter initMvpPresenter() {
        return new TiXiListPresenter ();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_child;
    }

    @Override
    public void onSuccess(TiXiListBean tiXiListBean) {

    }

    @Override
    public void onFail(String s) {

    }
}
