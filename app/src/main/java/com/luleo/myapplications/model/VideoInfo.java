package com.luleo.myapplications.model;

import android.graphics.Bitmap;

import java.io.Serializable;

/**
 * Created by leo on 2015/10/30.
 */
public class VideoInfo implements Serializable{

    private String filePath;
    private String mimeType;
    private Bitmap b;
    private String title;


    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Bitmap getB() {
        return b;
    }

    public void setB(Bitmap b) {
        this.b = b;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
