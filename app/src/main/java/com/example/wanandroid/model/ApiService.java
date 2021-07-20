package com.example.wanandroid.model;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.QueryMap;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface ApiService {

    //get请求
    @GET
    Observable<ResponseBody> getData(@Url String url);


    //有请求头的get请求
    @GET
    Observable<ResponseBody> getData(@Url String url, @HeaderMap HashMap<String, String> headerMap);

    //有请求参数的get请求
    @GET
    Observable<ResponseBody> getData(@Url String url, @QueryMap Map<String, Object> queryMap);


    //文件下载
    @GET
    @Streaming
    Observable<ResponseBody> downloadFile(@Url String url, String filePath);


    //没有参数的POST请求
    @POST
    @FormUrlEncoded
    Observable<ResponseBody> postData(@Url String url);


    //有请求头并且没参数的POST请求
    @POST
    @FormUrlEncoded
    Observable<ResponseBody> postData(@Url String url, @HeaderMap HashMap<String, String> headerMap);


    //有参数的POST请求
    @POST
    @FormUrlEncoded
    Observable<ResponseBody> postData(@Url String url, @FieldMap Map<String, String> paramMap);


    //有请求头并且有参数的POST请求
    @POST
    @FormUrlEncoded
    Observable<ResponseBody> postData(@Url String url, @HeaderMap Map<String, String> headerMap, @FieldMap Map<String, String> paramMap);



    //只有参数是JSON串的post请求
    @POST
    Observable<ResponseBody> postData(@Url String url,
                                      @Body RequestBody requestBody);



    //有请求头，并且参数是JSON串的post请求
    @POST
    Observable<ResponseBody> postData(@Url String url, @HeaderMap Map<String, String> headerMap, @Body RequestBody requestBody);


    //文件上传
    @POST
    @Multipart
    Observable<ResponseBody> uploadFile(@Url String uploadUrl, @Part MultipartBody.Part part);

}




