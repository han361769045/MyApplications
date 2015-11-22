package com.luleo.myapplications.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.luleo.myapplications.items.ItemView;
import com.luleo.myapplications.items.ViewWrapper;
import com.marshalchen.ultimaterecyclerview.itemTouchHelper.ItemTouchHelperAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by leo on 2015/8/29.
 */
public abstract class RecyclerViewAdapterBase<T, V extends View> extends RecyclerView.Adapter<ViewWrapper<V>> implements ItemTouchHelperAdapter {


    private List<T> items = new ArrayList();


    private int total = 0;



    @Override
    public int getItemCount() {
        return items.size();
    }


    @Override
    public void onBindViewHolder(ViewWrapper<V> viewHolder, int position) {

        ItemView itemView = (ItemView) viewHolder.getView();

        itemView.init(items.get(position),this);
    }


    @Override
    public  ViewWrapper<V> onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewWrapper(onCreateItemView(parent, viewType),parent);
    }



    @Override
    public void onItemDismiss(int position) {
        items.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) {
        Collections.swap(items, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
       // return true;
    }

    /**
     * 获取更多的数据
     * @param pageIndex
     * @param pageSize
     * @param objects
     */
    public abstract void getMoreData(int pageIndex, int pageSize, Object... objects);

    /**
     * 设置 ItemView
     * @param parent
     * @param viewType
     * @return
     */
    protected abstract V onCreateItemView(ViewGroup parent, int viewType);



    /**
     * 设置List值
     * @param items
     */
    protected  void setItems(List<T> items) {

        this.items=items;
    }

    /**
     * 获取list
     * @return
     */
    public List<T> getItems() {
        return items;
    }


    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

}
