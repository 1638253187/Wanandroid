package com.example.windqq.presenter;

import com.example.windqq.bean.VideoBean_Tow;
import com.example.windqq.callback.VideoTwoCallBack;
import com.example.windqq.model.VTwoModel;
import com.example.windqq.view.Video_TwoView;

import java.util.List;

public class ImpVTPresenter implements VTPresenter, VideoTwoCallBack {
    private VTwoModel model;
    private Video_TwoView view;

    public ImpVTPresenter(VTwoModel model, Video_TwoView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getVt() {
        if (model!=null){
            model.getVideo (this);
        }
    }

    @Override
    public void onSuccess(List<VideoBean_Tow.ResultBean> videoBean_tows) {
        if (view!=null){
            view.onSuccess (videoBean_tows);
        }
    }

    @Override
    public void onFaile(String error) {
        if (view!=null){
            view.onFaile (error);
        }
    }
}
