package com.example.wanandroid.model;

import com.example.wanandroid.api.ChangServer;
import com.example.wanandroid.bean.ChangBean;
import com.example.wanandroid.callback.ChangCallBack;
import com.example.wanandroid.util.netutil.HttpUtil;
import com.example.wanandroid.util.netutil.RxUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class ImpChangModel implements ChangModel {
    @Override
    public void getChang(final ChangCallBack callBack) {
        HttpUtil.getHttpUtil ().getServer (ChangServer.baseurl, ChangServer.class)
                .getChang ().compose (RxUtil.<ChangBean>rxObservableTransformer ())
                .subscribe (new Observer<ChangBean> () {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ChangBean changBean) {
                        callBack.onSuccess (changBean.getData ());
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
