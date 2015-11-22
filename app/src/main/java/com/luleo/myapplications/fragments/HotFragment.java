package com.luleo.myapplications.fragments;

import com.luleo.myapplications.R;
import com.luleo.myapplications.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.FragmentArg;
import org.androidannotations.annotations.res.StringRes;

/**
 * Created by leo on 2015/10/18.
 */

@EFragment(R.layout.fragment_hot)
public class HotFragment extends BaseFragment {


    @FragmentArg
    MyTitleBar myTitleBar;


    @StringRes
    String hot;

    @AfterViews
    void afterView(){



    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if(!hidden){
            myTitleBar.setTitle(hot);
        }

    }

}
