package com.example.windqq.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.windqq.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Tv_User extends AppCompatActivity {

    @BindView(R.id.web)
    WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_tv__user);
        ButterKnife.bind (this);
        web.setWebViewClient (new WebViewClient ());
        web.loadUrl ("https://aq.qq.com/cn2/index");
    }
}
