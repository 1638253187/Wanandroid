package com.example.wanandroid.presenter;

import com.example.wanandroid.callback.BaseCallBack;
import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.GzTabBean;
import com.example.wanandroid.model.GZTabModel;
public class GzTabPresenter extends BasePresenter<GZTabModel, BaseView<GzTabBean, String>> implements BaseCallBack<GzTabBean, String> {
    public void getTab() {
        if (model != null) {
            model.getTab (this);
        }
    }
    @Override
    public void onSuccess(GzTabBean K) {
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
