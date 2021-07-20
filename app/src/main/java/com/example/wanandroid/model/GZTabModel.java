package com.example.wanandroid.model;

import com.example.wanandroid.api.GzServer;
import com.example.wanandroid.callback.BaseCallBack;
import com.example.wanandroid.base.BaseModel;
import com.example.wanandroid.bean.GzTabBean;
import com.example.wanandroid.util.netutil.HttpUtil;
import com.example.wanandroid.util.netutil.RxUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class GZTabModel extends BaseModel {
    public void getTab(final BaseCallBack<GzTabBean, String> callBack) {
        HttpUtil.getHttpUtil ().getServer (GzServer.baseurl, GzServer.class).getTab ()
                .compose (RxUtil.<GzTabBean>rxObservableTransformer ()).subscribe (new Observer<GzTabBean> () {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(GzTabBean dataBean) {
                callBack.onSuccess (dataBean);
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
