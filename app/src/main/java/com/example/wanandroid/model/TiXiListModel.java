package com.example.wanandroid.model;

import com.example.wanandroid.api.TiXiServer;
import com.example.wanandroid.callback.BaseCallBack;
import com.example.wanandroid.base.BaseModel;
import com.example.wanandroid.bean.TiXiListBean;
import com.example.wanandroid.util.netutil.HttpUtil;
import com.example.wanandroid.util.netutil.RxUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TiXiListModel extends BaseModel {
    public void getList(final BaseCallBack<TiXiListBean,String>callBack , int page, int cid){
        HttpUtil.getHttpUtil ()
                .getServer (TiXiServer.baseur2,TiXiServer.class)
                .getTiXiList (page,cid)
                .compose (RxUtil.<TiXiListBean>rxObservableTransformer ())
                .subscribe (new Observer<TiXiListBean> () {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(TiXiListBean tiXiListBean) {
            callBack.onSuccess (tiXiListBean);
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
