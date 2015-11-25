package com.luleo.myapplications.fragments;


import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.luleo.myapplications.R;
import com.luleo.myapplications.tools.AndroidTool;
import com.luleo.myapplications.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.StringRes;

import cn.bingoogolapple.badgeview.BGABadgeTextView;

/**
 * Created by leo on 2015/10/18.
 */

@EFragment(R.layout.fragment_home)
public class HomeFragment extends  BaseFragment implements BaseSliderView.OnSliderClickListener {


    @ViewById
    BGABadgeTextView btv_home_test;


    @ViewById
    SliderLayout sliderLayout;

    @FragmentArg
    MyTitleBar myTitleBar;


    @StringRes
    String home;

    @AfterViews
    void afterView(){

        myTitleBar.setTitle(home);

        TextSliderView textSliderView1 = new TextSliderView(getActivity());
        textSliderView1.description("Game of Thrones1").image("http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
        textSliderView1.setOnSliderClickListener(this);

        TextSliderView textSliderView2 = new TextSliderView(getActivity());
        textSliderView2.description("Game of Thrones2").image("http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
        textSliderView2.setOnSliderClickListener(this);

        TextSliderView textSliderView3 = new TextSliderView(getActivity());
        textSliderView3.description("Game of Thrones3").image("http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg");
        textSliderView3.setOnSliderClickListener(this);

        sliderLayout.addSlider(textSliderView1);
        sliderLayout.addSlider(textSliderView2);
        sliderLayout.addSlider(textSliderView3);

        btv_home_test.showTextBadge("1");

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        AndroidTool.showToast(this,hidden+"");
        if (hidden){
            sliderLayout.stopAutoCycle();
        }else{
            myTitleBar.setTitle(home);
            sliderLayout.startAutoCycle();
        }

    }

    @Override
    public void onStop() {
        sliderLayout.stopAutoCycle();
        super.onStop();
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        AndroidTool.showToast(this, slider.getDescription());
        sliderLayout.startAutoCycle();
    }
}
