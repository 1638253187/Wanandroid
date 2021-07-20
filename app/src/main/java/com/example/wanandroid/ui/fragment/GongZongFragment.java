package com.example.wanandroid.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.VpAdapter;
import com.example.wanandroid.base.BaseMvpFargment;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.GzTabBean;
import com.example.wanandroid.model.GZTabModel;
import com.example.wanandroid.presenter.GzTabPresenter;
import com.example.wanandroid.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class GongZongFragment extends BaseMvpFargment<GzTabPresenter, GZTabModel, BaseView<GzTabBean, String>> implements BaseView<GzTabBean, String> {


    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp)
    ViewPager vp;
    Unbinder unbinder;
    private ArrayList<GzTabBean.DataBean> tablist;
    private ArrayList<GzListFragment> mTabfragmentlist;
    private VpAdapter adapter;

    @Override
    protected void initView() {
        super.initView ();
        tablist = new ArrayList<> ();
        mTabfragmentlist = new ArrayList<> ();
        adapter = new VpAdapter (getChildFragmentManager (), tablist,mTabfragmentlist);
        vp.setAdapter (adapter);
        //  3. 关联TabLayout 和 ViewPager
        tab.setupWithViewPager (vp);
        initData ();
    }

    @Override
    protected void initData() {
        super.initData ();
        presenter.getTab ();

    }

    @Override
    protected void initListener() {
        super.initListener ();
    }

    @Override
    protected BaseView<GzTabBean, String> initMvpView() {
        return this;
    }

    @Override
    protected GZTabModel initMvpModel() {
        return new GZTabModel ();
    }

    @Override
    protected GzTabPresenter initMvpPresenter() {
        return new GzTabPresenter ();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gong_zong;
    }

    @Override
    public void onSuccess(GzTabBean gzTabBean) {
        List<GzTabBean.DataBean> data = gzTabBean.getData ();
        tablist.addAll (data);
        try {
            // 6. 根据Tab栏数据的个数去创建对应的Fragment，并将id传递
            for (int i = 0; i < tablist.size (); i++) {
                // 获取Tab的每个数据
                GzTabBean.DataBean dataBean = tablist.get (i);
                // 创建每个Tab对应的Fragment，并传递id
                Bundle bundle = new Bundle ();
                bundle.putInt ("id",  dataBean.getId ());
                bundle.putString ("name",  dataBean.getName ());
                GzListFragment xiangMutabFragment = new GzListFragment ();
                xiangMutabFragment.setArguments (bundle);
                mTabfragmentlist.add (xiangMutabFragment);
            }
            adapter.notifyDataSetChanged ();
        } catch (Exception e) {
            e.printStackTrace ();
        }
    }

    @Override
    public void onFail(String s) {
        ToastUtil.showShort (s);
    }
}
