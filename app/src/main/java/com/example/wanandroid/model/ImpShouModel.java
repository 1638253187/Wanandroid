package com.example.wanandroid.model;

import com.example.wanandroid.api.ShouServer;
import com.example.wanandroid.bean.ShouBean;
import com.example.wanandroid.callback.ShouCallBack;
import com.example.wanandroid.util.netutil.HttpUtil;
import com.example.wanandroid.util.netutil.RxUtil;


import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ImpShouModel implements ShouModel {
    @Override
    public void getShou(final ShouCallBack callBack) {
        HttpUtil.getHttpUtil ().getServer (ShouServer.baseshou, ShouServer.class)
                .getShou ().compose (RxUtil.<ShouBean>rxObservableTransformer ())
                .subscribe (new Observer<ShouBean> () {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ShouBean shouBean) {
                        callBack.onSuccess (shouBean.getData ());
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
