package com.example.lin_new_project.fragment;


import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.ValueCallback;
import android.webkit.WebView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.lin_new_project.webview.MyWebViewClient;
import com.example.lin_new_project.webview.WebSettingsUtils;
import com.example.lin_new_project.databinding.FragmentWebviewBinding;
import com.example.lin_new_project.fun.ToastMethod;
import com.example.lin_new_project.viewBinding.BaseBindingFragment;


public class WebViewFragment extends BaseBindingFragment<FragmentWebviewBinding> implements MyWebViewClient.OnWebViewClient {
    MyWebViewClient myWebViewClient;
    @Override
    protected FragmentWebviewBinding onCreateViewBinding(@NonNull LayoutInflater layoutInflater
    ) {
        return FragmentWebviewBinding.inflate(layoutInflater);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
    }

    private void initView() {
        myWebViewClient=new MyWebViewClient(getActivity());
        myWebViewClient.setOnWebViewClientl(this);
        getBinding().webView.clearCache(true);
        getBinding().webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        getBinding().webView.getSettings().setAllowContentAccess(true);
        getBinding().webView.setWebViewClient(myWebViewClient);
        getBinding().webView.getSettings().setBlockNetworkImage(false);
        WebSettingsUtils.setDefaultWebSettings(getContext(), getBinding().webView);
        getBinding().webView.loadUrl("file:///android_asset/text2.html");
        getBinding().webView.addJavascriptInterface(new AndroidApp(), "AndroidApp");//這段式測試如果從網頁回拋資料給android原生代碼範例

    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

    }

    @Override
    public void onPageFinished(WebView view, String url) {
        getBinding().webView.evaluateJavascript("javascript:javatojswith('我来自Jav51212a')",
                new ValueCallback<String>() {
                    @Override
                    public void onReceiveValue(String value) {
                        Log.d("avascript回傳", value);
//                        ToastMethod.showToast(WebView_JavascriptActivity.this,value);
                    }
                });
    }

    private class AndroidApp {
        @JavascriptInterface//這個方法名稱是由根後端工程師討論定義下來的 可變動
        public void JsToJavaInterface(String value) {
            Log.d("順序", "JsToJavaInterface");

            ToastMethod.showToast(getContext(), value);

        }
    }
}