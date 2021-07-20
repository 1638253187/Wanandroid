package com.example.wanandroid.model;

import com.example.wanandroid.api.DaoHServer;
import com.example.wanandroid.callback.BaseCallBack;
import com.example.wanandroid.base.BaseModel;
import com.example.wanandroid.bean.DaoHBean;
import com.example.wanandroid.util.netutil.HttpUtil;
import com.example.wanandroid.util.netutil.RxUtil;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class DaoModel extends BaseModel {
    public void getDao(final BaseCallBack<DaoHBean, String> callBack) {
        HttpUtil.getHttpUtil ().getServer (DaoHServer.baseurl, DaoHServer.class).getDao ().compose (RxUtil.<DaoHBean>rxObservableTransformer ())
                .subscribe (new Observer<DaoHBean> () {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(DaoHBean daoHBean) {
                        callBack.onSuccess (daoHBean);
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
