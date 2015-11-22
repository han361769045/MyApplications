package com.luleo.myapplications.activities;


import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.jude.swipbackhelper.SwipeBackHelper;
import com.luleo.myapplications.R;
import com.luleo.myapplications.fragments.CartFragment_;
import com.luleo.myapplications.fragments.HomeFragment_;
import com.luleo.myapplications.fragments.HotFragment_;
import com.luleo.myapplications.fragments.MineFragment_;
import com.luleo.myapplications.viewgroup.FragmentTabHost;
import com.luleo.myapplications.viewgroup.MyTitleBar;

import org.androidannotations.annotations.AfterInject;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.SystemService;
import org.androidannotations.annotations.ViewById;
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
    String[] tabTag,tabTitle;

    Integer[] tabIcon = new Integer[5];

    Class[] classTab = { HomeFragment_.class, HotFragment_.class, null,CartFragment_.class,MineFragment_.class };


    @AfterInject
    void afterInject(){
        tabIcon[0]=R.mipmap.tab_home;
        tabIcon[1]=R.mipmap.tab_home;
        tabIcon[2]=R.mipmap.tab_add;
        tabIcon[3]=R.mipmap.tab_home;
        tabIcon[4]=R.mipmap.tab_home;
    }

    @AfterViews
    void afterView(){
        SwipeBackHelper.getCurrentPage(this).setSwipeBackEnable(false) ;
        initTab();


        myTitleBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });


        nvView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
                        drawerLayout.closeDrawer(nvView);
                        return true;
                    }
                });

        myTitleBar.setRightButtonOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoActivity_.intent(MainActivity.this).start();
            }
        });

    }

    protected void initTab(){

        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);

        for(int i=0;i<tabTag.length;i++){

            TabHost.TabSpec tabSpec=tabHost.newTabSpec(tabTag[i]);

            tabSpec.setIndicator(buildIndicator(i));

            tabHost.addTab(tabSpec, classTab[i], null);
        }

//        tabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

        tabHost.setCurrentTab(0);


    }

   protected View buildIndicator(int position){

        View view =null;

       if(position==2){

           view=layoutInflater.inflate(R.layout.tab_indicator_add,null);

           ImageView imageView = (ImageView) view.findViewById(R.id.icon_tab);

           imageView.setImageResource(tabIcon[position]);
       }else{

           view=layoutInflater.inflate(R.layout.tab_indicator,null);

           ImageView imageView = (ImageView) view.findViewById(R.id.icon_tab);

           TextView textView = (TextView) view.findViewById(R.id.text_indicator);

           imageView.setImageResource(tabIcon[position]);

           textView.setText(tabTitle[position]);
       }
        return  view;
    }



}
