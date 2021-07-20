package com.example.wanandroid.api;

import com.example.wanandroid.bean.ChangBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface ChangServer {
    String baseurl = "https://www.wanandroid.com/";

    @GET("friend/json")
    Observable<ChangBean> getChang();
}
