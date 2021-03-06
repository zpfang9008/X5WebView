package com.fang.example.webview;

import android.app.Application;
import android.util.Log;

import com.fang.example.webview.util.AssetsUtils;
import com.tencent.smtt.sdk.QbSdk;

/**
 * 文件名：App
 * 描述：
 *
 * @author fangzhenpeng
 * @date 2019/1/27 10:34
 */
public class App extends Application {

    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
        initX5Core();
    }

    private void initX5Core() {
        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
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
