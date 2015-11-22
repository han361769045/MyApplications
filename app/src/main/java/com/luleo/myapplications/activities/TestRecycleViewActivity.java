package com.luleo.myapplications.activities;

import android.graphics.drawable.Drawable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.luleo.myapplications.R;
import com.luleo.myapplications.adapters.BaseBaseAdapter;
import com.luleo.myapplications.adapters.BaseUltimateViewAdapter;
import com.luleo.myapplications.listener.OttoBus;
import com.luleo.myapplications.listener.SimpleItemTouchHelperCallback;
import com.luleo.myapplications.model.BaseModel;
import com.luleo.myapplications.viewgroup.MyTitleBar;
import com.marshalchen.ultimaterecyclerview.CustomUltimateRecyclerview;
import com.marshalchen.ultimaterecyclerview.UltimateRecyclerView;
import com.squareup.otto.Subscribe;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.MaterialHeader;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by leo on 2015/10/27.
 */
@EActivity(R.layout.activity_test)
public class TestRecycleViewActivity extends BaseActivity {

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    CustomUltimateRecyclerview  ultimateRecyclerView;

    @Bean(BaseBaseAdapter.class)
    BaseUltimateViewAdapter myAdapter;

    @Bean
    OttoBus bus;

    LinearLayoutManager linearLayoutManager;

    StoreHouseHeader storeHouseHeader;

    MaterialHeader materialHeader;

    ItemTouchHelper mItemTouchHelper;

    @AfterViews
    void afterView(){

        ultimateRecyclerView.setHasFixedSize(false);

        linearLayoutManager = new LinearLayoutManager(this);

        ultimateRecyclerView.setLayoutManager(linearLayoutManager);

        ultimateRecyclerView.setAdapter(myAdapter);

        ultimateRecyclerView.enableLoadmore();

        myAdapter.setCustomLoadMoreView(R.layout.custom_bottom_progressbar);

        ultimateRecyclerView.setOnLoadMoreListener(new UltimateRecyclerView.OnLoadMoreListener() {
            @Override
            public void loadMore(int itemsCount, int maxLastVisiblePosition) {
                myAdapter.getMoreData(itemsCount, maxLastVisiblePosition, new Object[1]);
            }
        });


        ultimateRecyclerView.setParallaxHeader(R.layout.recyclerview_header);
        ultimateRecyclerView.setOnParallaxScroll(new UltimateRecyclerView.OnParallaxScroll() {
            @Override
            public void onParallaxScroll(float percentage, float offset, View parallax) {
                Drawable c = myTitleBar.getBackground();
                c.setAlpha(Math.round(127 + percentage * 128));
                myTitleBar.setBackground(c);
            }
        });


        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(myAdapter,SimpleItemTouchHelperCallback.UPANDDOWN,SimpleItemTouchHelperCallback.SWIPETOLEFT);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(ultimateRecyclerView.mRecyclerView);
        ultimateRecyclerView.setCustomSwipeToRefresh();

         refreshingMaterial();
//        refreshingString();
    }

    void refreshingString() {
        storeHouseHeader = new StoreHouseHeader(this);
        storeHouseHeader.setPadding(0, 15, 0, 0);

        storeHouseHeader.initWithString("Refreshing");
        //  header.initWithStringArray(R.array.akta);
        ultimateRecyclerView.mPtrFrameLayout.removePtrUIHandler(materialHeader);
        ultimateRecyclerView.mPtrFrameLayout.setHeaderView(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.addPtrUIHandler(storeHouseHeader);
        ultimateRecyclerView.mPtrFrameLayout.autoRefresh(false);
        ultimateRecyclerView.mPtrFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return  PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout ptrFrameLayout) {
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
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(final PtrFrameLayout frame) {
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
    public void onUpdateTitle(BaseModel event) {
        List<BaseModel> list = new ArrayList<>();
        for (int i = 0; i <20 ; i++) {
            BaseModel baseModel = new BaseModel();
            baseModel.Error="测试"+i;
            list.add(baseModel);
        }
        myAdapter.insertAll(list,myAdapter.getAdapterItemCount());
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
