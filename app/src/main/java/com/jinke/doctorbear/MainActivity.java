package com.jinke.doctorbear;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.widget.TabHost;

import com.jinke.doctorbear.Fragment.DoctorFragment;
import com.jinke.doctorbear.Fragment.HomeFragment;
import com.jinke.doctorbear.Fragment.MeFragment;
import com.jinke.doctorbear.Fragment.SearchFragment;
import com.jinke.doctorbear.Widget.TabIndicatorView;

/**
 * Created by QZ on 2016/5/12.
 * 主页面利用TabHost实现四个Fragment的切换
 */
public class MainActivity extends FragmentActivity implements TabHost.OnTabChangeListener {
    private static final String TAB_HOME = "home";
    private static final String TAB_SEARCH = "search";
    private static final String TAB_DOCTOR = "doctor";
    private static final String TAB_ME = "me";

    private TabIndicatorView IndicatorHome;
    private TabIndicatorView IndicatorSearch;
    private TabIndicatorView IndicatorDiscover;
    private TabIndicatorView IndicatorMe;

    private FragmentTabHost tabHost;
    SharedPreferences sp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = getSharedPreferences("info",MODE_PRIVATE);
        sp.edit().putBoolean("page",true).commit();
        //初始化布局
        initView();
        //初始化监听
        initListener();
    }
    private void initView() {
        //初始化TabHost
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        //新建TabSpec
        TabHost.TabSpec spec = tabHost.newTabSpec(TAB_HOME);
        //新建Indicator，并设置Indicator属性
        IndicatorHome = new TabIndicatorView(this);
        IndicatorHome.setTabTitle("首页");
        IndicatorHome.setTabIcon(R.drawable.tab_home_normal, R.drawable.tab_home_focus);
        spec.setIndicator(IndicatorHome);
        //添加TabSpec
        tabHost.addTab(spec, HomeFragment.class, null);

        spec = tabHost.newTabSpec(TAB_SEARCH);
        IndicatorSearch = new TabIndicatorView(this);
        IndicatorSearch.setTabTitle("搜索");
        IndicatorSearch.setTabIcon(R.drawable.tab_search_normal, R.drawable.tab_search_focus);
        spec.setIndicator(IndicatorSearch);
        tabHost.addTab(spec, SearchFragment.class, null);

        spec = tabHost.newTabSpec(TAB_DOCTOR);
        IndicatorDiscover = new TabIndicatorView(this);
        IndicatorDiscover.setTabTitle("熊大夫");
        IndicatorDiscover.setTabIcon(R.drawable.tab_doctor_normal, R.drawable.tab_doctor_focus);
        spec.setIndicator(IndicatorDiscover);
        tabHost.addTab(spec, DoctorFragment.class, null);

        spec = tabHost.newTabSpec(TAB_ME);
        IndicatorMe = new TabIndicatorView(this);
        IndicatorMe.setTabTitle("我");
        IndicatorMe.setTabIcon(R.drawable.tab_me_normal, R.drawable.tab_me_focus);
        spec.setIndicator(IndicatorMe);
        tabHost.addTab(spec, MeFragment.class, null);

        //去掉Indicator之间的分割线
        tabHost.getTabWidget().setDividerDrawable(android.R.color.white);
        // 初始化tab选中
        tabHost.setCurrentTabByTag(TAB_HOME);
        IndicatorHome.setTabSelected(true);
    }
    private void initListener() {
        tabHost.setOnTabChangedListener(this);
    }

    /**
     * Indicator状态改变监听
     * @param tag Indicator对应的标签
     */
    public void onTabChanged(String tag) {
        //将四个Indicator选中状态全部置为false
        IndicatorHome.setTabSelected(false);
        IndicatorSearch.setTabSelected(false);
        IndicatorDiscover.setTabSelected(false);
        IndicatorMe.setTabSelected(false);
        
        //通过tag判断哪个标签被选中，设置选中状态为true，并切换对应的Fragment
        if (TAB_HOME.equals(tag)) {
            tabHost.setCurrentTabByTag(TAB_HOME);
            IndicatorHome.setTabSelected(true);
        } else if (TAB_SEARCH.equals(tag)) {
            tabHost.setCurrentTabByTag(TAB_SEARCH);
            IndicatorSearch.setTabSelected(true);
        } else if (TAB_DOCTOR.equals(tag)) {
            tabHost.setCurrentTabByTag(TAB_DOCTOR);
            IndicatorDiscover.setTabSelected(true);
        } else if (TAB_ME.equals(tag)) {
            tabHost.setCurrentTabByTag(TAB_ME);
            IndicatorMe.setTabSelected(true);
        }
    }
}
