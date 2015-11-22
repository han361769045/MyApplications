package com.luleo.myapplications.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.luleo.myapplications.items.BaseItemView;
import com.luleo.myapplications.items.BaseItemView_;
import com.luleo.myapplications.model.BaseModel;
import com.luleo.myapplications.rest.MyBackgroundTask;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2015/10/28.
 */
@EBean
public class BaseBaseAdapter extends BaseUltimateViewAdapter<BaseModel,BaseItemView> {


    @Bean
    MyBackgroundTask myBackgroundTask;

    public BaseBaseAdapter(Context context) {
        super(context);
    }

    @AfterInject
    void afterInject(){

        List<BaseModel> list = new ArrayList();

        for (int i = 0; i <20 ; i++) {
            BaseModel baseModel = new BaseModel();
            baseModel.Error="测试"+i;
            list.add(baseModel);
        }
        getItems().addAll(list);
    }

    @Override
    public void getMoreData(int pageIndex, int pageSize, Object... objects) {
        myBackgroundTask.doSomethingInBackground();
    }

    @Override
    protected BaseItemView onCreateItemView(ViewGroup parent) {
        return BaseItemView_.build(parent.getContext());
    }

    @Override
    public long generateHeaderId(int position) {
        if (getItems().get(customHeaderView != null ? position - 1 : position).Error.length() > 0)
            return getItems().get(customHeaderView != null ? position - 1 : position).Error.charAt(0);
        else return -1;
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return null;
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {

    }


}
