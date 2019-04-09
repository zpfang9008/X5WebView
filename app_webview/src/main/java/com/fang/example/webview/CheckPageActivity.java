package com.fang.example.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tencent.smtt.sdk.WebView;

public class CheckPageActivity extends AppCompatActivity {

    public static void start(Context context) {
        Intent starter = new Intent(context, CheckPageActivity.class);
        context.startActivity(starter);
    }

    private WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check_page);
        mWebView = findViewById(R.id.webview);
        mWebView.loadUrl("http://debugtbs.qq.com");
    }
}
