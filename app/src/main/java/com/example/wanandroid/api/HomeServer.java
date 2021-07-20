package com.example.wanandroid.api;

import com.example.wanandroid.bean.BannerBean;
import com.example.wanandroid.bean.HomeBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface HomeServer {

    String baseurl = "https://www.wanandroid.com/";
    String baseurl1 = "https://www.wanandroid.com/article/";

    @GET("banner/json")
    Observable<BannerBean> getBanner();

    @GET("list/{page}/json")
    Observable<HomeBean> getHome(@Path("page") int page);

}
