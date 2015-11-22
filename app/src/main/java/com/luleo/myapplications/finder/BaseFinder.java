package com.luleo.myapplications.finder;


import com.luleo.myapplications.listener.DataReceiveListener;
import com.luleo.myapplications.model.BaseModelJson;
import com.luleo.myapplications.model.PagerResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2015/7/21.
 */
public abstract class BaseFinder<T> {

    private List<T> list = new ArrayList();

    private int total = 0;

    DataReceiveListener listener;

    //设置监听器
    public void setDataReceiveListener(DataReceiveListener listener) {
        this.listener = listener;
    }

    public abstract void pagingQuery(int pageIndex, int pageSize, Object... objects );

    public abstract void afterPagingQuery(BaseModelJson<PagerResult<T>> bmj);

    //刷新
    public void cleanListAndCount() {
        list.clear();
        total = 0;
    }

    //获取 list
    public List<T> getList() {
        return list;
    }

    //设置list
    public void setList(List<T> list) {
        this.list = list;
    }

    //获取总数
    public int getTotal() {
        return total;
    }

    //设置总数
    public void setTotal(int total) {
        this.total = total;
    }
}

