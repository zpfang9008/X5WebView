package com.fangzp.x5webviewdemo.fragment;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.Toast;

import com.fangzp.x5module.X5WebView;
import com.fangzp.x5webviewdemo.R;
import com.fangzp.x5webviewdemo.WebJsActivity;
import com.tencent.smtt.sdk.ValueCallback;


public class WebJsFragment extends Fragment {

    private Activity mActivity;

    private X5WebView mX5Web;
    private Button mBtnJs;
    private Button mBtnJsParams;
    private Button mBtnJsEv;


    private int count = 0;

    public WebJsFragment() {
        // Required empty public constructor
    }

    public static WebJsFragment newInstance() {
        WebJsFragment fragment = new WebJsFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (Activity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_web_js, container, false);
        mX5Web = root.findViewById(R.id.x5_web);
        mBtnJs = root.findViewById(R.id.btn_js);
        mBtnJsParams = root.findViewById(R.id.btn_js_params);
        mBtnJsEv = root.findViewById(R.id.btn_js_ev);


        initWeb();
        setListener();
        return root;
    }

    private void initWeb() {
        //设置js回调方法
        mX5Web.addJavascriptInterface(new JSBridge(), "JsBridge");
        mX5Web.loadUrl("file:///android_asset/web_js.html");
    }

    private void setListener() {
        mBtnJs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mX5Web.post(new Runnable() {
                    @Override
                    public void run() {
                        mX5Web.loadUrl("javascript:androidLoadJsMethod()");
                    }
                });
            }
        });

        mBtnJsParams.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count++;
                mX5Web.post(new Runnable() {
                    @Override
                    public void run() {
                        mX5Web.loadUrl("javascript:androidLoadJsWithParams(" + getMsg() + ")");
                    }
                });
            }
        });

        mBtnJsEv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                evaluateJavascript();
            }
        });
    }

    private class JSBridge {

        /**
         * JS无参调用Android方法
         * js -> android
         */
        @JavascriptInterface
        public void jsLoadAndroid() {
            mX5Web.post(new Runnable() {
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
            mX5Web.post(new Runnable() {
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
            mX5Web.post(new Runnable() {
                @Override
                public void run() {
                    count++;
                    mX5Web.loadUrl("javascript:valueFromAndroid(" + getLocalMsg() + ")");
                }
            });
        }
    }

    private void jsLoadAndroidMethod() {
        Toast.makeText(mActivity, "JS无参调用Android方法", Toast.LENGTH_LONG).show();
    }

    private void jsLoadAndroidWithParamsMethod(String msg) {
        Toast.makeText(mActivity, "JS带参调用Android方法 = " + msg, Toast.LENGTH_LONG).show();
    }

    private String getLocalMsg() {
        return "'android msg = " + count + "'";
    }

    private String getMsg() {
        return "'Android Msg = " + count + "'";
    }

    private void evaluateJavascript() {
        mX5Web.evaluateJavascript("javascript:androidLoadJsMethod()",
                new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String s) {
                        Toast.makeText(mActivity, "s = " + s, Toast.LENGTH_SHORT).show();
                    }
                });
    }
}
