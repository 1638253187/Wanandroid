package com.example.windqq.presenter;

import com.example.windqq.bean.FlBean;
import com.example.windqq.callback.FlCallBack;
import com.example.windqq.model.FlModel;
import com.example.windqq.view.FlView;

import java.util.List;

public class ImpFlPresenter implements FlPresenter, FlCallBack {
    private FlModel model;
    private FlView view;

    public ImpFlPresenter(FlModel model, FlView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getFl(int page) {
        if (model != null) {
            model.getFl (this, page);
        }
    }

    @Override
    public void onSuccess(List<FlBean.ResultsBean> resultsBeans, int page) {
        if (view != null) {
            view.onSuccess (resultsBeans);
        }
    }

    @Override
    public void onFail(String error) {
        if (view != null) {
            view.onFail (error);
        }
    }
}
