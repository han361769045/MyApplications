package com.luleo.myapplications.items;

import android.content.Context;
import android.widget.ImageView;
import android.widget.TextView;

import com.luleo.myapplications.R;
import com.luleo.myapplications.model.BaseModel;

import org.androidannotations.annotations.EViewGroup;
import org.androidannotations.annotations.ViewById;

/**
 * Created by leo on 2015/10/28.
 */

@EViewGroup(R.layout.item_base)
public class BaseItemView extends ItemView<BaseModel> {

    @ViewById
    ImageView pic;

    @ViewById
    TextView name;

    Context context;

    public BaseItemView(Context context) {
        super(context);
        this.context=context;
    }

    @Override
    protected void init(Object... objects) {

        name.setText(_data.Error);

    }

    @Override
    public void onItemSelected() {

    }

    @Override
    public void onItemClear() {

    }

}
