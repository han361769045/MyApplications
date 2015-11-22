package com.luleo.myapplications.activities;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.luleo.myapplications.R;
import com.luleo.myapplications.adapters.BaseRecyclerViewAdapter;
import com.luleo.myapplications.adapters.VideoAdapter;
import com.luleo.myapplications.listener.OttoBus;
import com.luleo.myapplications.listener.SimpleItemTouchHelperCallback;
import com.luleo.myapplications.model.BaseModelJson;
import com.luleo.myapplications.model.VideoInfo;
import com.luleo.myapplications.tools.AndroidTool;
import com.luleo.myapplications.viewgroup.MyTitleBar;
import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.ObservableScrollState;
import com.marshalchen.ultimaterecyclerview.ObservableScrollViewCallbacks;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by leo on 2015/10/30.
 */
@EActivity(R.layout.activity_video)
public class VideoActivity extends BaseActivity {

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    CustomUltimateRecyclerview ultimateRecyclerView;

    @Bean(VideoAdapter.class)
    BaseRecyclerViewAdapter myAdapter;

    @Bean
    OttoBus bus;

    LinearLayoutManager linearLayoutManager;

    ItemTouchHelper mItemTouchHelper;

    SimpleItemTouchHelperCallback callback;

    StoreHouseHeader storeHouseHeader;

    MaterialHeader materialHeader;

    int pageIndex;

    @AfterViews
    void afterView(){

        AndroidTool.showLoadDialog(this);


        myAdapter.setOnItemClickListener(new BaseRecyclerViewAdapter.OnItemClickListener<VideoInfo>() {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, VideoInfo obj, int position) {

                VideoPlayerActivity_.intent(VideoActivity.this).videoPath(obj.getFilePath()).start();
            }

            @Override
            public void onHeaderClick(RecyclerView.ViewHolder viewHolder, int position) {

            }
        });

        myAdapter.setOnItemLongClickListener(new BaseRecyclerViewAdapter.OnItemLongClickListener<VideoInfo>() {
            @Override
            public void onItemLongClick(RecyclerView.ViewHolder viewHolder, VideoInfo obj, int position) {

                VideoPlayerActivity_.intent(VideoActivity.this).videoPath(obj.getFilePath()).start();
            }

            @Override
            public void onHeaderLongClick(RecyclerView.ViewHolder viewHolder, int position) {

            }
        });


        ultimateRecyclerView.setHasFixedSize(false);

        linearLayoutManager = new LinearLayoutManager(this);

        ultimateRecyclerView.setLayoutManager(linearLayoutManager);

        ultimateRecyclerView.setAdapter(myAdapter);

        ultimateRecyclerView.enableLoadmore();

        myAdapter.setCustomLoadMoreView(this, R.layout.custom_bottom_progressbar);

        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {

                myAdapter.getMoreData(pageIndex, itemsCount, new Object[1]);
            }
        });


        ultimateRecyclerView.setScrollViewCallbacks(new ObservableScrollViewCallbacks() {
            @Override
            public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
            }

            @Override
            public void onDownMotionEvent() {

            }

            @Override
            public void onUpOrCancelMotionEvent(ObservableScrollState observableScrollState) {
                if (observableScrollState == ObservableScrollState.DOWN) {
//                    ultimateRecyclerView.showToolbar(myTitleBar, ultimateRecyclerView, getScreenHeight());
                } else if (observableScrollState == ObservableScrollState.UP) {
//                    ultimateRecyclerView.hideToolbar(myTitleBar, ultimateRecyclerView, getScreenHeight());
                } else if (observableScrollState == ObservableScrollState.STOP) {
                }
            }
        });


//        callback= new SimpleItemTouchHelperCallback(myAdapter, SimpleItemTouchHelperCallback.UPANDDOWN,SimpleItemTouchHelperCallback.SWIPETOLEFT);
//        callback.setIsItemViewSwipeEnabled(false);
//        mItemTouchHelper = new ItemTouchHelper(callback);
//        mItemTouchHelper.attachToRecyclerView(ultimateRecyclerView.mRecyclerView);


        ultimateRecyclerView.setParallaxHeader(R.layout.recyclerview_header);
        ultimateRecyclerView.setOnParallaxScroll(new UltimateRecyclerView.OnParallaxScroll() {
            @Override
            public void onParallaxScroll(float percentage, float offset, View parallax) {
                Drawable c = myTitleBar.getBackground();
                c.setAlpha(Math.round(127 + percentage * 128));
                myTitleBar.setBackground(c);
            }
        });

        ultimateRecyclerView.setCustomSwipeToRefresh();

        refreshingString();
//        refreshingMaterial();

    }


    void refreshingString() {
        storeHouseHeader = new StoreHouseHeader(this);
        storeHouseHeader.setPadding(0, 15, 0, 0);

        storeHouseHeader.initWithString("Refreshing");
//        storeHouseHeader.initWithStringArray(R.array.akta);
        ultimateRecyclerView.mPtrFrameLayout.removePtrUIHandler(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(true);
        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {

                if(callback!=null){
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header) && !callback.isStartDrag;
                }else if(myAdapter.getOpenItems().get(0)!=-1){
                    myAdapter.closeAllExcept(null);
                    return false;
                }else{
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                }

            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {

                myAdapter.closeAllExcept(null);

                ptrFrameLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        //   ultimateRecyclerView.scrollBy(0, -50);
                        linearLayoutManager.scrollToPosition(0);
                        ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
                    }
                }, 1800);
            }
        });

    }

    void refreshingMaterial() {
        materialHeader = new MaterialHeader(this);
        int[] colors = getResources().getIntArray(R.array.google_colors);
        materialHeader.setColorSchemeColors(colors);
        materialHeader.setLayoutParams(new PtrFrameLayout.LayoutParams(-1, -2));
        materialHeader.setPadding(0, 15, 0, 10);
        materialHeader.setPtrFrameLayout(ultimateRecyclerView.mPtrFrameLayout);
        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
        ultimateRecyclerView.mPtrFrameLayout.removePtrUIHandler(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(materialHeader);

        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                if(callback!=null){
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header) && !callback.isStartDrag;
                }else{
                    return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
                }
            }

            @Override
            public void onRefreshBegin( PtrFrameLayout frame) {

                frame.postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        simpleRecyclerViewAdapter.insert("Refresh things", 0);
                        //   ultimateRecyclerView.scrollBy(0, -50);
                        linearLayoutManager.scrollToPosition(0);
                        ultimateRecyclerView.mPtrFrameLayout.refreshComplete();
                        //   changeHeaderHandler.sendEmptyMessageDelayed(2, 500);
                    }
                }, 1800);
            }
        });
    }


    @Subscribe
    public void onUpdateTitle(BaseModelJson<List<VideoInfo>> event) {

        myAdapter.insertAll(event.Data, myAdapter.getAdapterItemCount());

        pageIndex++;

    }

    @Override
    protected void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        bus.register(this);
    }

}
