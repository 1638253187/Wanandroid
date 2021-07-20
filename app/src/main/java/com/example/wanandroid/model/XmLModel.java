package com.example.wanandroid.model;

import com.example.wanandroid.api.XmServer;
import com.example.wanandroid.callback.BaseCallBack;
import com.example.wanandroid.base.BaseModel;
import com.example.wanandroid.bean.XmListBean;
import com.example.wanandroid.util.netutil.HttpUtil;
import com.example.wanandroid.util.netutil.RxUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class XmLModel extends BaseModel {
    public static int id;

    public static void whoId(int ids) {
        id = ids;
    }



    public void getXl(final BaseCallBack<XmListBean, String> callBack) {

        HttpUtil.getHttpUtil ().getServer (XmServer.baseurl1, XmServer.class)
                .getList ("project/list/1/json?cid=" + id + "")
                .compose (RxUtil.<XmListBean>rxObservableTransformer ())
                .subscribe (new Observer<XmListBean> () {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(XmListBean xmListBean) {
                        callBack.onSuccess (xmListBean);
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
