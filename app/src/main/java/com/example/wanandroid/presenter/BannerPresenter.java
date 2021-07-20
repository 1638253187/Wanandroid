package com.example.wanandroid.presenter;

import com.example.wanandroid.callback.BaseCallBack;
import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.model.BannerModel;


public class BannerPresenter extends BasePresenter<BannerModel, BaseView<BannerBean, String>> implements BaseCallBack<BannerBean, String> {
    public void getBanner() {
        if (model != null) {
            model.getBanner (this);
        }
    }

    @Override
    public void onSuccess(BannerBean K) {
        if (view != null) {
            view.onSuccess (K);
        }
    }

    @Override
    public void onFail(String V) {
        if (view != null) {
            view.onFail (V);
        }
    }
}
