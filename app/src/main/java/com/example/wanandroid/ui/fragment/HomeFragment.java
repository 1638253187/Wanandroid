package com.example.wanandroid.ui.fragment;


import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.HomeAdapter;
import com.example.wanandroid.base.BaseMvpFargment;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.bean.HomeBean;
import com.example.wanandroid.model.HomeModel;
import com.example.wanandroid.presenter.HomePresenter;
import com.example.wanandroid.ui.activity.Web;
import com.example.wanandroid.util.AnimatorUtil;
import com.example.wanandroid.util.AnimatorUtil_two;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import net.lemonsoft.lemonbubble.LemonBubble;
import net.lemonsoft.lemonbubble.LemonBubbleInfo;
import net.lemonsoft.lemonbubble.enums.LemonBubbleLayoutStyle;
import net.lemonsoft.lemonbubble.enums.LemonBubbleLocationStyle;
import java.util.ArrayList;
import butterknife.BindView;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends BaseMvpFargment<HomePresenter, HomeModel, BaseView<HomeBean, String>> implements BaseView<HomeBean, String>, OnRefreshLoadMoreListener {


    @BindView(R.id.recy)
    RecyclerView recy;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    Unbinder unbinder;
    private ArrayList<BannerBean.DataBean> list;
    private ArrayList<HomeBean.DataBean.DatasBean> homeList;
    private HomeAdapter adapter;
    private int page = 0;
    private BottomNavigationView bnv;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private int index;
    private LemonBubbleInfo roundProgressBubbleInfo;


    @Override
    protected void initView() {
        super.initView ();
        list = new ArrayList<> ();
        homeList = new ArrayList<> ();
        recy.setLayoutManager (new LinearLayoutManager (getActivity ()));
        adapter = new HomeAdapter (getActivity (), list, homeList);
        adapter.setHasStableIds (true);
        recy.setAdapter (adapter);
        roundProgressBubbleInfo = LemonBubble.getRoundProgressBubbleInfo ();
        recy.setItemAnimator (new DefaultItemAnimator ());
        smart.setOnRefreshLoadMoreListener (this);
        adapter.setOnItemCliclListener (new HomeAdapter.OnItemCliclListener () {
            @Override
            public void onItemClick(int position) {
                HomeBean.DataBean.DatasBean datasBean = homeList.get (position);
                String link = datasBean.getLink ();
                Intent intent = new Intent (getActivity (), Web.class);
                intent.putExtra ("link", link);
                startActivity (intent);
                index = position;
            }
        });
    }

    @Override
    protected void initData() {
        super.initData ();
        presenter.getHome (page++);
    }

    @Override
    protected void initListener() {
        super.initListener ();
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
                // 第一种，直接取消动画
                RecyclerView.ItemAnimator animator = recy.getItemAnimator ();
                if (animator instanceof SimpleItemAnimator) {
                    ((SimpleItemAnimator) animator).setSupportsChangeAnimations (false);
                }

                recy.getItemAnimator ().setChangeDuration (0);
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
    protected BaseView<HomeBean, String> initMvpView() {
        return this;
    }

    @Override
    protected HomeModel initMvpModel() {
        return new HomeModel ();
    }

    @Override
    protected HomePresenter initMvpPresenter() {
        return new HomePresenter ();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    public void onSuccess(HomeBean homeBean) {
//        homeList.clear ();
        homeList.addAll (homeBean.getData ().getDatas ());
        if (homeList != null && homeList.size () > 0) {
            // 获取默认的正确信息的泡泡信息对象
            LemonBubbleInfo myInfo = LemonBubble.getRightBubbleInfo ();
            // 设置图标在左侧，标题在右侧
            myInfo.setLayoutStyle (LemonBubbleLayoutStyle.ICON_LEFT_TITLE_RIGHT);
            // 设置泡泡控件在底部
            myInfo.setLocationStyle (LemonBubbleLocationStyle.BOTTOM);
            // 设置泡泡控件的动画图标颜色为蓝色
            myInfo.setIconColor (Color.GREEN);
            // 设置泡泡控件的尺寸，单位dp
            myInfo.setBubbleSize (200, 80);
            // 设置泡泡控件的偏移比例为整个屏幕的0.01,
            myInfo.setProportionOfDeviation (0.01f);
            // 设置泡泡控件的标题
            myInfo.setTitle ("完成");
            // 展示自定义的泡泡控件，并显示2s后关闭
            //            LemonBubble.showBubbleInfo (getActivity (), myInfo, 1000);
            //            LemonBubble.hide();
        }
        adapter.notifyDataSetChanged ();
    }

    @Override
    public void onFail(String s) {
        LemonBubble.showError (getActivity (), "网络不太好，请稍后再试", 1000);
    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        initData ();
        refreshLayout.finishLoadMore (1000);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 0;
        initData ();
        homeList.clear ();
        refreshLayout.finishRefresh (1000);
    }
}
