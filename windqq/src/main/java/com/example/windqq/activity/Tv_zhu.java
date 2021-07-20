package com.example.windqq.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.windqq.R;

public class Tv_zhu extends AppCompatActivity {

    private WebView mWeb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_tv_zhu);
        initView ();
    }

    private void initView() {
        mWeb = (WebView) findViewById (R.id.web);
        mWeb.setWebViewClient (new WebViewClient ());
        mWeb.loadUrl ("https://zc.qq.com/phone/");
    }
}
