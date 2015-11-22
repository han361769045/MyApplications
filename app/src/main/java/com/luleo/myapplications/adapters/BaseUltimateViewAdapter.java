package com.luleo.myapplications.adapters;

import android.animation.Animator;
import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Interpolator;
import android.view.animation.LinearInterpolator;

import com.luleo.myapplications.items.ItemView;
import com.luleo.myapplications.items.ViewWrapper;
import com.marshalchen.ultimaterecyclerview.UltimateViewAdapter;
import com.marshalchen.ultimaterecyclerview.animators.internal.ViewHelper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by leo on 2015/10/27.
 */
public abstract class BaseUltimateViewAdapter<T, V extends View> extends UltimateViewAdapter<ViewWrapper<V>> {

    private List<T> items = new ArrayList<>();

    private int total = 0;

    private boolean isFirstOnly = true;

    private Interpolator mInterpolator = new LinearInterpolator();

    private int mDuration = 300;

    private int mLastPosition = 5;

    private Context context;

    private OnItemClickListener onItemClickListener;

    private OnItemLongClickListener onItemLongClickListener;


    public BaseUltimateViewAdapter(Context context){

        this.context=context;
    }

    public BaseUltimateViewAdapter(){

    }

    public int getAdapterItemCount(){

        return items.size();
    }

    /**
     * 获取更多的数据
     * @param pageIndex
     * @param pageSize
     * @param objects
     */
    public abstract void getMoreData(int pageIndex, int pageSize, Object... objects);

    /**
     *
     * @param viewHolder
     * @param position
     */
    @Override
    public void onBindViewHolder(final ViewWrapper<V> viewHolder, final int position) {



        ItemView itemView =null;

        if(getItemViewType(position)==VIEW_TYPES.NORMAL){
            itemView = (ItemView) viewHolder.getView();
            itemView.init(items.get(customHeaderView != null ? position - 1 : position),this);
        }

        if (!isFirstOnly || position > mLastPosition) {
            for (Animator anim : getAdapterAnimations(viewHolder.itemView, AdapterAnimationType.ScaleIn)) {
                anim.setDuration(mDuration).start();
                anim.setInterpolator(mInterpolator);
            }
            mLastPosition = position;
        } else {
            ViewHelper.clear(viewHolder.itemView);
        }

        if(onItemClickListener!=null&&itemView!=null){
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClick(viewHolder,v,position);
                }
            });
        }
    }



    public ViewWrapper<V>  getViewHolder(View view){

        return new ViewWrapper(view);
    }

    public ViewWrapper<V> onCreateViewHolder(ViewGroup parent){
        return new ViewWrapper(onCreateItemView(parent),parent);
    }

    /**
     * 设置 ItemView
     * @param parent
     * @return
     */
    protected abstract V onCreateItemView(ViewGroup parent);


    public void insert(T object, int position) {
        items.add(position, object);
        if (customHeaderView != null) position++;
        notifyItemInserted(position);
    }

    public void insertAll(List<T> list, int position) {
        items.addAll(position, list);
        if (customHeaderView != null) position++;
        notifyItemInserted(position);
    }



    public List<T> getItems() {
        return items;
    }

    public void setItems(List<T> items) {
        this.items = items;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * Remove a item of  the list of the adapter
     *
     * @param position position
     */
    public void remove(int position) {
        if (items.size() > 0) {
            items.remove(customHeaderView != null ? position - 1 : position);
            notifyItemRemoved(position);
        }
    }

    /**
     * Clear the list of the adapter
     *
     */
    public void clear() {
        int size = items.size();
        items.clear();
        notifyItemRangeRemoved(0, size);
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
     * allow resource layout id to be introduced
     *
     * @param mLayout res id
     */
    public void setCustomLoadMoreView(@LayoutRes int mLayout){
        View h_layout = LayoutInflater.from(context).inflate(mLayout, null);
        setCustomLoadMoreView(h_layout);
    }

    /**
     *
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener{

        void onItemClick(RecyclerView.ViewHolder viewHolder, View view, int position);

    }

    public interface OnItemLongClickListener{

        void onItemLongClick(RecyclerView.ViewHolder viewHolder, View view, int position);

    }

}
