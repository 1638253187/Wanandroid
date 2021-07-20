package com.example.windqq.model;

import android.util.Log;

import com.example.windqq.api.VideoServer_tow;
import com.example.windqq.bean.VideoBean_Tow;
import com.example.windqq.callback.VideoTwoCallBack;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImpVTwoModel implements VTwoModel {

    private static int page = 1;

    public static int page() {
        page = 1;
        return page;
    }
   public static int page(int i) {
        page=i;
        return page;
    }

    @Override
    public void getVideo(final VideoTwoCallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder ()
                .baseUrl (VideoServer_tow.base_Url)
                .addCallAdapterFactory (RxJava2CallAdapterFactory.create ())
                .addConverterFactory (GsonConverterFactory.create ())
                .build ();
        VideoServer_tow videoServer_tow = retrofit.create (VideoServer_tow.class);
        Observable<VideoBean_Tow> observable = videoServer_tow.getVideo ("getJoke?page=" + page++ +"&count=15&type=video");
        observable.subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribe (new Observer<VideoBean_Tow> () {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(VideoBean_Tow videoBean_tow) {
                    callBack.onSuccess (videoBean_tow.getResult ());
                    }

                    @Override
                    public void onError(Throwable e) {
                    callBack.onFaile ("网络错误:"+e.getMessage ());
                    }

                    @Override
                    public void onComplete() {

                    }
                });

//        OkHttpClient okHttpClient = new OkHttpClient.Builder ().build ();
//        final Request request = new Request.Builder ()
//                .url ("https://api.apiopen.top/getJoke?page=" + page++ + "&count=15&type=video")
//                .get ()
//                .build ();
//        Call call = okHttpClient.newCall (request);
//        call.enqueue (new Callback () {
//            @Override
//            public void onFailure(Call call, IOException e) {
//
//            }
//
//            @Override
//            public void onResponse(Call call, Response response) throws IOException {
//                String vt = response.body ().toString ();
//                Gson gson = new GsonBuilder ().serializeNulls().create();
//                VideoBean_Tow videoBean_tow = gson.fromJson (vt, VideoBean_Tow.class);
//                callBack.onSuccess ((List<VideoBean_Tow.ResultBean>) videoBean_tow);
//            }
//        });
    }
}
