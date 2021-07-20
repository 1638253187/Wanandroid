package com.example.windqq.api;

import com.example.windqq.bean.FoodBean;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface FoodServer {
    String baseurl="http://www.qubaobei.com/ios/cf/";

    @GET()
    Observable<FoodBean>getFood(@Url String url);
}
