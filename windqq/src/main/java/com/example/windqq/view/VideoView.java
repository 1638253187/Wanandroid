package com.example.windqq.view;

import com.example.windqq.bean.VideoBean;

import java.util.List;

public interface VideoView {
    void onSuccess(List<VideoBean.ResultBean> e6VRBeans);

    void onFail(String error);
}
