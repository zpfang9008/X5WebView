package com.fang.example.webview;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.ViewGroup;

import com.fang.lib.webview.FileView;

import java.io.File;

public class FileActivity extends AppCompatActivity {

    private static final String TAG = "FileActivity";
    private static final String KEY_PATH = "PATh";

    private FileView mFileView;
    private String mPath = null;

    public static void start(Context context, String path) {
        Intent starter = new Intent(context, FileActivity.class);
        starter.putExtra(KEY_PATH, path);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("显示本地文件");

        mFileView = new FileView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(mFileView, params);

        mPath = getIntent().getStringExtra(KEY_PATH);
        if (!TextUtils.isEmpty(mPath)) {
            Log.d(TAG, "onCreate: " + mPath);
            disPlayFile();
        }
    }

    private void disPlayFile() {
        mFileView.displayFile(new File(mPath));
    }
}
