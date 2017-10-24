package com.fangzp.x5webviewdemo;

import android.graphics.Bitmap;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.fangzp.x5module.OfficeView;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private WebView mWebview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mWebview = (WebView) findViewById(R.id.webview);
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView webView, String s) {
                webView.loadUrl(s);
                return true;
            }
        });
        mWebview.loadUrl("https://mp.weixin.qq.com/s/evzDnTsHrAr2b9jcevwBzA");
        mWebview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView webView, String s, Bitmap bitmap) {
                super.onPageStarted(webView, s, bitmap);
                Log.d(TAG, "onPageStarted: ");
            }

            @Override
            public void onPageFinished(WebView webView, String s) {
                super.onPageFinished(webView, s);
                Log.d(TAG, "onPageFinished: ");
            }
        });

        showOffice();
    }

    private void showOffice() {
        OfficeView officeView = (OfficeView) findViewById(R.id.office_view);
        officeView.displayFile(new File(Environment.getExternalStorageDirectory() + "/abc.xlsx"));
    }
}
