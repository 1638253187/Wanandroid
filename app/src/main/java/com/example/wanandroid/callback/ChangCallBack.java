package com.example.wanandroid.callback;

import com.example.wanandroid.bean.ChangBean;

import java.util.List;

public interface ChangCallBack {
    void onSuccess(List<ChangBean.DataBean> dataBeans);

    void onFail(String error);
}
