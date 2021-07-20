package com.example.windqq.activity.fragment;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.windqq.R;
import com.example.windqq.adapter.RecyDuoAdapter;
import com.example.windqq.bean.FoodBean;
import com.example.windqq.callback.SimpleItemTouchHelperCallBack;
import com.example.windqq.model.ImpFoodModel;
import com.example.windqq.presenter.ImpFoodPresenter;
import com.example.windqq.view.FoodView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class FoodFragment extends Fragment implements OnRefreshLoadMoreListener, FoodView {

    private RecyclerView mRecy;
    private SmartRefreshLayout mSmart;
    private ArrayList<FoodBean.DataBean> list;
    private RecyDuoAdapter adapter;
    private ImpFoodPresenter impFoodPresenter;

    public FoodFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if (getUserVisibleHint ()) {
            initData ();
        } else {
            if (list != null && list.size () > 0) {
                list.clear ();
                adapter.notifyDataSetChanged ();
            }
        }
    }

    private void initData() {
        impFoodPresenter = new ImpFoodPresenter (new ImpFoodModel (), this);
        ImpFoodModel.page ();
        impFoodPresenter.getFood ();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate (R.layout.fragment_one, container, false);
        initView (inflate);
        return inflate;
    }

    private void initView(View inflate) {
        mRecy = (RecyclerView) inflate.findViewById (R.id.recy);
        mSmart = (SmartRefreshLayout) inflate.findViewById (R.id.smart);
        mSmart.setOnRefreshLoadMoreListener (this);
        list = new ArrayList<> ();
        mRecy.setLayoutManager (new LinearLayoutManager (getActivity ()));
        adapter = new RecyDuoAdapter (getActivity (), list);
        mRecy.setAdapter (adapter);

        //自定义处理类
        //callback回调，上下拖动和左右移动的时间触发
        SimpleItemTouchHelperCallBack helper = new SimpleItemTouchHelperCallBack (adapter);
        //itemTouchHelper系统辅助类，赋值需要一个ItemTouchHelper.callback对象，自定义处理类
        ItemTouchHelper touchHelper = new ItemTouchHelper (helper);
        //关联一个Recyclerview
        touchHelper.attachToRecyclerView (mRecy);
        adapter.setOnItemCliclListener (new RecyDuoAdapter.OnItemCliclListener () {
            @Override
            public void onItemClick(int position) {
                String url = "mqqapi://card/show_pslcard?src_type=internal&version=1&uin=" + 1638253187
                        + "&card_type=person&source=qrcode";
                startActivity (new Intent (Intent.ACTION_VIEW, Uri.parse (url)));
                MediaPlayer mPlayer = MediaPlayer.create(getActivity (), R.raw.qtj);
                mPlayer.start();
            }
        });
    }

    @Override
    public void onSuccess(List<FoodBean.DataBean> dataBeans) {
        if (dataBeans != null) {
            list.addAll (dataBeans);
            adapter.notifyDataSetChanged ();
        }
    }

    @Override
    public void onFail(String error) {
        Toast.makeText (getActivity (), error, Toast.LENGTH_SHORT).show ();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        impFoodPresenter.getFood ();
        refreshLayout.finishLoadMore ();
        mSmart.finishLoadMore ();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        list.clear ();
        ImpFoodModel.page ();
        impFoodPresenter.getFood ();
        refreshLayout.finishRefresh ();
        mSmart.finishRefresh ();
    }

}
