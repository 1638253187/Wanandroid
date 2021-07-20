package com.example.windqq.callback;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

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
        int swip = ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT;
        return makeMovementFlags(drag, swip);
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