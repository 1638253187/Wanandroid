package com.example.wanandroid.presenter;

import com.example.wanandroid.callback.BaseCallBack;
import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.DaoHBean;
import com.example.wanandroid.model.DaoModel;

public class DaoPresenter extends BasePresenter<DaoModel, BaseView<DaoHBean, String>> implements BaseCallBack<DaoHBean, String> {
    public void getDao() {
        if (model != null) {
            model.getDao (this);
        }
    }

    @Override
    public void onSuccess(DaoHBean K) {
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
