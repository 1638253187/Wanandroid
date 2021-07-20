package com.example.wanandroid.ui.fragment;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.GzListAdapter;
import com.example.wanandroid.base.BaseMvpFargment;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.TabListBean;
import com.example.wanandroid.model.GzListModel;
import com.example.wanandroid.presenter.GzListPresenter;
import com.example.wanandroid.ui.activity.Web;
import com.example.wanandroid.util.AnimatorUtil;
import com.example.wanandroid.util.AnimatorUtil_two;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import net.lemonsoft.lemonbubble.LemonBubble;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;


/**
 * A simple {@link Fragment} subclass.
 */
public class GzListFragment extends BaseMvpFargment<GzListPresenter, GzListModel, BaseView<TabListBean, String>> implements BaseView<TabListBean, String>, OnRefreshLoadMoreListener {


    @BindView(R.id.recy)
    RecyclerView recy;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    Unbinder unbinder;
    @BindView(R.id.etshou)
    EditText etshou;
    @BindView(R.id.btn_sphar)
    Button btnSphar;
    Unbinder unbinder1;
    @BindView(R.id.rere)
    RelativeLayout rere;
    private int id;
    private ArrayList<TabListBean.DataBean.DatasBean> list;
    private GzListAdapter adapter;
    private int page = 0;
    private BottomNavigationView bnv;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private String name;
    private String search;

    @Override
    protected void initView() {
        super.initView ();

//        LemonBubble.showRoundProgress(getActivity (), "等待中...");
        id = getArguments ().getInt ("id");
        name = getArguments ().getString ("name");
        recy.setLayoutManager (new LinearLayoutManager (getActivity ()));
        list = new ArrayList<> ();
        adapter = new GzListAdapter (getActivity (), list);
        recy.setAdapter (adapter);
        smart.setOnRefreshLoadMoreListener (this);
        initData ();
        adapter.setOnItemCliclListener (new GzListAdapter.OnItemCliclListener () {
            @Override
            public void onItemClick(int position) {
                TabListBean.DataBean.DatasBean datasBean = list.get (position);
                String link = datasBean.getLink ();
                Intent intent = new Intent (getActivity (), Web.class);
                intent.putExtra ("link", link);
                startActivity (intent);
            }
        });

    }

    @Override
    protected void initData() {
        super.initData ();
        presenter.getList (id, page++, search);
        etshou.setHint (name + "带你发现更多干货");
    }

    @Override
    protected void initListener() {
        super.initListener ();

        etshou.setOnEditorActionListener (new TextView.OnEditorActionListener () {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                Log.e ("输入完点击确认执行该方法", "输入结束");
                return false;
            }
        });
        etshou.addTextChangedListener (new TextWatcher () {
            /**
             * 编辑框的内容发生改变之前的回调方法
             */
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // 输入前的监听

                Log.e ("输入过程中执行该方法", "文字变化");
            }

            /**
             * 编辑框的内容正在发生改变时的回调方法 >>用户正在输入
             * 我们可以在这里实时地 通过搜索匹配用户的输入
             */
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // 输入的内容变化的监听
//                Log.e ("输入前确认执行该方法", "开始输入：" + s.toString ());
            }

            /**
             * 编辑框的内容改变以后,用户没有继续输入时 的回调方法
             */
            @Override
            public void afterTextChanged(Editable s) {
                search = s.toString ();
                // 输入后的监听
                Log.e ("输入结束执行该方法", s.toString ());
            }
        });

        btnSphar.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                presenter.getList (id, page++, search);
                list.clear ();
                page = 0;
                initData ();
                adapter.notifyDataSetChanged ();
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
    protected BaseView<TabListBean, String> initMvpView() {
        return this;
    }

    @Override
    protected GzListModel initMvpModel() {
        return new GzListModel ();
    }

    @Override
    protected GzListPresenter initMvpPresenter() {
        return new GzListPresenter ();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_gz_list;
    }

    @Override
    public void onSuccess(TabListBean tabListBean) {
//        LemonBubble.showRight(getActivity (), "完成", 200);
//        LemonBubble.hide();
        list.addAll (tabListBean.getData ().getDatas ());
        adapter.notifyDataSetChanged ();
    }

    @Override
    public void onFail(String s) {
//        LemonBubble.showError(getActivity (), "网络不太好，请稍后再试", 1500);

    }


    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        if (list != null && list.size () >= 0) {
            initData ();
        }
        refreshLayout.finishLoadMore (1000);
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        page = 0;
        list.clear ();
        initData ();
        refreshLayout.finishRefresh (1000);
    }


}
