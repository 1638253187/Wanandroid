package com.example.wanandroid.model;

import com.example.wanandroid.api.XmServer;
import com.example.wanandroid.callback.BaseCallBack;
import com.example.wanandroid.base.BaseModel;
import com.example.wanandroid.bean.XmBean;
import com.example.wanandroid.util.netutil.HttpUtil;
import com.example.wanandroid.util.netutil.RxUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class XmModel extends BaseModel {
    public void getXm(final BaseCallBack<XmBean, String> callBack) {
        HttpUtil.getHttpUtil ().getServer (XmServer.baseurl, XmServer.class)
                .getXm ()
                .compose (RxUtil.<XmBean>rxObservableTransformer ())
                .subscribe (new Observer<XmBean> () {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(XmBean dataBean) {
                        callBack.onSuccess (dataBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFail ("错误:" + e.getMessage ());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
