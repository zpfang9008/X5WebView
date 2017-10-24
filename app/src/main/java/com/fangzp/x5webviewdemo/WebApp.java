package com.fangzp.x5webviewdemo;

import android.app.Application;
import android.util.Log;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsListener;

/**
 * 创建人 : Peng
 * 创建日期 : 2017/10/21 11:51
 * 功能描述 :
 * 修改日期 :
 */

public class WebApp extends Application {

    private static final String TAG = "WebApp";

    @Override
    public void onCreate() {
        super.onCreate();

        TbsDownloader.startDownload(this);
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(int i) {
                Log.d(TAG, "onDownloadFinish: " + i);
                init();
            }

            @Override
            public void onInstallFinish(int i) {
                Log.d(TAG, "onInstallFinish: " + i);
                init();
            }

            @Override
            public void onDownloadProgress(int i) {
                Log.d(TAG, "onDownloadProgress: " + i);
            }
        });
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
}
