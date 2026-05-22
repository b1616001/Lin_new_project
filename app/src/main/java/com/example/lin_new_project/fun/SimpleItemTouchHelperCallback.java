package com.example.lin_new_project.fun;

import android.graphics.Canvas;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;


/*
 *@Description: 侧滑删除辅助类
 *@Author: hl
 *@Time: 2019/1/4 15:52
 */
public class SimpleItemTouchHelperCallback extends ItemTouchHelper.Callback {
    private Sthc_Movement sthc_movement;

    public SimpleItemTouchHelperCallback(Sthc_Movement sthc_movement){
        this.sthc_movement = sthc_movement;
    }

    /**
     * 控制每个条目的可操作状态 - 滑动，拖拽等  来源->getFlag
     * @param recyclerView
     * @param viewHolder
     * @return
     */
    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        //int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN;
        //int swipeFlags = ItemTouchHelper.LEFT;
        int[] flags = sthc_movement.getFlag(viewHolder.getLayoutPosition());
        return makeMovementFlags(flags[0], flags[1]);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        sthc_movement.onMove(viewHolder, target);
        return true;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return true;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        sthc_movement.onSwiped(viewHolder, i);
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder,
                            float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            final float alpha = 1 - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
        }
    }

    public interface Sthc_Movement{
        public void onMove(RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target);
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction);
        public int[] getFlag(int postion);
    }
}

