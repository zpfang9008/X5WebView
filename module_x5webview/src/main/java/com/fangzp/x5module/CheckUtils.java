package com.fangzp.x5module;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsDownloader;
import com.tencent.smtt.sdk.TbsListener;

/**
 * @author peng
 * @date 2018/4/24
 * @description
 * @edit
 */
public class CheckUtils {

    private static final String TAG = "CheckUtils";

    /**
     * 判断是否初始化了x5内核
     *
     * @return true:已经初始化，false:没有初始化
     */
    public static boolean isCoreInit() {
        return QbSdk.isTbsCoreInited();
    }

    public static void download(final Context context,final TbsListener tbsListener) {
        if (!isCoreInit()) {
            QbSdk.setDownloadWithoutWifi(false);
            TbsDownloader.startDownload(context);
            Log.d(TAG, "download: ");
            QbSdk.setTbsListener(new TbsListener() {
                @Override
                public void onDownloadFinish(int i) {
                    if (tbsListener != null) {
                        tbsListener.onDownloadFinish(i);
                    }
                    Log.d(TAG, "onDownloadFinish: " + i);
                }

                @Override
                public void onInstallFinish(int i) {
                    Log.d(TAG, "onInstallFinish: " + i);
                    if (tbsListener != null) {
                        tbsListener.onInstallFinish(i);
                    }
                    if (context instanceof Activity) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "内核下载完成，请重新打开app", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }

                @Override
                public void onDownloadProgress(int i) {
                    Log.d(TAG, "onDownloadProgress: " + i);
                    if (tbsListener != null) {
                        tbsListener.onDownloadProgress(i);
                    }
                }
            });
        }
    }
}
