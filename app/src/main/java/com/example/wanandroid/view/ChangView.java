package com.example.wanandroid.view;

import com.example.wanandroid.bean.ChangBean;

import java.util.List;

public interface ChangView {
    void onSuccess(List<ChangBean.DataBean>dataBeans);
    void onFail(String error);
}
