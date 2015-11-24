package com.luleo.myapplications.fragments;

import android.support.v4.app.Fragment;

import com.luleo.myapplications.prefs.MyPrefs_;
import com.luleo.myapplications.rest.MyErrorHandler;
import com.luleo.myapplications.rest.MyRestClient;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.rest.RestService;
import org.androidannotations.annotations.sharedpreferences.Pref;

/**
 * Created by leo on 2015/7/21.
 */
@EFragment
public abstract class BaseFragment extends Fragment {


    @Pref
    MyPrefs_ myPrefs;

    @RestService
    MyRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;


    @AfterInject
    void afterBaseInject(){
        myRestClient.setRestErrorHandler(myErrorHandler);
    }



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
