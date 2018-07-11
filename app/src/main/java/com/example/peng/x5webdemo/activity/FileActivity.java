package com.example.peng.x5webdemo.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.example.peng.x5webdemo.R;
import com.fangzp.x5module.CheckUtils;
import com.fangzp.x5module.FileView;
import com.tencent.smtt.sdk.TbsListener;

import java.io.File;

/**
 * @author fangzhenpeng
 * @date 2018/7/11 15:39
 * @description
 * @edit
 */
public class FileActivity extends AppCompatActivity {

    private static final String TAG = "FileActivity";
    private static final String KEY_PATH = "PATH";

    public static void start(final Context context, String filePath) {
        if (CheckUtils.isCoreInit()) {
            Intent starter = new Intent(context, FileActivity.class);
            starter.putExtra(KEY_PATH, filePath);
            context.startActivity(starter);
        } else {
            checkCore(context);
        }
    }

    private FileView mFileView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_files);
        mFileView = findViewById(R.id.office_web);

        if (getIntent().hasExtra(KEY_PATH)) {
            String path = getIntent().getStringExtra(KEY_PATH);
            Log.d(TAG, "onCreate: "+path);
            display(path);
        }
    }

    private static void checkCore(final Context context) {
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

    private void display(String filePath) {
        mFileView.displayFile(new File(filePath));
    }
}
