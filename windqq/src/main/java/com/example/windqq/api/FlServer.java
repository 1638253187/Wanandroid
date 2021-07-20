package com.example.windqq.api;

import com.example.windqq.bean.FlBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface FlServer {
    String baseurl = "http://gank.io/api/data/";

    @GET("福利/20/{page}")
    Observable<FlBean> getFl(@Path("page") int page);
}
