package com.fang.example.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

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
        initWebViewSettings();

        mWebView.loadUrl("http://debugtbs.qq.com");
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSettings() {
        WebSettings webSetting = mWebView.getSettings();
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);
        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        // webSetting.setLoadWithOverviewMode(true);
        webSetting.setAppCacheEnabled(true);
        // webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);
        // webSetting.setPageCacheCapacity(IX5WebSettings.DEFAULT_CACHE_CAPACITY);
        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        // webSetting.setRenderPriority(WebSettings.RenderPriority.HIGH);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);

        webSetting.setAllowFileAccessFromFileURLs(false);
        webSetting.setAllowUniversalAccessFromFileURLs(false);

        mWebView.setWebViewClient(client);
        mWebView.setWebChromeClient(mWebChromeClient);
    }

    private WebViewClient client = new WebViewClient() {
        /**
         * 防止加载网页时调起系统浏览器
         */
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }

        @Override
        public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
            super.onPageStarted(webView, s, bitmap);
        }

        @Override
        public void onPageFinished(WebView webView, String s) {
            super.onPageFinished(webView, s);
        }
    };

    private WebChromeClient mWebChromeClient = new WebChromeClient() {

        @Override
        public void onProgressChanged(WebView webView, int i) {
            super.onProgressChanged(webView, i);
        }
    };
}
