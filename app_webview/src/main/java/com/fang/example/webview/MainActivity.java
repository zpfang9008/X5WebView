package com.fang.example.webview;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.fang.example.webview.util.AssetsUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.tencent.smtt.sdk.TbsListener;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    public static final int PERMISSIONS_WRITE_EXTERNAL_STORAGE = 0x11;

    private TextView mTvInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mTvInfo = findViewById(R.id.tv_info);

        setInfo();

        checkPermission();
        setDownloadListener();
    }


    public void openFile(View view) {
        final String[] files = new String[]{"api_pdf.pdf", "doc.doc", "docx.docx",
                "ppt.ppt", "pptx.pptx", "xls.xls", "xlsx.xlsx"};
        AlertDialog.Builder builder = new AlertDialog.Builder(this)
                .setItems(files, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String select = files[which];
                        File outFile = new File(getCacheDir(), select);
                        FileActivity.start(MainActivity.this, outFile.getAbsolutePath());
                    }
                });
        builder.create().show();
    }

    public void openWeb(View view) {
        WebActivity.start(this);
    }

    public void check(View view) {
        setInfo();
    }

    public void openCheckWeb(View view) {
        CheckPageActivity.start(this);
    }

    private void checkPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
            } else {
                // No explanation needed, we can request the permission.
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_PHONE_STATE},
                        PERMISSIONS_WRITE_EXTERNAL_STORAGE);
                // PERMISSIONS_WRITE_EXTERNAL_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    initFile();
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                }
                return;
            }
            default:
                break;
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    private void initFile() {
        Log.d(TAG, "initFile: ");
        AssetsUtils.copyAssets(this);
    }

    private void setDownloadListener() {
        QbSdk.setTbsListener(new TbsListener() {
            @Override
            public void onDownloadFinish(final int i) {
                mTvInfo.post(new Runnable() {
                    @Override
                    public void run() {
                        mTvInfo.setText("内核下载完成 = " + i);
                    }
                });

            }

            @Override
            public void onInstallFinish(final int i) {
                mTvInfo.post(new Runnable() {
                    @Override
                    public void run() {
                        mTvInfo.setText("onInstallFinish = " + i);

                        setInfo();
                    }
                });

            }

            @Override
            public void onDownloadProgress(final int i) {
                mTvInfo.post(new Runnable() {
                    @Override
                    public void run() {
                        mTvInfo.setText("内核下载进度 = " + i);
                    }
                });

            }
        });
    }

    private void setInfo() {
        boolean canLoadX5FirstTimeThirdApp = QbSdk.canLoadX5FirstTimeThirdApp(this);
        boolean canLoadX5 = QbSdk.canLoadX5(this);
        String getMiniQBVersion = QbSdk.getMiniQBVersion(this);
        int getTbsVersion = QbSdk.getTbsVersion(this);
        boolean result = QbSdk.isTbsCoreInited();
        String info = "canLoadX5 = " + canLoadX5
                + "\ncanLoadX5FirstTimeThirdApp = " + canLoadX5FirstTimeThirdApp
                + "\ngetMiniQBVersion = " + getMiniQBVersion
                + "\ngetTbsVersion = " + getTbsVersion
                + "\nisTbsCoreInited = " + result;
        Log.d(TAG, "displayInfo: " + info);
        mTvInfo.setText(info);

    }
}
