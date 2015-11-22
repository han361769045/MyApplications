package com.luleo.myapplications.rest;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.provider.MediaStore;

import com.luleo.myapplications.MyApplication;
import com.luleo.myapplications.listener.OttoBus;
import com.luleo.myapplications.model.BaseModel;
import com.luleo.myapplications.model.BaseModelJson;
import com.luleo.myapplications.model.VideoInfo;
import com.luleo.myapplications.tools.AndroidTool;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.App;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.res.StringRes;
import org.androidannotations.annotations.rest.RestService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2015/8/23.
 */
@EBean
public class MyBackgroundTask {

    @RootContext
    Context context;

    @Bean
    OttoBus bus;

    @StringRes
    String netTimeout,noNet;

    @SystemService
    ConnectivityManager connManager;

    @RestService
    MyRestClient myRestClient;

    @Bean
    MyErrorHandler myErrorHandler;

    @App
    MyApplication myApplication;


    Cursor cursor;

    String[] projection = new String[]{
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.TITLE,
            MediaStore.Video.Media.MIME_TYPE};

    String orderBy = MediaStore.Video.Media.DISPLAY_NAME;

    Uri uri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;

    ContentResolver contentResolver;


//    @Pref
//    MyPrefs_ myPrefs_;

    @AfterInject
    void afterInject(){
        myRestClient.setRestErrorHandler(myErrorHandler);
        //bus.register(this);
    }

    @AfterViews
    void afterView(){

    }

    @Background(delay = 3000)
    public void doSomethingInBackground() {
        // do something
        if(isConnected()){
            updateUI(new BaseModel(true,"测试"));

        }else{
            updateUI();
        }
    }

    @UiThread
    void  updateUI(){
        AndroidTool.dismissLoadDialog();
        AndroidTool.showToast(context, noNet);
    }

    @UiThread
    void updateUI(BaseModel result) {
        //t.showResult(result);
        AndroidTool.dismissLoadDialog();
        if(result==null){
            AndroidTool.showToast(context, netTimeout);

        } else if(result.Successful){

        }else{
            AndroidTool.showToast(context,result.Error);
        }
        bus.post(result);
    }

    @UiThread
    void updateUI(BaseModelJson result) {
        AndroidTool.dismissLoadDialog();
        if(result==null){
            AndroidTool.showToast(context, netTimeout);

        } else if(result.Successful){

            bus.post(result);

        }else{
            AndroidTool.showToast(context,result.Error);
        }

    }

    @Background
    public void getContentProvider(int pageIndex, int pageSize, Object... objects){

        contentResolver = context.getContentResolver();

        cursor = contentResolver.query(uri, projection, null, null, orderBy);

        if(pageIndex==3){
            updateUI();
        }else

        if(isConnected()){
            updateUI(getContentProvider(uri, projection,orderBy));
        }else{
            updateUI();
        }
    }


    /**
     * 获取ContentProvider
     * @param projection
     * @param orderBy
     */
    public BaseModelJson<List<VideoInfo>> getContentProvider(Uri uri,String[] projection, String orderBy) {
        // TODO Auto-generated method stub

        List<VideoInfo> list =new ArrayList<>();

        BaseModelJson<List<VideoInfo>> baseModelJson = new BaseModelJson<>();

        if (null == cursor) {
            return null;
        }
        while (cursor.moveToNext()) {

            VideoInfo videoInfo = new VideoInfo();

            videoInfo.setFilePath(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA)));

            videoInfo.setMimeType(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.MIME_TYPE)));

            videoInfo.setTitle(cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.TITLE)));

            int id = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media._ID));

            BitmapFactory.Options options = new BitmapFactory.Options();

            options.inDither = false;

            options.inPreferredConfig = Bitmap.Config.ARGB_8888;

            videoInfo.setB(MediaStore.Video.Thumbnails.getThumbnail(contentResolver, id, MediaStore.Images.Thumbnails.MICRO_KIND, options));

            list.add(videoInfo);
        }

        baseModelJson.Data=list;

        baseModelJson.Successful=true;

        return baseModelJson;
    }


    /**
     * 判断网络是否连接
     *
     * @return
     */

    private boolean isConnected(){

        if (null != connManager)
        {

            NetworkInfo info = connManager.getActiveNetworkInfo();
            if (null != info && info.isConnected())
            {
                if (info.getState() == NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }


    /**
     * 判断是否是wifi连接
     */
    public  boolean isWifi()
    {
        if (connManager == null)
            return false;
        return connManager.getActiveNetworkInfo().getType() == ConnectivityManager.TYPE_WIFI;

    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
       //bus.unregister(this);
    }
}
