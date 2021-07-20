package com.example.windqq.activity.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.windqq.R;
import com.example.windqq.activity.WoDe;
import com.example.windqq.activity.YouXiang;
import com.example.windqq.adapter.RecyFlAdapter;
import com.example.windqq.adapter.RecyVoide_TwoAdapter;
import com.example.windqq.bean.FlBean;
import com.example.windqq.bean.VideoBean_Tow;
import com.example.windqq.model.ImpFlModel;
import com.example.windqq.model.ImpVTwoModel;
import com.example.windqq.presenter.ImpFlPresenter;
import com.example.windqq.presenter.ImpVTPresenter;
import com.example.windqq.view.FlView;
import com.example.windqq.view.Video_TwoView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class VideoFragment extends Fragment implements FlView, OnRefreshLoadMoreListener {

    private RecyclerView mRecy;
    private SmartRefreshLayout mSmart;
    private ImageView mIvUser;
    private ImageView mIvEmile;
    private RecyclerView mRecyTwo;
    private ArrayList<FlBean.ResultsBean> list;
    private RecyFlAdapter adapter;
    private int page = 1;
    private ArrayList<VideoBean_Tow.ResultBean> list1;
    private RecyVoide_TwoAdapter adapter1;
    private int ische = 0;
    private LinearLayoutManager layoutManager;
    private int fllength;

    public VideoFragment() {
        // Required empty public constructor
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint (isVisibleToUser);
        if (isVisibleToUser) {
            initData ();
            initData_one ();
        } else {
            if (list != null && list.size () > 0) {
                list.clear ();
                ische = 0;
                adapter.notifyDataSetChanged ();
            }
        }
    }

    private void initData_one() {
        ImpVTPresenter impVTPresenter = new ImpVTPresenter (new ImpVTwoModel (), new Video_TwoView () {
            @Override
            public void onSuccess(List<VideoBean_Tow.ResultBean> videoBean_tows) {
                if (videoBean_tows != null) {
                    list1.addAll (videoBean_tows);
                    adapter1.notifyDataSetChanged ();
                }
            }

            @Override
            public void onFaile(String error) {
                Toast.makeText (getActivity (), error, Toast.LENGTH_SHORT).show ();
            }
        });
        impVTPresenter.getVt ();
    }

    private void initData() {
        ImpFlPresenter impFlPresenter = new ImpFlPresenter (new ImpFlModel (), this);
        impFlPresenter.getFl (page++);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate (R.layout.fragment_three, container, false);
        initView (inflate);
        initLinster ();

        return inflate;
    }

    private void initLinster() {
        //邮箱信息
        mIvEmile.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
               startActivity (new Intent (getActivity (), YouXiang.class));
            }
        });
        //我的
        mIvUser.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View v) {
                startActivity (new Intent (getActivity (), WoDe.class));
            }
        });
    }

    private void initView(View inflate) {
        mRecy = (RecyclerView) inflate.findViewById (R.id.recy);
        mSmart = (SmartRefreshLayout) inflate.findViewById (R.id.smart_two);
        mIvUser = (ImageView) inflate.findViewById (R.id.iv_user);
        mIvEmile = (ImageView) inflate.findViewById (R.id.iv_emile);
        ((DefaultItemAnimator) mRecy.getItemAnimator ()).setSupportsChangeAnimations (false);
        layoutManager = new LinearLayoutManager (getContext (), LinearLayoutManager.HORIZONTAL, false);
        mRecy.setLayoutManager (layoutManager);
        list = new ArrayList<> ();
        adapter = new RecyFlAdapter (getActivity (), list);
        mRecy.setAdapter (adapter);
        mRecyTwo = (RecyclerView) inflate.findViewById (R.id.recy_two);
        mRecyTwo.setLayoutManager (new LinearLayoutManager (getActivity ()));
        list1 = new ArrayList<> ();
        adapter1 = new RecyVoide_TwoAdapter (getActivity (), list1);
        mRecyTwo.setAdapter (adapter1);
        mSmart.setOnRefreshLoadMoreListener (this);
        adapter.setOnItemCliclListener (new RecyFlAdapter.OnItemCliclListener () {
            @Override
            public void onItemClick(int position) {
                fllength = position + 1;
                list1.clear ();
                ImpVTwoModel.page (fllength);
                Log.e ("tag",fllength+"");
                initData_one ();
                adapter1.notifyDataSetChanged ();
            }
        });
        adapter1.setmOnimageviewListener (new RecyVoide_TwoAdapter.MonImageViewListener () {
            @Override
            public void onItemClick(View v, int position) {
                ImageButton iv_zan = v.findViewById (R.id.iv_zan);

                if (iv_zan.isClickable ()) {
                    if (ische % 2 == 0) {
                        iv_zan.setBackground (getResources ().getDrawable (R.drawable.yzx));
                    } else {
                        iv_zan.setBackground (getResources ().getDrawable (R.drawable.yzy));
                    }
                    ische++;
                }

            }
        });

//        mRecy.addOnScrollListener (new RecyclerView.OnScrollListener () {
//            @Override
//            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
//                super.onScrollStateChanged (recyclerView, newState);
//                switch (newState) {
//                    case RecyclerView.SCROLL_STATE_IDLE:
//                        if (MyJzvdStd.AUTOPLAY) {
//                            autoPlayVideo(recyclerView,VideoTagEnum.TAG_AUTO_PLAY_VIDEO);
//                        }
//                        break;
//                    default:
//                        autoPlayVideo(recyclerView, VideoTagEnum.TAG_PAUSE_VIDEO);
//                        break;
//
//                }
//            }
//
//            @Override
//            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled (recyclerView, dx, dy);
//            }
//        });
    }


    @Override
    public void onSuccess(List<FlBean.ResultsBean> resultsBeans) {
        list.addAll (resultsBeans);
        adapter.notifyDataSetChanged ();
    }


    @Override
    public void onFail(String error) {
        Toast.makeText (getActivity (), error, Toast.LENGTH_SHORT).show ();

    }

    @Override
    public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
        initData_one ();
        refreshLayout.finishLoadMore ();
        mSmart.finishLoadMore ();
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        list1.clear ();
        ImpVTwoModel.page ();
        initData_one ();
        refreshLayout.finishRefresh ();
        mSmart.finishRefresh ();
    }

    @Override
    public void onPause() {
        super.onPause ();
        adapter1.StopPlay ();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView ();
        adapter1.StopPlay ();
    }

    @Override
    public void onDestroy() {
        adapter1.StopPlay ();
        super.onDestroy ();
    }
}

//    private void initSHUju() {
//        String URL = "http://" + Constant.auto_ip_port + "/video/mobile/findAllUser.do";
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    OkHttpClient client = new OkHttpClient();//创建OkHttpClient对象
//                    Request request = new Request.Builder()
//                            .url(URL)//请求接口。如果需要传参拼接到接口后面。
//                            .build();//创建Request 对象
//                    Response response = null;
//                    response = client.newCall(request).execute();//得到Response 对象
//                    if (response.isSuccessful()) {
//                        responseResult = AESUtils.decrypt(AES_KEY, response.body().string());
//
//                        Log.d("tag","response.code()=="+response.code());
//                        Log.d("tag","response.message()=="+response.message());
//                        Log.d("tag","responseResult=="+responseResult);
//                        ulist = JSON.parseObject(responseResult, AlluserBean.class).getUlist();
//                        sourceDateList.addAll(ulist);
//                        getActivity().runOnUiThread(new Runnable() {
//                            @Override
//                            public void run() {
//                                adapter.notifyDataSetChanged();
//                            }
//                        });
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//        }).start();
//    }