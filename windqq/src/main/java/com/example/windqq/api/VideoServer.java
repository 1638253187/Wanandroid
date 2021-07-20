package com.example.windqq.api;

import com.example.windqq.bean.VideoBean;

import io.reactivex.Observable;
import retrofit2.http.GET;

public interface VideoServer {
    String baseurl="https://api.apiopen.top/";
    @GET("videoCategoryDetails?id=14")
    Observable<VideoBean>getVideo();
}
