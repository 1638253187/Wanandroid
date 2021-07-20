package com.example.wanandroid.api;

import com.example.wanandroid.bean.ShouBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ShouServer {
    String baseshou = "https://www.wanandroid.com//hotkey/";

    @GET("json")
    Observable<ShouBean> getShou();
}
