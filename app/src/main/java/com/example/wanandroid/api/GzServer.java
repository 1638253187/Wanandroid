package com.example.wanandroid.api;

import com.example.wanandroid.bean.GzTabBean;
import com.example.wanandroid.bean.TabListBean;
import com.example.wanandroid.ui.fragment.GzListFragment;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface GzServer {

    String baseurl = "https://wanandroid.com/wxarticle/";
    String baseurl1 = "https://wanandroid.com/";
    String basesearch = "https://wanandroid.com/wxarticle/list/";

    @GET("chapters/json")
    Observable<GzTabBean> getTab();

    @GET("wxarticle/list/{id}/{page}/json")
    Observable<TabListBean> getList(@Path("id") int id, @Path("page") int page,@Query("k") String k);


}
