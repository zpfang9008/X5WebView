package com.fangzp.x5webviewdemo;

import android.content.Intent;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.Toast;

import com.fangzp.x5module.X5WebView;
import com.tencent.smtt.sdk.ValueCallback;
import com.tencent.smtt.sdk.WebChromeClient;

public class WebJsActivity extends AppCompatActivity {

    private X5WebView x5Web;
    private Button btnAndroidLoadJsMethod;
    private Button btnAndroidLoadJsMethodWithParams;
    private Button btn3;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_js);

        x5Web = (X5WebView) findViewById(R.id.x5_web);
        btnAndroidLoadJsMethod = (Button) findViewById(R.id.btn_1);
        btnAndroidLoadJsMethodWithParams = (Button) findViewById(R.id.btn_2);
        btn3 = (Button) findViewById(R.id.btn_3);

        btnAndroidLoadJsMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                x5Web.post(new Runnable() {
                    @Override
                    public void run() {
                        x5Web.loadUrl("javascript:androidLoadJsMethod()");
                    }
                });
            }
        });

        btnAndroidLoadJsMethodWithParams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                x5Web.post(new Runnable() {
                    @Override
                    public void run() {
                        x5Web.loadUrl("javascript:androidLoadJsWithParams(" + getMsg() + ")");
                    }
                });
            }
        });

        initFileChoose();
        //设置js回调方法
        x5Web.addJavascriptInterface(new JSBridge(), "jsBridge");
        x5Web.loadUrl("file:///android_asset/app.html");
    }

    private String getMsg() {
        return "'Android Msg = " + count + "'";
    }

    private class JSBridge {

        /**
         * JS无参调用Android方法
         * js -> android
         */
        @JavascriptInterface
        public void jsLoadAndroid() {
            x5Web.post(new Runnable() {
                @Override
                public void run() {
                    jsLoadAndroidMethod();
                }
            });
        }

        /**
         * JS带参调用Android方法
         * js -> android
         *
         * @param msg String msg
         */
        @JavascriptInterface
        public void jsLoadAndroidWithParams(final String msg) {
            x5Web.post(new Runnable() {
                @Override
                public void run() {
                    jsLoadAndroidWithParamsMethod(msg);
                }
            });
        }

        /**
         * JS调用Android方法，并传入参数，再次调用JS方法
         * js -> android -> js
         */
        @JavascriptInterface
        public void jsGetParamsFromAndroid() {
            x5Web.post(new Runnable() {
                @Override
                public void run() {
                    count++;
                    x5Web.loadUrl("javascript:valueFromAndroid(" + getLocalMsg() + ")");
                }
            });
        }
    }

    private void jsLoadAndroidMethod(){
        Toast.makeText(WebJsActivity.this, "JS无参调用Android方法", Toast.LENGTH_LONG).show();
    }

    private void jsLoadAndroidWithParamsMethod(String msg){
        Toast.makeText(WebJsActivity.this, "JS带参调用Android方法 = " + msg, Toast.LENGTH_LONG).show();
    }

    private String getLocalMsg() {
        return "'android msg = " + count + "'";
    }

    public static final int REQ_ABLUM = 0x11;


    private ValueCallback<Uri> mUploadFile;
    private ValueCallback<Uri[]> mUploadFiles;

    private void initFileChoose() {
        x5Web.setWebChromeClient(new WebChromeClient() {

            // For Android 3.0+
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
                Log.i("test", "openFileChooser 1");
                mUploadFile = uploadMsg;
                openIntent();
            }

            // For Android < 3.0
            public void openFileChooser(ValueCallback<Uri> uploadMsgs) {
                Log.i("test", "openFileChooser 2");
                mUploadFile = uploadMsgs;
                openIntent();
            }

            // For Android  > 4.1.1
            public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
                Log.i("test", "openFileChooser 3");
                mUploadFile = uploadMsg;
                openIntent();
            }

            // For Android  >= 5.0
            public boolean onShowFileChooser(com.tencent.smtt.sdk.WebView webView,
                                             ValueCallback<Uri[]> filePathCallback,
                                             WebChromeClient.FileChooserParams fileChooserParams) {
                Log.i("test", "openFileChooser 4:" + filePathCallback.toString());
                mUploadFiles = filePathCallback;
                openIntent();
                return true;
            }
        });
    }

    private void openIntent() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, REQ_ABLUM);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQ_ABLUM) {
                if (null != mUploadFile) {
                    Uri result = data == null ? null : data.getData();
                    mUploadFile.onReceiveValue(result);
                    mUploadFile = null;
                }
                if (null != mUploadFiles) {
                    Uri result = data == null ? null : data.getData();
                    mUploadFiles.onReceiveValue(new Uri[]{result});
                    mUploadFiles = null;
                }
            }
        } else if (resultCode == RESULT_CANCELED) {
            if (null != mUploadFile) {
                mUploadFile.onReceiveValue(null);
                mUploadFile = null;
            }
        }
    }
}
