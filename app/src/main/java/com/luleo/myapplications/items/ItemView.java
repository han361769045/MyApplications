package com.luleo.myapplications.items;

import android.content.Context;
import android.widget.FrameLayout;

import com.marshalchen.ultimaterecyclerview.itemTouchHelper.ItemTouchHelperViewHolder;


/**
 * Created by leo on 2015/7/21.
 */
public abstract class ItemView<T> extends FrameLayout implements ItemTouchHelperViewHolder {

    protected T _data;


    public ItemView(Context context) {
        super(context);
    }

    public void init(T t, Object... objects) {
        this._data = t;
//        this.setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        init(objects);
    }


    protected abstract void init(Object... objects);

//    float x =0;
//    float y = 0;
//    float moveX=0;
//    float moveY=0;
//
//    @Override
//    public boolean dispatchTouchEvent(MotionEvent event) {
//
//
//        int action = event.getAction();
//
//        switch (action) {
//
//            case  MotionEvent.ACTION_DOWN:
//                x = event.getX();
//                y = event.getY();
//                break;
//
//            case MotionEvent.ACTION_MOVE:
//                moveX=event.getX();
//                moveY=event.getY();
//                Log.e("dispatchTouchEvent",moveX+"ACTION_MOVE");
//
//                break;
//            case MotionEvent.ACTION_UP:
//
//                if(Math.abs(moveX-x)<100){
//                    Log.e("dispatchTouchEvent--",Math.abs(moveX-x)+"ACTION_UP");
//                    return true;
//                }
//                break;
//            case MotionEvent.ACTION_CANCEL:
//                break;
//        }
//
//        return super.dispatchTouchEvent(event);
//    }
//
//
//        @Override
//    public boolean onInterceptTouchEvent(MotionEvent event) {
//            int action = event.getAction();
//
//            switch (action) {
//
//                case  MotionEvent.ACTION_DOWN:
//                    x = event.getX();
//                    y = event.getY();
//                    break;
//
//                case MotionEvent.ACTION_MOVE:
//                    moveX=event.getX();
//                    moveY=event.getY();
//                    Log.e("onInterceptTouchEvent",moveX+"ACTION_MOVE");
//
//                    break;
//                case MotionEvent.ACTION_UP:
//
//                    if(Math.abs(moveX-x)<100){
//                        Log.e("onInterceptTouchEvent--",Math.abs(moveX-x)+"ACTION_UP");
//                        return true;
//                    }
//                    return true;
//                case MotionEvent.ACTION_CANCEL:
//                    break;
//            }
//
//        return false;
//
//    }
//
//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//
//
//        int action = event.getAction();
//
//
//        switch (action) {
//
//            case MotionEvent.ACTION_MOVE:
//                Log.e("onTouchEvent","ACTION_MOVE");
//                break;
//
//            case MotionEvent.ACTION_UP:
//
//                Log.e("ACTION_UP","ACTION_UP");
//
//                return true;
//        }
//        return super.onTouchEvent(event);
//    }



}