package com.example.windqq.model;

import com.example.windqq.api.FlServer;
import com.example.windqq.bean.FlBean;
import com.example.windqq.callback.FlCallBack;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImpFlModel implements FlModel {
    @Override
    public void getFl(final FlCallBack callBack, final int page) {
        Retrofit retrofit = new Retrofit.Builder ()
                .baseUrl (FlServer.baseurl)
                .addConverterFactory (GsonConverterFactory.create ())
                .addCallAdapterFactory (RxJava2CallAdapterFactory.create ())
                .build ();

        FlServer server = retrofit.create (FlServer.class);
        Observable<FlBean> observable = server.getFl (page);

        observable.subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribe (new Observer<FlBean> () {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FlBean flBean) {
                        callBack.onSuccess (flBean.getResults (), page);
                    }

                    @Override
                    public void onError(Throwable e) {
                        callBack.onFail ("网络错误:" +e.getMessage ());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }
}
