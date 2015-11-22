package com.luleo.myapplications.activities;

import android.media.MediaPlayer;
import android.view.View;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.luleo.myapplications.R;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.ViewById;
import org.springframework.util.StringUtils;

/**
 * Created by leo on 2015/11/1.
 */
@EActivity(R.layout.activity_video_player)
public class VideoPlayerActivity extends BaseActivity {


    @ViewById
    ProgressBar loading;

    @ViewById
    VideoView videoView;

    MediaController mController;

    @Extra
    String videoPath;

    int progress=0;

    @AfterInject
    void afterInject(){
        mController = new MediaController(this);
    }


    @AfterViews
    void afterView(){
        videoView.setMediaController(mController);
        mController.setMediaPlayer(videoView);

        if(!StringUtils.isEmpty(videoPath)){
            videoView.setVideoPath(videoPath);
            videoView.requestFocus();
            videoView.start();
        }

        loading.setVisibility(View.VISIBLE);
        loading.setProgress(0);

        videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                loading.setProgress(100);
                loading.setVisibility(View.GONE);
            }
        });

        videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {

                mp.pause();

            }
        });

//        mController.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                videoView.start();
//                return true;
//            }
//        });





    }
    @Override
    protected void onPause()
    {
        // TODO Auto-generated method stub
        super.onPause();
        videoView.pause();
    }
    @Override
    protected void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        videoView.seekTo(progress);
    }

}
