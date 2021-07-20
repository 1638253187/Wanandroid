package com.example.wanandroid.view;

import com.example.wanandroid.bean.ShouBean;

import java.util.List;

public interface ShouView  {
    void onSuccess(List<ShouBean.DataBean> shouBeans);

    void onFail(String error);
}
