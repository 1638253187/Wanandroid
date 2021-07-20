package com.example.wanandroid.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.util.imageutil.CircleImageView;
import com.example.wanandroid.util.relativelayout.PaperPlaneLayout;

public class Web extends AppCompatActivity {

    private WebView mWeb;
    private CircleImageView mIvBack;
    /**
     * 每日一问 今天聊一下Gradel
     */
    private TextView mTvTitle;
    private ImageView mIvLike;
    private ImageView mIvMore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_web);
        initView ();
    }

    private void initView() {
        mWeb = (WebView) findViewById (R.id.web);
        mWeb.setWebViewClient (new WebViewClient ());
        Intent intent = getIntent ();
        String link = intent.getStringExtra ("link");
        mWeb.loadUrl (link);
        mIvBack = (CircleImageView) findViewById (R.id.iv_back);
        mTvTitle = (TextView) findViewById (R.id.tv_title);
        mIvLike = (ImageView) findViewById (R.id.iv_like);
        mIvMore = (ImageView) findViewById (R.id.iv_more);
        ivLinstenter();
    }

    private void ivLinstenter() {
        mIvBack.setOnClickListener (backlicklistener);
        mIvMore.setOnClickListener (morelicjlistener);

    }

    private ImageView.OnClickListener backlicklistener=new View.OnClickListener () {
        @Override
        public void onClick(View v) {
            finish ();
        }
    };
    private ImageView.OnClickListener morelicjlistener=new View.OnClickListener () {
        @Override
        public void onClick(View v) {
            finish ();
        }
    };
}
