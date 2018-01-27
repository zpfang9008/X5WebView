package com.example.peng.x5webdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.peng.x5webdemo.R;
import com.fangzp.x5module.X5WebView;

public class WebJsActivity extends AppCompatActivity {

    private X5WebView mWebX5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_js);
        mWebX5 = findViewById(R.id.web_x5);
    }
}
