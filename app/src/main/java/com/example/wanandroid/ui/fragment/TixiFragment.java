package com.example.wanandroid.ui.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.TiXiAdapter;
import com.example.wanandroid.base.BaseApp;
import com.example.wanandroid.base.BaseMvpFargment;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.TiXiBean;
import com.example.wanandroid.model.TiXiModel;
import com.example.wanandroid.presenter.TiXiPresenter;
import com.example.wanandroid.ui.activity.TiXiActivity;
import com.example.wanandroid.util.AnimatorUtil;
import com.example.wanandroid.util.AnimatorUtil_two;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class TixiFragment extends BaseMvpFargment<TiXiPresenter, TiXiModel, BaseView<TiXiBean, String>> implements BaseView<TiXiBean, String>, OnRefreshLoadMoreListener {

    @BindView(R.id.recy)
    RecyclerView recy;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    Unbinder unbinder;
    private ArrayList<TiXiBean.DataBean.ChildrenBean> list_two;
    private ArrayList<TiXiBean.DataBean> list;
    private TiXiAdapter adapter;
    private BottomNavigationView bnv;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private ArrayList<Fragment> fragments;
    private ArrayList<ChildFragment> childFragments;

    @Override
    protected void initView() {
        super.initView ();
        recy.setLayoutManager (new LinearLayoutManager (getActivity ()));
        list_two = new ArrayList<> ();
        list = new ArrayList<> ();
        fragments = new ArrayList<> ();
        childFragments = new ArrayList<> ();
        adapter = new TiXiAdapter (getActivity (), list, list_two);
        recy.setAdapter (adapter);
        smart.setOnRefreshLoadMoreListener (this);
        adapter.setOnItemCliclListener (new TiXiAdapter.OnItemCliclListener () {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent (BaseApp.getContext (), TiXiActivity.class);
                TiXiBean.DataBean dataBean = list.get (position);
                Bundle bundle = new Bundle ();
                bundle.putSerializable ("data",dataBean);
                intent.putExtras (bundle);
                startActivityForResult (intent,0);
            }
        });
        recy.addOnScrollListener (new RecyclerView.OnScrollListener () {
            @Override
            public void onScrollStateChanged(@NonNull final RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged (recyclerView, newState);
                //获得recyclerView的线性布局管理器
                LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager ();
                int position = manager.findFirstVisibleItemPosition ();
                if (position > 0.1) {
                    fab.setOnClickListener (new View.OnClickListener () {
                        @Override
                        public void onClick(View v) {
                            recyclerView.scrollToPosition (0);
                        }
                    });
                }
                bnv = getActivity ().findViewById (R.id.bnv);
                fab = getActivity ().findViewById (R.id.fab);
                toolbar = getActivity ().findViewById (R.id.toolbar);

                //获取到第一个item的显示的下标  不等于0表示第一个item处于不可见状态 说明列表没有滑动到顶部 显示回到顶部按钮
                int firstVisibleItemPosition = manager.findFirstVisibleItemPosition ();
                // 当不滚动时
//                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
//                    // 判断是否滚动超过一屏
//                    if (firstVisibleItemPosition == 3) {
//                        AnimatorUtil.translateShow (bnv, null);
//                        bnv.setVisibility (View.VISIBLE);
//                    } else {
//                        //显示回到顶部按钮
//
//
//                    }
//                    //获取RecyclerView滑动时候的状态
//                } else if (newState == RecyclerView.SCROLL_INDICATOR_TOP) {//拖动中
////                    bnv.setVisibility(View.GONE);
//                }
            }

            @Override
            public void onScrolled(@NonNull final RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled (recyclerView, dx, dy);
                //上滑
                if (dy == 6) {
                    getActivity ().runOnUiThread (new Runnable () {
                        @Override
                        public void run() {
                            AnimatorUtil_two.translateHide (toolbar, null);
                            toolbar.setVisibility (View.GONE);
//                            AnimatorUtil.translateHide (bnv, null);
//                            AnimatorUtil.translateHide (fab, null);
//                            bnv.setVisibility (View.GONE);
                        }
                    });
                } else if (dy == -6) {
//                    //下滑
                    getActivity ().runOnUiThread (new Runnable () {
                        @Override
                        public void run() {
                            AnimatorUtil.translateShow (toolbar, null);
                            toolbar.setVisibility (View.VISIBLE);
//                            AnimatorUtil.translateShow (fab, null);
//                            AnimatorUtil.translateShow (bnv, null);
//                            bnv.setVisibility (View.VISIBLE);
                        }
                    });
                }
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager ();
                int childCount = layoutManager.getChildCount ();
                //对每个item进行初始化
                for (int i = 0; i < childCount; i++) {

                    layoutManager.getChildAt (i).setAlpha (1);
                    layoutManager.getChildAt (i).setScaleY (1);
                    layoutManager.getChildAt (i).setScaleX (1);
                }
                calculateAlphaAndScale (recyclerView, layoutManager);
            }
        });


    }

    private void calculateAlphaAndScale(RecyclerView recyclerView, LinearLayoutManager layoutManager) {
        //获得recyclerView的线性布局管理器
        LinearLayoutManager manager = (LinearLayoutManager) recyclerView.getLayoutManager ();

        int firstItemPosition = manager.findFirstVisibleItemPosition ();
        int lastItemPosition = manager.findLastVisibleItemPosition ();
        //根据索引找到view
        View lastView = manager.getChildAt (lastItemPosition - firstItemPosition);
        if (lastView != null) {
            //当找到最后一项的view时,根据可见部分占view总部分的占比,计算出透明度以及缩放比例。
            int itemHeight = lastView.getHeight ();
            int visibleHeight = recyclerView.getHeight () - lastView.getTop ();
            if (visibleHeight < 0) {
                return;
            }
            float ratio = visibleHeight * 1.0f / itemHeight;
            if (ratio > 1.0) {
                return;
            }
            lastView.setAlpha (ratio);
            float scale = 0.8f;//默认最小的缩放比例
            float scaleFactor = scale + (1 - scale) * ratio;

            lastView.setScaleX (scaleFactor);
            lastView.setScaleY (scaleFactor);
        }
    }

    @Override
    protected void initData() {
        super.initData ();
        presenter.getTiXi ();
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
    protected int getLayoutId() {
        return R.layout.fragment_tixi;
    }


    @Override
    public void onSuccess(TiXiBean tiXiBean) {

        list.addAll (tiXiBean.getData ());
        List<TiXiBean.DataBean> data = tiXiBean.getData ();
        list_two.addAll (tiXiBean.getData ().get (0).getChildren ());
        adapter.notifyDataSetChanged ();
    }

    @Override
    public void onFail(String s) {
        LemonBubble.showError (getActivity (), "网络不太好，请稍后再试", 1000);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        refreshLayout.finishLoadMore (1000);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        list.clear ();
        list_two.clear ();
        initData ();
        refreshLayout.finishRefresh (1000);
    }
}
