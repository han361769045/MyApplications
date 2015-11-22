package com.luleo.myapplications.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.jude.swipbackhelper.SwipeListener;

import org.androidannotations.annotations.EActivity;

/**
 * Created by leo on 2015/10/18.
 */

@EActivity
public abstract class BaseActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)//获取当前页面
                .setSwipeBackEnable(true)//设置是否可滑动
                .setSwipeEdge(80)//可滑动的范围。px。200表示为左边200px的屏幕
                .setSwipeEdgePercent(0.08f)//可滑动的范围。百分比。0.2表示为左边20%的屏幕
                .setSwipeSensitivity(0.5f)//对横向滑动手势的敏感程度。0为迟钝 1为敏感
//                .setScrimColor(Color.BLUE)//底层阴影颜色
                .setClosePercent(0.8f)//触发关闭Activity百分比
                .setSwipeRelateEnable(true)//是否与下一级activity联动(微信效果)。默认关
                .setSwipeRelateOffset(200)//activity联动时的偏移量。默认500px。
                .addListener(new SwipeListener() {//滑动监听
                    @Override
                    public void onScroll(float percent, int px) {//滑动的百分比与距离
                    }

                    @Override
                    public void onEdgeTouch() {//当开始滑动
                    }

                    @Override
                    public void onScrollToClose() {//当滑动关闭
                    }
                });
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }

    @Override
    public void finish() {
        super.finish();
    }
}
