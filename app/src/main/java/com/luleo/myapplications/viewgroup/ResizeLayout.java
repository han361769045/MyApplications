package com.luleo.myapplications.viewgroup;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.RelativeLayout;

/**
 * Created by leo on 2015/10/25.
 */
public class ResizeLayout  extends RelativeLayout {

    private OnResizeListener mListener;

    public interface OnResizeListener {
        void OnResize(int w, int h, int oldw, int oldh);
    }

    public void setOnResizeListener(OnResizeListener l) {
        mListener = l;
    }

    public ResizeLayout(Context context) {
        super(context);
    }

    public ResizeLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ResizeLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        if (mListener != null) {
            mListener.OnResize(w, h, oldw, oldh);
        }
        Log.i("sdasdas","w= " + w + " h = " + h + " oldw = " + oldw + " oldh = " + oldh);
    }
}
