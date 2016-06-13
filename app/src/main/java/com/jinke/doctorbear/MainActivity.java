package com.jinke.doctorbear;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.NotificationCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;

import com.jinke.doctorbear.Activity.HomeTextEdit;
import com.jinke.doctorbear.Fragment.DoctorFragment;
import com.jinke.doctorbear.Fragment.HomeFragment;
import com.jinke.doctorbear.Fragment.MeFragment;
import com.jinke.doctorbear.Fragment.SearchFragment;
import com.jinke.doctorbear.Utils.GlobalAddress;
import com.jinke.doctorbear.Widget.TabIndicatorView;

import org.json.JSONObject;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.UserInfo;

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

    private ImageView edit_imageV ;
    private FragmentTabHost tabHost;
    SharedPreferences sp;
    private NotificationManager mNotificationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNotificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        sp = getSharedPreferences("info",MODE_PRIVATE);
        sp.edit().putBoolean("page",true).commit();
        //初始化布局
        initView();
        //初始化监听
        initListener();
        RongIM.setOnReceiveMessageListener(new MyReceiveMessageListener());

     //   String token="P6jBoNrgnMcn/yrgY3h9++W0IBYzjKRtM/pAGwzxsTsVMO0PPSJjnfIz8lhL6EV7wkT5P1slZr/n+n6Q4D1XvgsTwyiJVjZ9";
        if (GlobalAddress.getToken(MainActivity.this).equals("")){
            Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
            if (weibo.isAuthValid())
            weibo.removeAccount(true);

        }else{
            connect(GlobalAddress.getToken(MainActivity.this));
            System.out.println(GlobalAddress.getToken(MainActivity.this)+"lalalalallalalallalalaa");
        }
    }
    private void initView() {
        edit_imageV =(ImageView)findViewById(R.id.fg_home_iv_edit);

        //初始化TabHost
        tabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        tabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
        //新建TabSpec
        TabHost.TabSpec spec = tabHost.newTabSpec(TAB_HOME);
        //新建Indicator，并设置Indicator属性
        IndicatorHome = new TabIndicatorView(this);
        IndicatorHome.setTabTitle("首页");
        IndicatorHome.setTabIcon(R.mipmap.doctor_bear_tabbar_index, R.mipmap.doctor_bear_tabbar_index_on);
        spec.setIndicator(IndicatorHome);
        //添加TabSpec
        tabHost.addTab(spec, HomeFragment.class, null);

        spec = tabHost.newTabSpec(TAB_SEARCH);
        IndicatorSearch = new TabIndicatorView(this);
        IndicatorSearch.setTabTitle("搜索");
        IndicatorSearch.setTabIcon(R.mipmap.doctor_bear_tabbar_search, R.mipmap.doctor_bear_tabbar_search_on);
        spec.setIndicator(IndicatorSearch);
        tabHost.addTab(spec, SearchFragment.class, null);

        spec = tabHost.newTabSpec(TAB_DOCTOR);
        IndicatorDiscover = new TabIndicatorView(this);
        IndicatorDiscover.setTabTitle("熊大夫");
        IndicatorDiscover.setTabIcon(R.mipmap.doctor_bear_tabbar_bear, R.mipmap.doctor_bear_tabbar_bear_on);
        spec.setIndicator(IndicatorDiscover);
        tabHost.addTab(spec, DoctorFragment.class, null);

        spec = tabHost.newTabSpec(TAB_ME);
        IndicatorMe = new TabIndicatorView(this);
        IndicatorMe.setTabTitle("我");
        IndicatorMe.setTabIcon(R.mipmap.doctor_bear_tabbar_me, R.mipmap.doctor_bear_tabbar_me_on);
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

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fg_home_iv_edit:
                Intent intent = new Intent(this, HomeTextEdit.class);
                startActivity(intent);
                break;
        }
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

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);

    }
    public  void connect(String token){
        RongIM.connect(token,new Myconnect());
    }
    class Myconnect extends RongIMClient.ConnectCallback{
        @Override
        public void onError(RongIMClient.ErrorCode errorCode) {

            System.out.println("OnError"+"---------------------");
        }

        @Override
        public void onSuccess(String s) {

            System.out.println("OnSuccess"+"---------------------");
        }

        @Override
        public void onTokenIncorrect() {

            System.out.println("onTokenIncorrect"+"---------------------");
        }

        @Override
        public void onFail(RongIMClient.ErrorCode errorCode) {
            super.onFail(errorCode);
            System.out.println("onFail"+"---------------------");
        }
    }
    private class MyReceiveMessageListener implements RongIMClient.OnReceiveMessageListener {

        /**
         * 收到消息的处理。
         *
         * @param message 收到的消息实体。
         * @param left    剩余未拉取消息数目。
         * @return 收到消息是否处理完成，true 表示走自已的处理方式，false 走融云默认处理方式。
         */
        @Override
        public boolean onReceived(Message message, int left) {
            //开发者根据自己需求自行处理
            System.out.println(message.getSenderUserId()+message.getContent()+"nihaonihao");
//            if (GlobalAddress.getUserId(MainActivity.this).equals("3963183378")){
//                RongIM.getInstance().setCurrentUserInfo(new UserInfo(GlobalAddress.getUserId(MainActivity.this),
//                        GlobalAddress.getUserName(MainActivity.this), Uri.parse(GlobalAddress.getUserIcon(MainActivity.this))));
//                System.out.println(GlobalAddress.getUserId(MainActivity.this)+"测试一下id");
//                if (RongIM.getInstance() != null ) {
//                    RongIM.getInstance().startPrivateChat(MainActivity.this,message.getSenderUserId(),null);
//                }
//
//            }

            RongIM.getInstance().setCurrentUserInfo(new UserInfo(GlobalAddress.getUserId(MainActivity.this),
                        GlobalAddress.getUserName(MainActivity.this), Uri.parse(GlobalAddress.getUserIcon(MainActivity.this))));

            Uri uri = Uri.parse("rong://" + getApplicationInfo().packageName).buildUpon().appendPath("conversation").appendPath(Conversation.ConversationType.PRIVATE.getName().toLowerCase()).appendQueryParameter("targetId",
                    message.getSenderUserId()).appendQueryParameter("title", "用户").build();
            Intent it=new Intent("android.intent.action.VIEW", uri);
            PendingIntent pendingIntent = PendingIntent.getActivity(MainActivity.this, 0, it, 0);
            NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(MainActivity.this);
            mBuilder.setSmallIcon(R.drawable.bear_icon);
            mBuilder.setContentTitle("熊大夫");//设置通知栏标题
            mBuilder.setContentText("您收到了消息！");
       //     UserInfo userInfo=message.getContent().getUserInfo();
//            if (userInfo==null){
//                System.out.println("userinfo is null");
//            }
          //  System.out.println(message.getContent().getUserInfo().toString());
            mBuilder.setDefaults(Notification.DEFAULT_VIBRATE);
            mBuilder.setContentIntent(pendingIntent);
            mBuilder.setAutoCancel(true);
            mBuilder.setWhen(System.currentTimeMillis());
            mBuilder.setTicker(message.getExtra());
          //  mBuilder.setNumber(3);
            Notification notification = mBuilder.build();
            //notification.flags = Notification.FLAG_AUTO_CANCEL;
            mNotificationManager.notify(1, notification);
            System.out.println(message.toString()+":"+left+"asdfaoids");

            return true;
        }
    }
}
