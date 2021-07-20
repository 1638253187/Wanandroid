package com.example.windqq.api;

import com.example.windqq.bean.VideoBean_Tow;

import io.reactivex.Observable;
import io.reactivex.Observer;
import retrofit2.http.GET;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Url;

public interface VideoServer_tow {
    String base_Url = "https://api.apiopen.top/";
    @GET()
    Observable<VideoBean_Tow> getVideo(@Url String url);
}
