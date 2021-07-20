package com.example.wanandroid.callback;

public interface BaseView<K, V> {
    void onSuccess(K k);
    void onFail(V v);
}

