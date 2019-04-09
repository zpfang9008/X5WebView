package com.fang.example.webview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;

import com.fang.lib.webview.X5WebView;

public class WebActivity extends AppCompatActivity {

    private static final String TAG = "WebActivity";

    private X5WebView mWebView;

    public static void start(Context context) {
        Intent starter = new Intent(context, WebActivity.class);
        context.startActivity(starter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mWebView = new X5WebView(this);
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        addContentView(mWebView, params);
        mWebView.loadUrl("https://x5.tencent.com/product/tbi.html");

        Log.d(TAG, "onCreate: " + mWebView.getX5WebViewExtension());
    }
}
