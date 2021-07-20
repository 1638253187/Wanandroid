package com.example.wanandroid.model;

import com.example.wanandroid.api.TiXiServer;
import com.example.wanandroid.callback.BaseCallBack;
import com.example.wanandroid.base.BaseModel;
import com.example.wanandroid.bean.TiXiBean;
import com.example.wanandroid.util.netutil.HttpUtil;
import com.example.wanandroid.util.netutil.RxUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TiXiModel extends BaseModel {
    public void getTixi(final BaseCallBack<TiXiBean, String> callBack) {
        HttpUtil.getHttpUtil ().getServer (TiXiServer.baseurl, TiXiServer.class)
                .getTiXi ()
                .compose (RxUtil.<TiXiBean>rxObservableTransformer ()).subscribe (new Observer<TiXiBean> () {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TiXiBean tiXiBean) {
                callBack.onSuccess (tiXiBean);
            }

            @Override
            public void onError(Throwable e) {
                callBack.onFail ("网络错误:" + e.getMessage ());

            }

            @Override
            public void onComplete() {

            }
        });

    }

}
