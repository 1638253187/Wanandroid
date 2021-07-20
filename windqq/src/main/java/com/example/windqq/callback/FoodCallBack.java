package com.example.windqq.callback;

import com.example.windqq.bean.FoodBean;

import java.util.List;

public interface FoodCallBack {
    void onSuccess(List<FoodBean.DataBean> dataBeans);

    void onFail(String error);
}
