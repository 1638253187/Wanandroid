package com.example.wanandroid.model;

import com.example.wanandroid.api.HomeServer;
import com.example.wanandroid.callback.BaseCallBack;
import com.example.wanandroid.base.BaseModel;
import com.example.wanandroid.bean.HomeBean;
import com.example.wanandroid.util.netutil.HttpUtil;
import com.example.wanandroid.util.netutil.RxUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class HomeModel extends BaseModel {
    public void getHome(final BaseCallBack<HomeBean, String> callBack, int page) {
        HttpUtil.getHttpUtil ().getServer (HomeServer.baseurl1, HomeServer.class)
                .getHome (page).compose (RxUtil.<HomeBean>rxObservableTransformer ()).subscribe (new Observer<HomeBean> () {
            @Override
            public void onSubscribe(Disposable d) {
            }

            @Override
            public void onNext(HomeBean homeBean) {
                callBack.onSuccess (homeBean);
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
