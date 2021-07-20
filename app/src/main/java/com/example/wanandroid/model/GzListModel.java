package com.example.wanandroid.model;

import com.example.wanandroid.api.GzServer;
import com.example.wanandroid.callback.BaseCallBack;
import com.example.wanandroid.base.BaseModel;
import com.example.wanandroid.bean.TabListBean;
import com.example.wanandroid.util.netutil.HttpUtil;
import com.example.wanandroid.util.netutil.RxUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class GzListModel extends BaseModel {
    public void getList(final BaseCallBack<TabListBean, String> callBack, int id, int page,String k) {
        HttpUtil.getHttpUtil ().getServer (GzServer.baseurl1, GzServer.class).getList (id, page,k)
                .compose (RxUtil.<TabListBean>rxObservableTransformer ())
                .subscribe (new Observer<TabListBean> () {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TabListBean tabListBean) {
                    callBack.onSuccess (tabListBean);
                    }

                    @Override
                    public void onError(Throwable e) {
                    callBack.onFail ("错误:"+e.getMessage ());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
