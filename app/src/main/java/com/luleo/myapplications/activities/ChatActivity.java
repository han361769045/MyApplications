package com.luleo.myapplications.activities;

import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.luleo.myapplications.R;
import com.luleo.myapplications.viewgroup.MyTitleBar;
import com.luleo.myapplications.viewgroup.PasteEditText;
import com.luleo.myapplications.viewgroup.ResizeLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.FocusChange;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by leo on 2015/10/25.
 */

@EActivity(R.layout.activity_chat)
public class ChatActivity extends BaseActivity implements ResizeLayout.OnResizeListener {

    public static final String COPY_IMAGE = "EASEMOBIMG";

    public static final int REQUEST_CODE_COPY_AND_PASTE = 11;

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    LinearLayout barBottom,rlBottom,btnPressToSpeak,more,llFaceContainer,llBtnContainer;

    @ViewById
    RelativeLayout edittextLayout;

    @ViewById
    ResizeLayout rlRoot;

    @ViewById
    Button btnMore,btnSend,btnSetModeVoice,btnSetModeKeyboard;

    @ViewById
    PasteEditText etSendmessage;

    @ViewById
    ImageView ivEmoticonsNormal ,ivEmoticonsChecked;

//    @ViewById
//    ViewPager vPager;

    @ViewById
    ListView list;

    @SystemService
    InputMethodManager manager;

    @ViewById
    RecyclerView recyclerView;

    int keyboardHeight = 0;

    List<String> reslist;

    @AfterViews
    void afterView(){

        myTitleBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TestRecycleViewActivity_.intent(ChatActivity.this).start();
            }
        });


        rlRoot.setOnResizeListener(this);

        reslist=getExpressionRes(62);

        // 初始化表情viewpager
        List<View> views = new ArrayList<View>();
//        View gv1 = getGridChildView(1);
//        View gv2 = getGridChildView(2);
//        View gv3 = getGridChildView(3);
//        views.add(gv1);
//        views.add(gv2);
//        views.add(gv3);
//        ultimateRecyclerView.setAdapter();
    }

    @Click
    void btnSetModeVoice(){
        btnPressToSpeak.setVisibility(View.VISIBLE);
        btnSetModeVoice.setVisibility(View.GONE);
        btnSetModeKeyboard.setVisibility(View.VISIBLE);
        if(more.getVisibility()==View.VISIBLE){
            more.setVisibility(View.GONE);
        }
        hideKeyboard();
        edittextLayout.setVisibility(View.GONE);
    }

    @Click
    void btnSetModeKeyboard(){
        btnPressToSpeak.setVisibility(View.GONE);
        edittextLayout.setVisibility(View.VISIBLE);
        btnSetModeVoice.setVisibility(View.VISIBLE);
        btnSetModeKeyboard.setVisibility(View.GONE);
        showKeyboard();

    }


    @FocusChange
    void etSendmessage(boolean hasFocus){

        if (hasFocus) {
            edittextLayout
                    .setBackgroundResource(R.mipmap.input_bar_bg_active);
        } else {
            edittextLayout
                    .setBackgroundResource(R.mipmap.input_bar_bg_normal);
        }

    }

    public List<String> getExpressionRes(int getSum) {
        List<String> reslist = new ArrayList<String>();
        for (int x = 0; x <= getSum; x++) {
            String filename = "f_static_0" + x;

            reslist.add(filename);

        }
        return reslist;

    }


    @Click
    void etSendmessage(){
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        edittextLayout.setBackgroundResource(R.mipmap.input_bar_bg_active);
        more.setVisibility(View.GONE);
        ivEmoticonsNormal.setVisibility(View.VISIBLE);
        ivEmoticonsChecked.setVisibility(View.INVISIBLE);
    }

    @TextChange
    void etSendmessage(CharSequence s, int start, int before,
                       int count){
        if (!TextUtils.isEmpty(s)) {
            btnMore.setVisibility(View.GONE);
            btnSend.setVisibility(View.VISIBLE);
        } else {
            btnMore.setVisibility(View.VISIBLE);
            btnSend.setVisibility(View.GONE);
        }
    }

    @Click
    void ivEmoticonsNormal(){
        more.setVisibility(View.VISIBLE);
        hideKeyboard();

    }

    @Click
    void ivEmoticonsChecked(){
        more.setVisibility(View.GONE);
        showKeyboard();
    }

    /**
     * 隐藏软键盘
     */
     void hideKeyboard() {
         getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
         ivEmoticonsNormal.setVisibility(View.GONE);
         ivEmoticonsChecked.setVisibility(View.VISIBLE);
         reCountListHeight();
         if (getCurrentFocus() != null && manager.isActive()){
            manager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

    }

    void showKeyboard(){
        manager.showSoftInput(getCurrentFocus(), InputMethodManager.HIDE_NOT_ALWAYS);
        ivEmoticonsNormal.setVisibility(View.VISIBLE);
        ivEmoticonsChecked.setVisibility(View.GONE);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }


    @Override
    public void OnResize(int w, int h, int oldw, int oldh) {
        if (oldw != 0 && oldh != 0) {
            if (h < oldh) {// 软键盘弹出来了
                keyboardHeight = oldh - h;
                reCountListHeight();
            }
        }
    }

    private void reCountListHeight() {
        if (keyboardHeight == 0) {
            return;
        }
        setbiaoqingViewHeight();
//        layoutparams params = list.getlayoutparams();
//        params.height = listviewheight - keyboardheight;
//        list.setlayoutparams(params);
//
//        adapter.notifydatasetchanged();
//        list.smoothScrollToPositionFromTop(adapter.getCount() - 1, -keyboardHeight + sendContentView.getHeight(), 0);
    }

    /**
     * 设置表情的高度
     *
     */
    private void setbiaoqingViewHeight() {
        LayoutParams biaoqingParams = more.getLayoutParams();
        biaoqingParams.height = keyboardHeight;
        more.setLayoutParams(biaoqingParams);
    }

    public void onBackPressed() {
        ivEmoticonsNormal.setVisibility(View.VISIBLE);
        ivEmoticonsChecked.setVisibility(View.INVISIBLE);
        if(more.getVisibility()==View.VISIBLE){
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
            more.setVisibility(View.GONE);
            return;
        }else{
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        }
        super.onBackPressed();
    }

}
