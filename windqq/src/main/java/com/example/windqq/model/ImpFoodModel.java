package com.example.windqq.model;

import com.example.windqq.api.FoodServer;
import com.example.windqq.bean.FoodBean;
import com.example.windqq.callback.FoodCallBack;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class ImpFoodModel implements FoodModel {
    private static int page=1;

    public static int page(){
        page=1;
        return page;
    }

    @Override
    public void getFood(final FoodCallBack callBack) {
        Retrofit retrofit = new Retrofit.Builder ()
                .addConverterFactory (GsonConverterFactory.create ())
                .addCallAdapterFactory (RxJava2CallAdapterFactory.create ())
                .baseUrl (FoodServer.baseurl)
                .build ();
        FoodServer foodServer = retrofit.create (FoodServer.class);
        Observable<FoodBean> observable = foodServer.getFood ("dish_list.php?stage_id=1&limit=20&page="+page+++"");
        observable.subscribeOn (Schedulers.io ())
                .observeOn (AndroidSchedulers.mainThread ())
                .subscribe (new Observer<FoodBean> () {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(FoodBean foodBean) {
                    callBack.onSuccess (foodBean.getData ());
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
