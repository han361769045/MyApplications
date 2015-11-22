package com.luleo.myapplications.tools;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * Created by leo on 2015/10/24.
 */
public class AppUtil {

    public static String getAppInfo(Context context){

        PackageManager manager = context.getPackageManager();
        try {
            PackageInfo info = manager.getPackageInfo(context.getPackageName(), 0);
            String packageName = info.packageName;  //包名
            int versionCode = info.versionCode;  //版本号
            String versionName = info.versionName;   //版本名

            return  versionName;

        } catch (PackageManager.NameNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return  null;
    }

}

