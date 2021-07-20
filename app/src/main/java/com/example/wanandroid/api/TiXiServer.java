package com.example.wanandroid.api;

import com.example.wanandroid.bean.TiXiBean;
import com.example.wanandroid.bean.TiXiListBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface TiXiServer {

    String baseurl = "https://www.wanandroid.com/";
    String baseur2 = "https://www.wanandroid.com/";

    @GET("tree/json")
    Observable<TiXiBean> getTiXi();

    @GET("article/list/{page}/json?")
    Observable<TiXiListBean> getTiXiList(@Path("page") int  page, @Query("cid") int cid);


}
