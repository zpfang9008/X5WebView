package com.example.peng.x5webdemo.activity;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.peng.x5webdemo.R;
import com.example.peng.x5webdemo.WebApp;
import com.example.peng.x5webdemo.databinding.ActivitySelectFilesBinding;
import com.example.peng.x5webdemo.util.Objects;
import com.example.peng.x5webdemo.util.Utils;
import com.tencent.smtt.sdk.QbSdk;

import java.io.File;


/**
 * @author fangzhenpeng
 * @date 2018/7/11 15:14
 * @description
 * @edit
 */
public class SelectFilesActivity extends AppCompatActivity {

    private static final String TAG = "SelectFilesActivity";
    public static final int PERMISSIONS_WRITE_EXTERNAL_STORAGE = 0x11;

    private ActivitySelectFilesBinding mBinding;

    private String filePath;
    private String[] fileName = new String[]{
            "doc.doc",
            "docx.docx",
            "ppt.ppt",
            "pptx.pptx",
            "xls.xls",
            "xlsx.xlsx",
            "api_pdf.pdf"
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this,
                R.layout.activity_select_files);

        checkPermission();
        setToolBar();
        setListener();
        displayInfo();
    }

    private void setToolBar() {
        setSupportActionBar(mBinding.toolBar);
        Objects.requireNonNull(getSupportActionBar()).setTitle("打开文件");
    }

    private void setListener() {
        mBinding.tvDoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(0);
            }
        });

        mBinding.tvDocx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(1);
            }
        });

        mBinding.tvPpt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(2);
            }
        });

        mBinding.tvPptx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(3);
            }
        });

        mBinding.tvXls.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(4);
            }
        });

        mBinding.tvXlsx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(5);
            }
        });

        mBinding.tvPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(6);
            }
        });
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
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    copyOffice(this);
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

    private void copyOffice(final Activity activity) {

        WebApp.getAppExecutors().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                File file = new File(filePath, fileName[0]);
                if (!file.exists()) {
                    Utils.copyAssets(activity);
                }
            }
        });
    }

    private void startActivity(int position) {
        filePath = Objects.requireNonNull(getExternalFilesDir(null)).getAbsolutePath()
                + File.separator;
        Log.d(TAG, "startActivity: " + filePath + fileName[position]);
        FileActivity.start(this, filePath + fileName[position]);
    }

    private void displayInfo() {
        boolean canLoadX5FirstTimeThirdApp = QbSdk.canLoadX5FirstTimeThirdApp(this);
        boolean canLoadX5 = QbSdk.canLoadX5(this);
        String getMiniQBVersion = QbSdk.getMiniQBVersion(this);
        int getTbsVersion = QbSdk.getTbsVersion(this);
        String info = "canLoadX5 = " + canLoadX5
                + "\ncanLoadX5FirstTimeThirdApp = " + canLoadX5FirstTimeThirdApp
                + "\ngetMiniQBVersion = " + getMiniQBVersion
                + "\ngetTbsVersion = " + getTbsVersion;
        Log.d(TAG, "displayInfo: " + info);
        mBinding.tvInfo.setText(info);
    }
}
