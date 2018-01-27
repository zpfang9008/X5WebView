package com.example.peng.x5webdemo.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.peng.x5webdemo.R;
import com.fangzp.x5module.X5WebView;

public class WebViewActivity extends AppCompatActivity {

    private X5WebView mWebX5;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view);

        mWebX5 = findViewById(R.id.web_x5); mWebX5.loadUrl("https://x5.tencent.com/");
    }
}
