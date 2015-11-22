package com.luleo.myapplications.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.luleo.myapplications.items.VideoItemView_;
import com.luleo.myapplications.model.VideoInfo;
import com.luleo.myapplications.rest.MyBackgroundTask;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;

/**
 * Created by leo on 2015/10/30.
 */

@EBean
public class VideoAdapter extends BaseRecyclerViewAdapter<VideoInfo> {

    @Bean
    MyBackgroundTask myBackgroundTask;

    @RootContext
    Context context;


    @AfterInject
    void afterInject(){
    }


    @Override
    public void getMoreData(int pageIndex, int pageSize, Object... objects) {

        myBackgroundTask.getContentProvider(pageIndex, pageSize, objects);
    }

    @Override
    protected View onCreateItemView(ViewGroup parent) {

        return VideoItemView_.build(parent.getContext());
    }

    @Override
    public long generateHeaderId(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }
}
