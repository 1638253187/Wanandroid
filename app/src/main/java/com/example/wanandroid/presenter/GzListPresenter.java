package com.example.wanandroid.presenter;

import com.example.wanandroid.callback.BaseCallBack;
import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.TabListBean;
import com.example.wanandroid.model.GzListModel;

public class GzListPresenter extends BasePresenter<GzListModel, BaseView<TabListBean,String>> implements BaseCallBack<TabListBean, String> {
    public void getList(int id,int page,String k){
        if (model!=null){
            model.getList (this,id,page,k);
        }
    }

    @Override
    public void onSuccess(TabListBean K) {
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
