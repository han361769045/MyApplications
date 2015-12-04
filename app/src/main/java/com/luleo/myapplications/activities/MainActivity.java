package com.luleo.myapplications.activities;


import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;

import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.flyco.dialog.widget.NormalListDialog;
import com.jude.swipbackhelper.SwipeBackHelper;
import com.luleo.myapplications.R;
import com.luleo.myapplications.fragments.CartFragment_;
import com.luleo.myapplications.fragments.HomeFragment_;
import com.luleo.myapplications.fragments.HotFragment_;
import com.luleo.myapplications.fragments.MineFragment_;
import com.luleo.myapplications.tools.AndroidTool;
import com.luleo.myapplications.viewgroup.FragmentTabHost;
import com.luleo.myapplications.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.res.ColorStateListRes;
import org.androidannotations.annotations.res.DrawableRes;
import org.androidannotations.annotations.res.StringArrayRes;

@EActivity(R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewById
    FragmentTabHost tabHost;

    @SystemService
    LayoutInflater layoutInflater;

    @ViewById
    MyTitleBar myTitleBar;

    @ViewById
    DrawerLayout drawerLayout;

    @ViewById
    NavigationView nvView;

    @StringArrayRes
    String[] tabTag, tabTitle;

    @ViewById(android.R.id.tabs)
    TabWidget tabWidget;

    Class[] classTab = {HomeFragment_.class, HotFragment_.class, null, CartFragment_.class, MineFragment_.class};

    Drawable[] drawables = new Drawable[5];

    @DrawableRes
    Drawable home_selector;

    @DrawableRes(R.drawable.home_selector)
    Drawable home_selector1;

    @DrawableRes(R.drawable.home_selector)
    Drawable home_selector2;

    @DrawableRes(R.drawable.home_selector)
    Drawable home_selector3;

    @DrawableRes(R.drawable.home_selector)
    Drawable home_selector4;

    ImageView imageView;

    ExpandableListView elv;

    String[] stringItems = {"按钮1", "按钮2", "按钮3"};

    ActionSheetDialog dialog;

    NormalListDialog normalListDialog ;


    @ColorStateListRes
    ColorStateList white;

    @AfterInject
    void afterInject() {
        drawables[0] = home_selector;
        drawables[1] = home_selector1;
        drawables[2] = home_selector2;
        drawables[3] = home_selector3;
        drawables[4] = home_selector4;
    }

    @AfterViews
    void afterView() {


        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false);

        initTab();

        myTitleBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        myTitleBar.setRightTextOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AndroidTool.showToast(MainActivity.this,"ssssssssss");
            }
        });

        nvView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        drawerLayout.closeDrawer(nvView);

                        switch (menuItem.getItemId()){

                            case R.id.nav_first_fragment:

                                AndroidTool.showToast(MainActivity.this,"nav_first_fragment");

                                break;

                            case R.id.nav_second_fragment:

                                AndroidTool.showToast(MainActivity.this,"nav_second_fragment");
                                break;
                        }

                        return true;
                    }
                });

    }

    protected void initTab() {

        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        for (int i = 0; i < tabTag.length; i++) {

            Bundle bundle = new Bundle();

            bundle.putParcelable("myTitleBar", myTitleBar);

            TabHost.TabSpec tabSpec = tabHost.newTabSpec(tabTag[i]);

            tabSpec.setIndicator(buildIndicator(i));

            tabHost.addTab(tabSpec, classTab[i], bundle);
        }

        tabWidget.getChildTabViewAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ObjectAnimator oa = ObjectAnimator.ofFloat(imageView, "rotation", 0f, 135f);
                oa.setDuration(300);
                oa.start();
                ActionSheetDialogNoTitle();
                //NormalListDialogStringArr();
            }
        });

        tabHost.setCurrentTab(0);
    }


    void NormalListDialogStringArr() {
        normalListDialog = new NormalListDialog(this, stringItems);
        normalListDialog.isTitleShow(false)//
                .layoutAnimation(null)
                .show(R.style.myDialogAnim);
        normalListDialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                normalListDialog.dismiss();
            }
        });

        normalListDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ObjectAnimator oa = ObjectAnimator.ofFloat(imageView, "rotation", 135f, 0f);
                oa.setDuration(300);
                oa.start();
            }
        });

    }

    void ActionSheetDialogNoTitle() {

        dialog= new ActionSheetDialog(this, stringItems, elv);

        dialog.isTitleShow(false).show();

        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {

                AndroidTool.showToast(MainActivity.this,stringItems[position]);

                dialog.dismiss();
            }
        });

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ObjectAnimator oa = ObjectAnimator.ofFloat(imageView, "rotation", 135f, 0f);
                oa.setDuration(300);
                oa.start();
            }
        });
    }

    protected View buildIndicator(int position) {

        View view;

        if (position == 2) {

            view = layoutInflater.inflate(R.layout.tab_indicator_add, null);

            imageView = (ImageView) view.findViewById(R.id.icon_tab);

            imageView.setImageResource(R.mipmap.tab_add);

        } else {

            view = layoutInflater.inflate(R.layout.tab_indicator, null);

//            BGABadgeImageView imageView = (BGABadgeImageView) view.findViewById(R.id.icon_tab);

            ImageView imageView = (ImageView) view.findViewById(R.id.icon_tab);

            TextView textView = (TextView) view.findViewById(R.id.text_indicator);

            imageView.setImageDrawable(drawables[position]);

//            imageView.showTextBadge("1");

            textView.setText(tabTitle[position]);

        }
        return view;
    }
}