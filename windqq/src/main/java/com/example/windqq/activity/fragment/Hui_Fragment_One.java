package com.example.windqq.activity.fragment;


import android.content.Context;
import android.graphics.Rect;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.windqq.R;
import com.example.windqq.adapter.RecyVoideAdapter;
import com.example.windqq.bean.VideoBean;
import com.example.windqq.model.ImpVideoModel;
import com.example.windqq.presenter.ImpVideoPresenter;
import com.example.windqq.view.VideoView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import cn.jzvd.JZVideoPlayer;

import static android.support.v7.widget.RecyclerView.SCROLL_STATE_DRAGGING;
import static android.widget.AbsListView.OnScrollListener.SCROLL_STATE_IDLE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Hui_Fragment_One extends Fragment implements VideoView, OnRefreshLoadMoreListener {


    @BindView(R.id.recy)
    RecyclerView recy;
    Unbinder unbinder;
    @BindView(R.id.smart)
    SmartRefreshLayout smart;
    private ArrayList<VideoBean.ResultBean> list;
    private int page = 1;
    private RecyVoideAdapter adapter;
    private ImpVideoPresenter impVideoPresenter;
    private int visibleCount;
    private JZVideoPlayer jzVideoPlayer;

    public Hui_Fragment_One() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint (isVisibleToUser);
        if (isVisibleToUser) {
            initData ();
        } else {
            if (list != null && list.size () > 0) {
                list.clear ();
                adapter.notifyDataSetChanged ();
            }
        }
    }

    private void initData() {
        impVideoPresenter = new ImpVideoPresenter (new ImpVideoModel (), this);
        impVideoPresenter.getVideo ();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate (R.layout.fragment_hui__fragment__one, container, false);
        unbinder = ButterKnife.bind (this, inflate);
        recy.setLayoutManager (new LinearLayoutManager (getActivity ()));
        recy.addItemDecoration (new DividerItemDecoration (getActivity (), 1));
        list = new ArrayList<> ();
        adapter = new RecyVoideAdapter (getActivity (), list);
        recy.setAdapter (adapter);
        smart.setOnRefreshLoadMoreListener (this);
        recy.addOnScrollListener (new RecyclerView.OnScrollListener () {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged (recyclerView, newState);
                switch (newState) {
                    case SCROLL_STATE_IDLE: //滚动停止
                        autoPlayVideo(recyclerView);
                        break;
                    case SCROLL_STATE_DRAGGING: //手指拖动
                        break;
                }
            }

            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled (recyclerView, dx, dy);
                RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager ();
                LinearLayoutManager linearLayoutManager = new LinearLayoutManager (getActivity ());
                int firstVisibleItemPosition = linearLayoutManager.findFirstVisibleItemPosition ();
                layoutManager.requestLayout();
                int lastPos = linearLayoutManager.findLastVisibleItemPosition ();
                visibleCount = lastPos - firstVisibleItemPosition;

            }
        });
        return inflate;

    }

    private void autoPlayVideo(RecyclerView view) {
        RecyclerView.LayoutManager layoutManager = view.getLayoutManager();
        for (int i = 0; i < visibleCount; i++)
            if (layoutManager != null && layoutManager.getChildAt (i) != null && layoutManager.getChildAt (i).findViewById (R.id.jzvideo) != null) {
                  jzVideoPlayer = (JZVideoPlayer) layoutManager.getChildAt (i).findViewById (R.id.jzvideo);
                Rect rect = new Rect ();
                jzVideoPlayer.getLocalVisibleRect (rect);
                int videoheight = jzVideoPlayer.getHeight ();
                if (rect.top == 0 && rect.bottom == videoheight) {
                        jzVideoPlayer.startVideo ();
                    return;
                }
            }
        JZVideoPlayer.releaseAllVideos();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
        if (jzVideoPlayer!=null){
            jzVideoPlayer.removeTextureView();
        }
        unbinder.unbind ();
    }

    @Override
    public void onSuccess(List<VideoBean.ResultBean> e6VRBeans) {
        list.addAll (e6VRBeans);
        adapter.notifyDataSetChanged ();
    }

    @Override
    public void onFail(String error) {
        Toast.makeText (getActivity (), error, Toast.LENGTH_SHORT).show ();
    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        impVideoPresenter.getVideo ();
        refreshLayout.finishLoadMore ();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        list.clear ();
        impVideoPresenter.getVideo ();
        refreshLayout.finishRefresh ();
    }

    @Override
    public void onPause() {
        super.onPause ();
        // 视频回去的时候要暂停
        ((AudioManager) getActivity ().getSystemService (
                Context.AUDIO_SERVICE)).requestAudioFocus (
                new AudioManager.OnAudioFocusChangeListener () {
                    @Override
                    public void onAudioFocusChange(int focusChange) {
                    }
                }, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

    }
}
