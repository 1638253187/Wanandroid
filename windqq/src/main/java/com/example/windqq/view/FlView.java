package com.example.windqq.view;

import com.example.windqq.bean.FlBean;

import java.util.List;

public interface FlView {
    void onSuccess(List<FlBean.ResultsBean> resultsBeans);

    void onFail(String error);
}
