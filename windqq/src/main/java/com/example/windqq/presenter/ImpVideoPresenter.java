package com.example.windqq.presenter;

import com.example.windqq.bean.VideoBean;
import com.example.windqq.callback.VideoCallBack;
import com.example.windqq.model.VideoModel;
import com.example.windqq.view.VideoView;

import java.util.List;

public class ImpVideoPresenter implements VideoPresenter, VideoCallBack {
    private VideoModel model;
    private VideoView view;

    public ImpVideoPresenter(VideoModel model, VideoView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getVideo() {
        if (model!=null){
            model.getVideo (this);
        }
    }

    @Override
    public void onSuccess(List<VideoBean.ResultBean> e6VRBeans) {
        if (view!=null){
            view.onSuccess (e6VRBeans);
        }
    }

    @Override
    public void onFail(String error) {
        if (view!=null){
            view.onFail (error);
        }
    }
}
