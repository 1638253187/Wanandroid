package com.example.wanandroid.presenter;

import com.example.wanandroid.callback.BaseCallBack;
import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.TiXiListBean;
import com.example.wanandroid.model.TiXiListModel;

public class TiXiListPresenter extends BasePresenter<TiXiListModel, BaseView<TiXiListBean,String>> implements BaseCallBack<TiXiListBean, String> {
    public void getList(int page,int cid){
        if (model!=null){
            model.getList (this,page,cid);
        }
    }

    @Override
    public void onSuccess(TiXiListBean K) {
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
