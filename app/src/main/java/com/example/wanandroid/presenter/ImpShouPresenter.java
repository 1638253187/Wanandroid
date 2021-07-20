package com.example.wanandroid.presenter;

import com.example.wanandroid.bean.ShouBean;
import com.example.wanandroid.callback.ShouCallBack;
import com.example.wanandroid.model.ShouModel;
import com.example.wanandroid.util.ToastUtil;
import com.example.wanandroid.view.ShouView;

import java.util.List;

public class ImpShouPresenter implements ShouPresenter, ShouCallBack {
    private ShouModel model;
    private ShouView view;

    public ImpShouPresenter(ShouModel model, ShouView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getShou() {
        if (model != null) {
            model.getShou (this);
        }
    }


    @Override
    public void onSuccess(List<ShouBean.DataBean> shouBeans) {
        if (view != null) {
            view.onSuccess (shouBeans);
        }
    }

    @Override
    public void onFail(String error) {
        if (view != null) {
            view.onFail (error);
        }
    }
}
