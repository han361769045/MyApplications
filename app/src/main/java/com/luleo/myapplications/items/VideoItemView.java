package com.luleo.myapplications.items;

import android.content.Context;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.luleo.myapplications.R;
import com.luleo.myapplications.model.VideoInfo;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by leo on 2015/10/30.
 */
@EViewGroup(R.layout.item_video)
public class VideoItemView extends ItemView<VideoInfo> {

    @ViewById
    ImageView pic;

    @ViewById
    TextView name;

    @ViewById
    Button delete;

    Context context;

    public VideoItemView(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void init(Object... objects) {

        pic.setImageBitmap(_data.getB());
        name.setText(_data.getTitle());
    }

    @Click
    void delete(){
        baseRecyclerViewAdapter.remove(viewHolder.getAdapterPosition());
    }

    @Override
    public void onItemSelected() {
    }

    @Override
    public void onItemClear() {

    }
}
