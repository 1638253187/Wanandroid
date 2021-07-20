package com.example.windqq.view;

import com.example.windqq.bean.VideoBean_Tow;

import java.util.List;

public interface Video_TwoView {
    void onSuccess(List<VideoBean_Tow.ResultBean> videoBean_tows);

    void onFaile(String error);
}
