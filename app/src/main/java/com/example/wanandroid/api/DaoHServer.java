package com.example.wanandroid.api;

import com.example.wanandroid.bean.DaoHBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface DaoHServer {
    String baseurl="https://www.wanandroid.com/";

    @GET("navi/json")
    Observable<DaoHBean>getDao();
}
