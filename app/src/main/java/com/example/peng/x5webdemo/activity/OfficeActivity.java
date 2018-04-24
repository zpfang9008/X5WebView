package com.example.peng.x5webdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.example.peng.x5webdemo.R;
import com.fangzp.x5module.CheckUtils;
import com.fangzp.x5module.OfficeView;
import com.tencent.smtt.sdk.TbsListener;

import java.io.File;

public class OfficeActivity extends AppCompatActivity {

    private static final String TAG = "OfficeActivity";

    private static final String KEY_PATH = "PATH";

    public static void start(final Context context, String filePath) {
        if (CheckUtils.isCoreInit()) {
            Intent starter = new Intent(context, OfficeActivity.class);
            starter.putExtra(KEY_PATH, filePath);
            context.startActivity(starter);
        } else {
            CheckUtils.download(context, new TbsListener() {
                @Override
                public void onDownloadFinish(int i) {

                }

                @Override
                public void onInstallFinish(int i) {

                }

                @Override
                public void onDownloadProgress(final int i) {
                    if (context instanceof Activity) {
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "下载进度 = " + i, Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
        }
    }

    private OfficeView mOfficeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_office);
        mOfficeView = findViewById(R.id.office_web);

        if (getIntent().hasExtra(KEY_PATH)) {
            String path = getIntent().getStringExtra(KEY_PATH);
            displayWord(path);
        }
    }

    private void displayWord(String filePath) {
        mOfficeView.displayFile(new File(filePath));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mOfficeView.onStop();
    }
}
