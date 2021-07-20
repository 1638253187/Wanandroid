package com.example.wanandroid.base;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.example.wanandroid.callback.TouchCallBack;

import io.reactivex.annotations.NonNull;

public class SimpleItemTouchHelperCallBack extends ItemTouchHelper.Callback {

    private TouchCallBack touchCallBack;

    public SimpleItemTouchHelperCallBack(TouchCallBack touchCallBack) {
        this.touchCallBack = touchCallBack;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //允许上下移动
        int drag = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

        //允许左右滑动
        int right = ItemTouchHelper.RIGHT| ItemTouchHelper.LEFT;
        return makeMovementFlags(drag, right);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        touchCallBack.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition());
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        touchCallBack.onItemDelete(viewHolder.getAdapterPosition());
    }
}
