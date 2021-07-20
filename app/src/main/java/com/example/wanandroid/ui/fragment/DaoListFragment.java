package com.example.wanandroid.ui.fragment;


import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.NodeAdapter;
import com.example.wanandroid.base.BaseMvpFargment;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.DaoHBean;
import com.example.wanandroid.model.DaoModel;
import com.example.wanandroid.presenter.DaoPresenter;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import qdx.stickyheaderdecoration.NormalDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class DaoListFragment extends BaseMvpFargment<DaoPresenter, DaoModel, BaseView<DaoHBean, String>> implements BaseView<DaoHBean, String> {
    @BindView(R.id.recy)
    RecyclerView recy;
    Unbinder unbinder;
    private ArrayList<DaoHBean.DataBean> list;
    private NodeAdapter adapter;

    @Override
    protected void initView() {
        super.initView ();

        list = new ArrayList<> ();
        recy.setLayoutManager(new LinearLayoutManager (getActivity ()));
        adapter = new NodeAdapter (getActivity (), list);
        recy.setAdapter (adapter);
    }

    @Override
    protected void initData() {
        super.initData ();
        if (list==null){
            LemonBubble.showRoundProgress(getActivity (), "等待中...");
        }
        presenter.getDao ();
    }

    @Override
    protected void initListener() {
        super.initListener ();
    }

    @Override
    protected BaseView initMvpView() {
        return this;
    }

    @Override
    protected DaoModel initMvpModel() {
        return null;
    }

    @Override
    protected DaoPresenter initMvpPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_dao_list;
    }


    @Override
    public void onSuccess(DaoHBean daoHBean) {
        //获取数据集合
        final List<DaoHBean.DataBean> data = daoHBean.getData ();
        //设置粘性头布局。返回对应的头标题

        NormalDecoration decoration = new NormalDecoration () {
            @Override
            public String getHeaderName(int i) {
                return data.get (i).getName ();
            }
        };
        decoration.setHeaderContentColor (getResources ().getColor (R.color.colorGreen));
        decoration.setTextColor (getResources ().getColor (R.color.colorWhite));
        //设置item分割
        recy.addItemDecoration (decoration);
        //刷新数据
        list.addAll (data);
        adapter.notifyDataSetChanged ();
        LemonBubble.showRight(getActivity (), "完成", 1000);
        LemonBubble.hide();



    }

    @Override
    public void onFail(String s) {
        LemonBubble.showError(getActivity (), "网络不太好，请稍后再试", 1000);

    }
}
