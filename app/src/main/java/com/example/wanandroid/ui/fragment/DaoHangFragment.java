package com.example.wanandroid.ui.fragment;


import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.VpAdapter_two;
import com.example.wanandroid.base.BaseMvpFargment;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.XmBean;
import com.example.wanandroid.model.XmModel;
import com.example.wanandroid.presenter.XmPresenter;
import com.example.wanandroid.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaoHangFragment extends BaseMvpFargment<XmPresenter, XmModel, BaseView<XmBean, String>> implements BaseView<XmBean, String> {


    @BindView(R.id.tab)
    TabLayout tab;
    @BindView(R.id.vp)
    ViewPager vp;
    Unbinder unbinder;
    private ArrayList<XmBean.DataBean> list;
    private ArrayList<XmListFragment> xmListFragments;
    private VpAdapter_two adapter;

    public DaoHangFragment() {
        // Required empty public constructor
    }


    @Override
    protected void initView() {
        super.initView ();
//        LemonBubble.showRoundProgress(getActivity (), "等待中...");
        list = new ArrayList<> ();
        xmListFragments = new ArrayList<> ();
        tab.setupWithViewPager (vp);
        LinearLayout linearLayout= (LinearLayout) tab.getChildAt(0);
        linearLayout.setShowDividers(LinearLayout.SHOW_DIVIDER_MIDDLE);
        linearLayout.setDividerDrawable(ContextCompat.getDrawable(mContext,R.drawable.tablayout_center_line));
        linearLayout.setDividerPadding(0); //设置分割线间隔
        adapter = new VpAdapter_two (getChildFragmentManager (), list, xmListFragments);
        vp.setAdapter (adapter);
    }

    @Override
    protected void initData() {
        super.initData ();
        if (presenter != null)
            presenter.getXm ();
    }

    @Override
    protected void initListener() {
        super.initListener ();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dao_hang;
    }

    @Override
    protected BaseView initMvpView() {
        return this;
    }

    @Override
    protected XmModel initMvpModel() {
        return new XmModel ();
    }

    @Override
    protected XmPresenter initMvpPresenter() {
        return new XmPresenter ();
    }


    @Override
    public void onSuccess(XmBean xmBean) {
        List<XmBean.DataBean> data = xmBean.getData ();
        list.addAll (data);
        try {
            // 6. 根据Tab栏数据的个数去创建对应的Fragment，并将id传递
            for (int i = 0; i < list.size (); i++) {
                // 获取Tab的每个数据
                XmBean.DataBean dataBean = list.get (i);
                // 创建每个Tab对应的Fragment，并传递id
                Bundle bundle = new Bundle ();
                bundle.putInt ("id",  dataBean.getId ());
                XmListFragment xiangMutabFragment = new XmListFragment ();
                xiangMutabFragment.setArguments (bundle);
                xmListFragments.add (xiangMutabFragment);
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
