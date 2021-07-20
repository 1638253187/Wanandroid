package com.example.windqq.callback;

import com.example.windqq.bean.VideoBean_Tow;

import java.util.List;

public interface VideoTwoCallBack {
    void onSuccess(List<VideoBean_Tow.ResultBean> videoBean_tows);

    void onFaile(String error);
}
