package com.example.peng.x5webdemo;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;

/**
 * @author Peng
 * @date 2018/1/27
 * @description
 * @edit
 */

public class WebApp extends Application {

    private static final String TAG = "WebApp";

    private static AppExecutors sAppExecutors;

    @Override
    public void onCreate() {
        super.onCreate();
        init();

        sAppExecutors = new AppExecutors();
    }

    private void init(){
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                Log.d(TAG, " onViewInitFinished is " + arg0);
            }

            @Override
            public void onCoreInitFinished() {
                Log.d(TAG, "onCoreInitFinished: ");
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(getApplicationContext(), cb);
    }

    public static AppExecutors getAppExecutors() {
        return sAppExecutors;
    }
}
