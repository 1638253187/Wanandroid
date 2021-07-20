package com.example.wanandroid.callback;

public abstract interface BaseCallBack<K,V> {
    void onSuccess(K K);
    void onFail(V V);
}
