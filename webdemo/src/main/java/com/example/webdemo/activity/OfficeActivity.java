package com.example.webdemo.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.webdemo.R;
import com.fangzp.x5module.OfficeView;

import java.io.File;

public class OfficeActivity extends AppCompatActivity {

    private static final String TAG = "OfficeActivity";

    private static final String KEY_PATH = "PATH";

    public static void start(Context context, String filePath) {
        Intent starter = new Intent(context, OfficeActivity.class);
        starter.putExtra(KEY_PATH, filePath);
        context.startActivity(starter);
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
