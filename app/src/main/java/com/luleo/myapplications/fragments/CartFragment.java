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
@EFragment(R.layout.fragment_cart)
public class CartFragment extends BaseFragment {


    @FragmentArg
    MyTitleBar myTitleBar;


    @StringRes
    String cart;

    @AfterViews
    void afterView(){


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        if(!hidden){
            myTitleBar.setTitle(cart);
        }

    }
}
