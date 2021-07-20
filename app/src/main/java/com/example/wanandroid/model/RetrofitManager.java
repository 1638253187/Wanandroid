package com.example.wanandroid.model;

import com.example.wanandroid.base.BaseApp;
import com.example.wanandroid.constant.Constants;

import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

public class RetrofitManager {
    private static final long MAX_SIZE = 600 * 1024 * 1024;
    private Retrofit mRetrofit;

    private RetrofitManager() {
        initRetrofit();
    }

    //Retrofti和OkHttp关联
    private void initRetrofit() {
        Cache cahce = new Cache(BaseApp.getContext().getCacheDir(),
                MAX_SIZE);
        OkHttpClient client = new OkHttpClient.Builder().
                connectTimeout(5000, TimeUnit.MILLISECONDS).
                readTimeout(5000, TimeUnit.MILLISECONDS).
                cache(cahce).build();
        mRetrofit = new Retrofit.Builder().
                baseUrl(Constants.DEBUG_BASE_URL).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                client(client).build();
    }

    private static RetrofitManager mManager;

    public static RetrofitManager getmManager() {
        if (mManager == null)
            synchronized (RetrofitManager.class) {
                if (mManager == null)
                    mManager = new RetrofitManager();
            }
        return mManager;
    }

    //获取ApiService实例
    public ApiService getApiService() {
        return mRetrofit.create(ApiService.class);
    }
}
