package com.example.wanandroid.model;


import com.example.wanandroid.base.BaseObserver;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;

//关联RxJava的相关回调的
public class RequestImp {
    private ApiService mApiService;

    public RequestImp() {
        mApiService = RetrofitManager.getmManager().
                getApiService();
    }

    public void getData(String url, BaseObserver<ResponseBody> observer) {
        mApiService.getData(url).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(observer);
    }


    //有请求头的get请求
    public void getData(String url, HashMap<String, String> headerMap,
                        BaseObserver<ResponseBody> observer) {
        if (headerMap != null)
            mApiService.getData(url, headerMap).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(observer);
        else
            getData(url, observer);
    }


    //有参数的get请求
    public void getData(String url, Map<String, Object> paramMap,
                        BaseObserver<ResponseBody> observer) {
        if (paramMap != null)
            mApiService.getData(url, paramMap).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(observer);
        else
            getData(url, observer);
    }


    //文件下载
    public void downloadFile(String fileUrl, String filePath,
                             BaseObserver<ResponseBody>
                                     observer) {
        mApiService.downloadFile(fileUrl, filePath).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(observer);
    }


    //没有参数的post请求
    public void postData(String url,
                         BaseObserver<ResponseBody> observer) {
        mApiService.postData(url).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(observer);

    }


    //只有请求头的post请求
    public void postData(String url, HashMap<String, String> headerMap,
                         BaseObserver<ResponseBody> observer) {
        if (headerMap != null)
            mApiService.postData(url, headerMap).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(observer);
        else
            postData(url, observer);
    }


    //有参数的post请求
    public void postData(String url, Map<String, String> paramMap,
                         BaseObserver<ResponseBody> observer) {
        if (paramMap != null)
            mApiService.postData(url, paramMap).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(observer);
        else
            postData(url, observer);
    }


    //有请求头并且有参的post请求
    public void postData(String url, HashMap<String, String> headerMap,
                         Map<String, String> paramMap,
                         BaseObserver<ResponseBody> observer) {
        if (paramMap == null && headerMap == null)
            postData(url, observer);
        else if (headerMap == null && paramMap != null)
            postData(url, paramMap, observer);
        else if (headerMap != null && paramMap == null)
            postData(url, headerMap, observer);
        else if (headerMap != null && paramMap != null)
            mApiService.postData(url, headerMap, paramMap).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(observer);
    }


    //参数是json串的post请求  没有请求头的
    public void postData(String url,
                         RequestBody body, BaseObserver<ResponseBody> observer) {
        mApiService.postData(url, body).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(observer);

    }

    //参数是json串的post请求  有请求头的
    public void postData(String url, Map<String, String> headerMap,
                         RequestBody body, BaseObserver<ResponseBody> observer) {
        if (headerMap != null)
            mApiService.postData(url, headerMap, body).
                    subscribeOn(Schedulers.io()).
                    observeOn(AndroidSchedulers.mainThread()).
                    subscribe(observer);
        else
            postData(url, body, observer);

    }


    public void uploadFile(String fileUrl,
                           MultipartBody.Part part,
                           BaseObserver<ResponseBody> observer) {
        mApiService.uploadFile(fileUrl, part).
                subscribeOn(Schedulers.io()).
                observeOn(AndroidSchedulers.mainThread()).
                subscribe(observer);
    }


}
