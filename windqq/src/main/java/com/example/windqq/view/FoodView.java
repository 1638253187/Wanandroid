package com.example.windqq.view;

import com.example.windqq.bean.FoodBean;

import java.util.List;

public interface FoodView {
    void onSuccess(List<FoodBean.DataBean> dataBeans);

    void onFail(String error);
}
