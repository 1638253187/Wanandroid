package com.example.wanandroid.presenter;

import com.example.wanandroid.bean.ChangBean;
import com.example.wanandroid.callback.ChangCallBack;
import com.example.wanandroid.model.ChangModel;
import com.example.wanandroid.view.ChangView;

import java.util.List;

public class ImpChangPresenter implements ChangPresenter, ChangCallBack {
    private ChangModel model;
    private ChangView view;

    public ImpChangPresenter(ChangModel model, ChangView view) {
        this.model = model;
        this.view = view;
    }
    @Override
    public void getChang() {
        if (model!=null){
            model.getChang (this);
        }
    }

    @Override
    public void onSuccess(List<ChangBean.DataBean> dataBeans) {
        if (view!=null){
            view.onSuccess (dataBeans);
        }
    }

    @Override
    public void onFail(String error) {
        if (view!=null){
            view.onFail (error);
        }
    }
}
