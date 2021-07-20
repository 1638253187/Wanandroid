package com.example.wanandroid.ui.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.HomeAdapter;
import com.example.wanandroid.adapter.TiXiLAdapter;
import com.example.wanandroid.base.BaseMvpFargment;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.TiXiListBean;
import com.example.wanandroid.model.TiXiListModel;
import com.example.wanandroid.presenter.TiXiListPresenter;
import com.example.wanandroid.ui.activity.Web;
import com.example.wanandroid.util.ToastUtil;
import com.scwang.smartrefresh.layout.header.BezierRadarHeader;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TiXiListFragment extends BaseMvpFargment<TiXiListPresenter, TiXiListModel, BaseView<TiXiListBean, String>> implements BaseView<TiXiListBean, String> {

    @BindView(R.id.smart)
    BezierRadarHeader smart;
    @BindView(R.id.recy)
    RecyclerView recy;
    Unbinder unbinder;
    private ArrayList<TiXiListBean.DataBean.DatasBean> list;
    private int page=0;
    private int id;
    private TiXiLAdapter adapter;

    @Override
    protected void initView() {
        super.initView ();
        id = getArguments ().getInt ("id");
        list = new ArrayList<> ();
        recy.setLayoutManager (new LinearLayoutManager (getActivity ()));
        adapter = new TiXiLAdapter (getActivity (), list);
        recy.setAdapter (adapter);
    }

    @Override
    protected void initData() {
        super.initData ();
        presenter.getList (page,id);
    }

    @Override
    protected void initListener() {
        super.initListener ();
        adapter.setOnItemCliclListener (new HomeAdapter.OnItemCliclListener () {
            @Override
            public void onItemClick(int position) {
                TiXiListBean.DataBean.DatasBean datasBean = list.get (position);
                String link = datasBean.getLink ();
                Intent intent = new Intent (getActivity (), Web.class);
                intent.putExtra ("link",link);
                startActivity (intent);
            }
        });
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
        return R.layout.fragment_ti_xi_list;
    }

    @Override
    public void onSuccess(TiXiListBean tiXiListBean) {
        list.addAll (tiXiListBean.getData ().getDatas ());
        adapter.notifyDataSetChanged ();
    }

    @Override
    public void onFail(String s) {
        ToastUtil.showShort (s);
    }


}
