package com.example.wanandroid.callback;

public interface TouchCallBack {
    //数据交换
    void onItemMove(int fromPosition, int toPosition);

    //数据删除
    void onItemDelete(int position);
}
