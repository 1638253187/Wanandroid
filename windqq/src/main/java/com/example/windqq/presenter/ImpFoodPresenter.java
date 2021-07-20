package com.example.windqq.presenter;

import com.example.windqq.bean.FoodBean;
import com.example.windqq.callback.FoodCallBack;
import com.example.windqq.model.FoodModel;
import com.example.windqq.view.FoodView;

import java.util.List;

public class ImpFoodPresenter implements FoodPresenter, FoodCallBack {
   private FoodModel model;
   private FoodView view;

    public ImpFoodPresenter(FoodModel model, FoodView view) {
        this.model = model;
        this.view = view;
    }

    @Override
    public void getFood() {
        if (model!=null){
            model.getFood (this);
        }
    }

    @Override
    public void onSuccess(List<FoodBean.DataBean> dataBeans) {
        if (view!=null){
            view.onSuccess (dataBeans);
        }
    }

    @Override
    public void onFail(String error) {
        if (view!=null){
            view.onFail (error);
        }
    }
}
