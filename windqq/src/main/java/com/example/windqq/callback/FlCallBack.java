package com.example.windqq.callback;

import com.example.windqq.bean.FlBean;

import java.util.List;

public interface FlCallBack {
    void onSuccess(List<FlBean.ResultsBean> resultsBeans, int page);

    void onFail(String error);
}
