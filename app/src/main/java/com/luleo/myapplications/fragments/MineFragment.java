package com.luleo.myapplications.fragments;

import com.luleo.myapplications.R;
import com.luleo.myapplications.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

/**
 * Created by leo on 2015/10/18.
 */

@EFragment(R.layout.fragment_mine)
public class MineFragment extends  BaseFragment {

    MyTitleBar myTitleBar;


    @AfterViews
    void AfterView(){


    }

}
