package com.example.wanandroid.presenter;

import com.example.wanandroid.callback.BaseCallBack;
import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.XmBean;
import com.example.wanandroid.model.XmModel;

public class XmPresenter extends BasePresenter<XmModel, BaseView<XmBean, String>> implements BaseCallBack<XmBean, String> {
    public void getXm() {
        if (model != null) {
            model.getXm (this);
        }
    }

    @Override
    public void onSuccess(XmBean K) {
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
