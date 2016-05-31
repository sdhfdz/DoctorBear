package com.jinke.doctorbear.MyApplication;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import cn.sharesdk.framework.ShareSDK;
import io.rong.imkit.RongIM;

/**
 * Created by sdh on 2016/5/27.
 */
public class MyApplication extends Application{
    @Override
    public void onCreate() {
        super.onCreate();
        ShareSDK.initSDK(this,"12e4d47253398");

        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
        }
    }
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }
}
