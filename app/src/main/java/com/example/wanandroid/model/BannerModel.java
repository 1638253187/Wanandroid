package com.example.wanandroid.model;

import com.example.wanandroid.api.HomeServer;
import com.example.wanandroid.callback.BaseCallBack;
import com.example.wanandroid.base.BaseModel;
import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.util.netutil.HttpUtil;
import com.example.wanandroid.util.netutil.RxUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class BannerModel extends BaseModel {

    public void getBanner(final BaseCallBack<BannerBean, String> callBack) {
        HttpUtil.getHttpUtil ().getServer (HomeServer.baseurl, HomeServer.class)
                .getBanner ().compose (RxUtil.<BannerBean>rxObservableTransformer ())
                .subscribe (new Observer<BannerBean> () {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BannerBean bannerBean) {
                        callBack.onSuccess (bannerBean);
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
