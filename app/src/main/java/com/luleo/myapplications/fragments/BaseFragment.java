package com.luleo.myapplications.fragments;

import android.support.v4.app.Fragment;

import org.androidannotations.annotations.EFragment;

/**
 * Created by leo on 2015/7/21.
 */
@EFragment
public abstract class BaseFragment extends Fragment {


    @Override
    public void onPause() {
        super.onPause();
        if (isVisible()){
            onHiddenChanged(isVisible());
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        if (isVisible()){
            onHiddenChanged(!isVisible());
        }
    }
}
