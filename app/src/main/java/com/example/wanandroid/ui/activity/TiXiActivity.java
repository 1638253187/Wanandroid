package com.example.wanandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.TiXiVpAdapter;
import com.example.wanandroid.base.BaseMvpActivity;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.TiXiBean;
import com.example.wanandroid.model.TiXiModel;
import com.example.wanandroid.presenter.TiXiPresenter;
import com.example.wanandroid.ui.fragment.TiXiListFragment;
import com.example.wanandroid.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class TiXiActivity extends BaseMvpActivity<TiXiPresenter, TiXiModel, BaseView<TiXiBean, String>> implements BaseView<TiXiBean, String> {


    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp)
    ViewPager vp;
    @BindView(R.id.tv_toolbar)
    TextView tvToolbar;
    private ArrayList<TiXiBean.DataBean> list;
    private ArrayList<TiXiBean.DataBean.ChildrenBean> tablist;
    private ArrayList<TiXiListFragment> tiXiListFragments;
    private TiXiVpAdapter adapter;
    private String ids;
    private List<TiXiBean.DataBean.ChildrenBean> children;
    private String name;

    @Override
    public void initView() {
        super.initView ();
        Intent intent = getIntent ();
        Bundle bundl = intent.getExtras ();
        TiXiBean.DataBean data = (TiXiBean.DataBean) bundl.getSerializable ("data");
        children = data.getChildren ();
        name = data.getName ();
        tvToolbar.setText (name);
        list = new ArrayList<> ();
        tiXiListFragments = new ArrayList<> ();
        adapter = new TiXiVpAdapter (getSupportFragmentManager (), (ArrayList<TiXiBean.DataBean.ChildrenBean>) children, tiXiListFragments);
        vp.setAdapter (adapter);
        tab.setupWithViewPager (vp);
    }

    @Override
    public void initData() {
        super.initData ();
        presenter.getTiXi ();
    }

    @Override
    public void initListener() {
        super.initListener ();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_ti_xi;
    }

    @Override
    protected BaseView<TiXiBean, String> initMvpView() {
        return this;
    }

    @Override
    protected TiXiModel initMvpModel() {
        return new TiXiModel ();
    }

    @Override
    protected TiXiPresenter initMvpPresenter() {
        return new TiXiPresenter ();
    }


    @Override
    public void onSuccess(TiXiBean tiXiBean) {
        list.addAll (tiXiBean.getData ());
        for (int i = 0; i < children.size (); i++) {
            int id = children.get (i).getId ();
            TiXiListFragment tiXiListFragment = new TiXiListFragment();
            Bundle bundle = new Bundle ();
            bundle.putInt ("id", id);
            tiXiListFragment.setArguments (bundle);
            tiXiListFragments.add (tiXiListFragment);
        }
        adapter.notifyDataSetChanged ();
    }

    @Override
    public void onFail(String s) {
        ToastUtil.showShort (s);
    }

}
