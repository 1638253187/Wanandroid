package com.example.wanandroid.presenter;

import com.example.wanandroid.callback.BaseCallBack;
import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.HomeBean;
import com.example.wanandroid.model.HomeModel;

public class HomePresenter extends BasePresenter<HomeModel, BaseView<HomeBean,String>> implements BaseCallBack<HomeBean, String> {
    public void getHome(int page){
        if (model!=null){
            model.getHome (this,page);
        }
    }

    @Override
    public void onSuccess(HomeBean K) {
        if (view!=null){
            view.onSuccess (K);
        }
    }

    @Override
    public void onFail(String V) {
        if (view!=null){
            view.onFail (V);
        }
    }
}
