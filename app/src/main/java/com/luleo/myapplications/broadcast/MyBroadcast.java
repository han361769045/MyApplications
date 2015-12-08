package com.luleo.myapplications.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import org.androidannotations.annotations.EReceiver;
import org.androidannotations.annotations.ReceiverAction;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.api.BackgroundExecutor;

/**
 * Created by leo on 2015/7/21.
 */

@EReceiver
public class MyBroadcast extends BroadcastReceiver {

    @SystemService
    ConnectivityManager manager;





    @ReceiverAction("android.net.conn.CONNECTIVITY_CHANGE")
    public void connectivity(){
        NetworkInfo mobileInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        NetworkInfo wifiInfo = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        NetworkInfo activeInfo = manager.getActiveNetworkInfo();
        if(activeInfo==null){
            BackgroundExecutor.cancelAll("",true);
        }

    }



    @Override
    public void onReceive(Context context, Intent intent) {
        // empty, will be overridden in generated subclass
    }
}
