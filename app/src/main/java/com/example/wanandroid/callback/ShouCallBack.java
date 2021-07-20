package com.example.wanandroid.callback;

import com.example.wanandroid.bean.ShouBean;

import java.util.List;

public interface ShouCallBack {
    void onSuccess(List<ShouBean.DataBean> shouBeans);

    void onFail(String error);
}
