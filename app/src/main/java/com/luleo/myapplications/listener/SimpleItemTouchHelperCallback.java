package com.luleo.myapplications.listener;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;

import com.luleo.myapplications.adapters.BaseRecyclerViewAdapter;
import com.marshalchen.ultimaterecyclerview.itemTouchHelper.ItemTouchHelperAdapter;
import com.marshalchen.ultimaterecyclerview.itemTouchHelper.ItemTouchHelperViewHolder;

/**
 * Created by leo on 2015/8/29.
 */
public class SimpleItemTouchHelperCallback  extends ItemTouchHelper.Callback{

    public static final float ALPHA_FULL = 1.0f;

    private final ItemTouchHelperAdapter mAdapter;

    private  boolean isItemViewSwipeEnabled = true;

    private  boolean isLongPressDragEnabled = true;

    private int dragFlags = ItemTouchHelper.UP | ItemTouchHelper.DOWN | ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;

    public static  int UPANDDOWN = ItemTouchHelper.UP | ItemTouchHelper.DOWN;

    public static  int SWIPETORIGHT = ItemTouchHelper.END;

    public static  int SWIPETOLEFT = ItemTouchHelper.START;

    private int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;

    public boolean isStartDrag=false;


    /**
     *
     * @param adapter
     */
    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter ) {
        mAdapter = adapter;
    }

    /**
     *
     * @param adapter
     * @param dragFlags
     * @param swipeFlags
     */
    public SimpleItemTouchHelperCallback(ItemTouchHelperAdapter adapter,int dragFlags,int swipeFlags ) {
        this(adapter);
        this.dragFlags=dragFlags;
        this.swipeFlags=swipeFlags;
    }


    public void setIsItemViewSwipeEnabled(boolean isItemViewSwipeEnabled) {
        this.isItemViewSwipeEnabled = isItemViewSwipeEnabled;
    }

    public void setIsLongPressDragEnabled(boolean isLongPressDragEnabled) {
        this.isLongPressDragEnabled = isLongPressDragEnabled;
    }

    @Override
    public boolean isLongPressDragEnabled() {
        return isLongPressDragEnabled;
    }

    @Override
    public boolean isItemViewSwipeEnabled() {

        return isItemViewSwipeEnabled;
    }

    @Override
    public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        // Set movement flags based on the layout manager
        if(viewHolder.getItemViewType()== BaseRecyclerViewAdapter.VIEW_TYPES.NORMAL){
            return makeMovementFlags(dragFlags, swipeFlags);
        }
        return makeMovementFlags(dragFlags, 1>>1);
    }

    @Override
    public boolean canDropOver(RecyclerView recyclerView, RecyclerView.ViewHolder current,
                               RecyclerView.ViewHolder target) {
        if (current.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        return super.canDropOver(recyclerView, current, target);
    }


    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder source, RecyclerView.ViewHolder target) {
        if (source.getItemViewType() != target.getItemViewType()) {
            return false;
        }
        // Notify the adapter of the move
        mAdapter.onItemMove(source.getAdapterPosition(), target.getAdapterPosition());

        return true;
    }
    @Override
    public void onMoved(final RecyclerView recyclerView,
                        final RecyclerView.ViewHolder viewHolder, int fromPos, final RecyclerView.ViewHolder target, int toPos, int x,
                        int y) {
        super.onMoved(recyclerView,viewHolder,fromPos,target,toPos,x,y);

    }


    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int i) {
        // Notify the adapter of the dismissal
//        mAdapter.onItemDismiss(viewHolder.getAdapterPosition());
    }


    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
//        float temp=viewHolder.itemView.getWidth()/2;
        if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE ) {
            // Fade out the view as it is swiped out of the parent's bounds
            final float alpha = ALPHA_FULL - Math.abs(dX) / (float) viewHolder.itemView.getWidth();
            viewHolder.itemView.setAlpha(alpha);
            viewHolder.itemView.setTranslationX(dX);
//            Log.i("==================", -temp + "'");
//            Log.i("==================",dX+"'");
//            viewHolder.itemView.setTranslationX(-temp>dX?-temp:dX);
        }
//        else if(actionState ==ItemTouchHelper.DOWN){
//
//            viewHolder.itemView.setTranslationX(0);
//        }
        else{
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    }
    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView,
                                RecyclerView.ViewHolder viewHolder,
                                float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDrawOver(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }

    /**
     *  在每次View Holder的状态变成拖拽 (ACTION_STATE_DRAG) 或者 滑动 (ACTION_STATE_SWIPE)的时候被调用。
     * @param viewHolder
     * @param actionState
     */
    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        // We only want the active item to change
        isStartDrag=false;
        if (actionState != ItemTouchHelper.ACTION_STATE_IDLE) {
            isStartDrag=true;
            if (viewHolder instanceof ItemTouchHelperViewHolder) {
                // Let the view holder know that this item is being moved or dragged
                ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
                itemViewHolder.onItemSelected();
            }
            if (viewHolder.itemView instanceof ItemTouchHelperViewHolder) {
                // Let the view holder know that this item is being moved or dragged
                ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder.itemView;
                itemViewHolder.onItemSelected();
            }
        }
        super.onSelectedChanged(viewHolder, actionState);
    }

    /**
     * 在 swipe 或者 drag 动画完事之后 调用次方法
     * @param recyclerView
     * @param viewHolder
     */
    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        super.clearView(recyclerView, viewHolder);
        viewHolder.itemView.setAlpha(ALPHA_FULL);

        if (viewHolder instanceof ItemTouchHelperViewHolder) {
            // Tell the view holder it's time to restore the idle state
            ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder;
            itemViewHolder.onItemClear();
        }
        if (viewHolder.itemView instanceof ItemTouchHelperViewHolder) {
            // Let the itemView know that this item is being moved or dragged
            ItemTouchHelperViewHolder itemViewHolder = (ItemTouchHelperViewHolder) viewHolder.itemView;
            itemViewHolder.onItemClear();
        }
    }




}
