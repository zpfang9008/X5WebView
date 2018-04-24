package com.fangzp.x5module;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;

import com.tencent.smtt.export.external.interfaces.IX5WebSettings;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

/**
 * @author fangzhenpeng
 * @date 2017/10/23
 * @description 基于腾讯TBS的webView
 */

public class X5WebView extends WebView {

    public X5WebView(Context context) {
        super(context);
        initData();
    }

    public X5WebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        initData();
    }

    public X5WebView(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
        initData();
    }

    private void initData() {
        initClient();
        initSetting();
    }

    private void initClient() {
        this.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initSetting() {
        WebSettings webSetting = this.getSettings();
        //set js
        webSetting.setJavaScriptEnabled(true);
        webSetting.setJavaScriptCanOpenWindowsAutomatically(true);

        webSetting.setAllowFileAccess(true);
        webSetting.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSetting.setSupportZoom(true);
        webSetting.setBuiltInZoomControls(true);
        webSetting.setUseWideViewPort(true);
        webSetting.setSupportMultipleWindows(true);
        webSetting.setLoadWithOverviewMode(true);
        //支持自动加载图片
        webSetting.setLoadsImagesAutomatically(true);
        //设置编码格式
        webSetting.setDefaultTextEncodingName("utf-8");

        webSetting.setAppCacheEnabled(true);
        webSetting.setDatabaseEnabled(true);
        webSetting.setDomStorageEnabled(true);
        webSetting.setGeolocationEnabled(true);
        webSetting.setAppCacheMaxSize(Long.MAX_VALUE);

        webSetting.setPluginState(WebSettings.PluginState.ON_DEMAND);
        webSetting.setCacheMode(WebSettings.LOAD_NO_CACHE);
    }
}
