package com.example.wanandroid.api;

import com.example.wanandroid.bean.XmBean;
import com.example.wanandroid.bean.XmListBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface XmServer {
    String baseurl = "https://www.wanandroid.com/project/";
    String baseurl1 = "https://www.wanandroid.com/";
    @GET("tree/json")
    Observable<XmBean> getXm();

    @GET()
    Observable<XmListBean> getList(@Url String url);
}
