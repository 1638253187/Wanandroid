package com.example.wanandroid.presenter;

import com.example.wanandroid.callback.BaseCallBack;
import com.example.wanandroid.base.BasePresenter;
import com.example.wanandroid.callback.BaseView;
import com.example.wanandroid.bean.XmListBean;
import com.example.wanandroid.model.XmLModel;

public class XmlPresenter extends BasePresenter<XmLModel, BaseView<XmListBean,String>> implements BaseCallBack<XmListBean, String> {
    public void getXml(){
        if (model!=null){
            model.getXl (this);
        }
    }

    @Override
    public void onSuccess(XmListBean K) {
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
