package com.example.peng.x5webdemo.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;


import com.example.peng.x5webdemo.R;
import com.example.peng.x5webdemo.util.Utils;
import com.example.peng.x5webdemo.WebApp;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private String filePath;
    private String[] fileName = new String[]{"xls.xls",
            "xlsx.xlsx", "ppt.ppt",
            "pptx.pptx",
            "doc.doc",
            "docx.docx"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        checkPermission();

        filePath = getExternalFilesDir(null).getAbsolutePath() + File.separator;

    }

    public void openWeb(View view) {
        startActivity(new Intent(this, WebViewActivity.class));
    }

    public void openWebJs(View view) {
        startActivity(new Intent(this, WebJsActivity.class));
    }

    public void openXls(View view) {
        OfficeActivity.start(this, filePath + fileName[0]);

        Log.d(TAG, "openXls: " + (filePath + fileName[0]));
    }

    public void openXlsx(View view) {
        OfficeActivity.start(this, filePath + fileName[1]);
    }

    public void openPPt(View view) {
        OfficeActivity.start(this, filePath + fileName[2]);
    }

    public void openPPtx(View view) {
        OfficeActivity.start(this, filePath + fileName[3]);
    }

    public void openDoc(View view) {
        OfficeActivity.start(this, filePath + fileName[4]);
    }

    public void openDocx(View view) {
        OfficeActivity.start(this, filePath + fileName[5]);
    }

    private void copyOffice() {
        WebApp.getAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                File file = new File(filePath, fileName[0]);
                if (!file.exists()) {
                    Utils.copyAssets(MainActivity.this);
                }
            }
        });
    }

    public static final int PERMISSIONS_WRITE_EXTERNAL_STORAGE = 0x11;

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
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSIONS_WRITE_EXTERNAL_STORAGE);

                // PERMISSIONS_WRITE_EXTERNAL_STORAGE is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSIONS_WRITE_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.

                    copyOffice();

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
}
