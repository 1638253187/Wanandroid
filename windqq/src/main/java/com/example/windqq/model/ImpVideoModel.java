package com.example.windqq.model;

import com.example.windqq.api.VideoServer;
import com.example.windqq.bean.VideoBean;
import com.example.windqq.callback.VideoCallBack;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static io.reactivex.android.schedulers.AndroidSchedulers.mainThread;
import static io.reactivex.schedulers.Schedulers.io;

public class ImpVideoModel implements VideoModel {
    @Override
    public void getVideo(final VideoCallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder ()
                .baseUrl (VideoServer.baseurl)
                .addCallAdapterFactory (RxJava2CallAdapterFactory.create ())
                .addConverterFactory (GsonConverterFactory.create ())
                .build ();
        VideoServer server = retrofit.create (VideoServer.class);
        Observable<VideoBean> observable = server.getVideo ();
        observable.subscribeOn (io ())
                .observeOn (mainThread ())
                .subscribe (new Observer<VideoBean> () {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }

                    @Override
                    public void onNext(VideoBean videoBean) {
                    callBack.onSuccess (videoBean.getResult ());
                    }

                    @Override
                    public void onError(Throwable e) {
                    callBack.onFail ("网络错误:"+e.getMessage ());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
