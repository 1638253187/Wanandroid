package com.example.wanandroid.presenter;

import com.example.wanandroid.callback.BaseCallBack;
import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.TiXiBean;
import com.example.wanandroid.model.TiXiModel;

public class TiXiPresenter extends BasePresenter<TiXiModel, BaseView<TiXiBean, String>> implements BaseCallBack<TiXiBean, String> {
    public void getTiXi() {
        if (model != null) {
            model.getTixi (this);
        }
    }

    @Override
    public void onSuccess(TiXiBean K) {
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
