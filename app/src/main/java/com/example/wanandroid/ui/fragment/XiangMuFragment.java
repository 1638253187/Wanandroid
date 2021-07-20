package com.example.wanandroid.ui.fragment;


import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.wanandroid.R;
import com.example.wanandroid.adapter.NodeAdapter;
import com.example.wanandroid.base.BaseMvpFargment;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.DaoHBean;
import com.example.wanandroid.model.DaoModel;
import com.example.wanandroid.presenter.DaoPresenter;
import com.example.wanandroid.util.AnimatorUtil;
import com.example.wanandroid.util.AnimatorUtil_two;
import com.example.wanandroid.util.ToastUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.Unbinder;
import q.rorbin.verticaltablayout.VerticalTabLayout;
import q.rorbin.verticaltablayout.adapter.TabAdapter;
import q.rorbin.verticaltablayout.widget.ITabView;
import q.rorbin.verticaltablayout.widget.TabView;
import qdx.stickyheaderdecoration.NormalDecoration;

/**
 * A simple {@link Fragment} subclass.
 */
public class XiangMuFragment extends BaseMvpFargment<DaoPresenter, DaoModel, BaseView<DaoHBean, String>> implements BaseView<DaoHBean, String> {


    @BindView(R.id.vtltab)
    VerticalTabLayout vtltab;
    Unbinder unbinder;
    @BindView(R.id.recy)
    RecyclerView recy;
    Unbinder unbinder1;
    private ArrayList<DaoHBean.DataBean> list;
    private ArrayList<DaoListFragment> fragmentslist;
    private NodeAdapter adapter;
    private BottomNavigationView bnv;
    private FloatingActionButton fab;
    private Toolbar toolbar;
    private int selectedTabPosition;
    private int selectposition;
    private LinearLayoutManager manager;

    @Override
    protected void initView() {
        super.initView ();
        list = new ArrayList<> ();
        fragmentslist = new ArrayList<> ();
        recy.setLayoutManager (new LinearLayoutManager (getActivity ()));
        adapter = new NodeAdapter (getActivity (), list);
        recy.setAdapter (adapter);
        initData ();
        recy.setItemAnimator (new DefaultItemAnimator ());
    }

    @Override
    protected void initData() {
        super.initData ();
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
        return new DaoModel ();
    }

    @Override
    protected DaoPresenter initMvpPresenter() {
        return new DaoPresenter ();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_xiang_mu;
    }

    @Override
    public void onSuccess(DaoHBean daoHBean) {
        list.clear ();
        list.remove (daoHBean);
        list.addAll (daoHBean.getData ());
        adapter.notifyDataSetChanged ();
        //tab栏和RecyclerView联动
        vtltab.addOnTabSelectedListener (new VerticalTabLayout.OnTabSelectedListener () {
        @Override
        public void onTabSelected(TabView tab, int position) {
            if (manager!=null)
            manager.scrollToPositionWithOffset(position,0);

        }

        @Override
        public void onTabReselected(TabView tab, int position) {

        }
    });
        //RecyclerView和tab栏联动
        recy.addOnScrollListener (new RecyclerView.OnScrollListener () {
            @Override
            public void onScrollStateChanged(@NonNull final RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged (recyclerView, newState);
                //获得recyclerView的线性布局管理器
                manager = (LinearLayoutManager) recyclerView.getLayoutManager ();
                //联动
                vtltab.setTabSelected (manager.findFirstVisibleItemPosition ());
                int position = manager.findFirstVisibleItemPosition ();
                if (position > 0.1) {
                    fab.setOnClickListener (new View.OnClickListener () {
                        @Override
                        public void onClick(View v) {
                            recyclerView.scrollToPosition (0);
                        }
                    });

//                    if (position < 1) {
//                        vtltab.setTabSelected (0);
//                    }
//                    if (position == 1) {
//                        vtltab.setTabSelected (1);
//                    }
//                    if (position == 2) {
//                        vtltab.setTabSelected (2);
//                    }
//                    if (position == 3) {
//                        vtltab.setTabSelected (3);
//                    }
//                    if (position == 4) {
//                        vtltab.setTabSelected (4);
//                    }
//                    if (position == 5) {
//                        vtltab.setTabSelected (5);
//                    }
//                    if (position == 6) {
//                        vtltab.setTabSelected (6);
//                    }
//                    if (position == 7) {
//                        vtltab.setTabSelected (7);
//                    }
//                    if (position == 8) {
//                        vtltab.setTabSelected (8);
//                    }
//                    if (position == 9) {
//                        vtltab.setTabSelected (9);
//                    }
//                    if (position == 10) {
//                        vtltab.setTabSelected (10);
//                    }
//                    if (position == 11) {
//                        vtltab.setTabSelected (11);
//                    }
//                    if (position == 12) {
//                        vtltab.setTabSelected (12);
//                    }
//                    if (position == 13) {
//                        vtltab.setTabSelected (13);
//                    }
//                    if (position == 14) {
//                        vtltab.setTabSelected (14);
//                    }
//                    if (position == 15) {
//                        vtltab.setTabSelected (15);
//                    }
//                    if (position == 16) {
//                        vtltab.setTabSelected (16);
//                    }
//                    if (position == 17) {
//                        vtltab.setTabSelected (17);
//                    }
//                    if (position == 18) {
//                        vtltab.setTabSelected (18);
//                    }
//                    if (position == 19) {
//                        vtltab.setTabSelected (19);
//                    }
//                    if (position == 20) {
//                        vtltab.setTabSelected (20);
//                    }
//                    if (position == 21) {
//                        vtltab.setTabSelected (21);
//                    }
//                    if (position == 22) {
//                        vtltab.setTabSelected (22);
//                    }
//                    if (position == 23) {
//                        vtltab.setTabSelected (23);
//                    }
//                    if (position == 24) {
//                        vtltab.setTabSelected (24);
//                    }
//                    if (position == 25) {
//                        vtltab.setTabSelected (25);
//                    }
//                    if (position == 26) {
//                        vtltab.setTabSelected (26);
//                    }
//                    if (position == 27) {
//                        vtltab.setTabSelected (27);
//                    }
//                    if (position == 28) {
//                        vtltab.setTabSelected (28);
//                    }
//                    if (position == 29) {
//                        vtltab.setTabSelected (29);
//                    }

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
            }
        });
        vtltab.setIndicatorColor (getResources ().getColor (android.R.color.transparent));
        vtltab.setTabAdapter (new TabAdapter () {
            @Override
            public int getCount() {//个数
                return list.size ();
            }

            @Override
            public ITabView.TabBadge getBadge(int position) {
                return null;
            }

            @Override
            public ITabView.TabIcon getIcon(int position) {
                return null;
            }

            @Override
            public ITabView.TabTitle getTitle(int position) {//设置标题
                ITabView.TabTitle title = new ITabView.TabTitle.Builder ()
                        .setContent (list.get (position).getName ())
                        .setTextColor ((Color.GREEN), Color.GRAY)
                        .build ();
                return title;
            }

            @Override
            public int getBackground(int position) {
                return -1;
            }
        });
        //获取数据集合
        final List<DaoHBean.DataBean> data = daoHBean.getData ();
        //设置粘性头布局。返回对应的头标题
        NormalDecoration decoration = new NormalDecoration () {
            @Override
            public String getHeaderName(int i) {
                return list.get (i).getName ();
            }
        };
//        设置item分割
        recy.addItemDecoration (decoration);

        //刷新数据
//        list.addAll (data);

    }


    @Override
    public void onFail(String s) {
        ToastUtil.showShort (s);
    }

}
