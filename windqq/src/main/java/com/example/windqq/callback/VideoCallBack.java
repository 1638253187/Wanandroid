package com.example.windqq.callback;

import com.example.windqq.bean.VideoBean;

import java.util.List;

public interface VideoCallBack {
    void onSuccess(List<VideoBean.ResultBean> resultBeans );
    void onFail(String error);
}
