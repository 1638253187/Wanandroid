package com.example.wanandroid.ui.activity;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.wanandroid.R;
import com.example.wanandroid.util.ToastUtil;
import com.example.wanandroid.util.imageutil.CircleImageView;
import com.wikikii.bannerlib.banner.IndicatorLocation;
import com.wikikii.bannerlib.banner.LoopLayout;
import com.wikikii.bannerlib.banner.LoopStyle;
import com.wikikii.bannerlib.banner.OnDefaultImageViewLoader;
import com.wikikii.bannerlib.banner.bean.BannerInfo;
import com.wikikii.bannerlib.banner.listener.OnBannerItemClickListener;
import com.wikikii.bannerlib.banner.view.BannerBgContainer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class Like extends AppCompatActivity {

    @BindView(R.id.iv_back)
    CircleImageView ivBack;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.banner_bg_container)
    BannerBgContainer bannerBgContainer;
    @BindView(R.id.loop_layout)
    LoopLayout loopLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_like);
        ButterKnife.bind (this);
        initListener();
        loopLayout = findViewById (R.id.loop_layout);
        bannerBgContainer = findViewById (R.id.banner_bg_container);
        loopLayout.setLoop_ms (3000);//轮播的速度(毫秒)
        loopLayout.setLoop_duration (400);//滑动的速率(毫秒)
        loopLayout.setScaleAnimation (true);// 设置是否需要动画
        loopLayout.setLoop_style (LoopStyle.Empty);//轮播的样式-默认empty
        loopLayout.setIndicatorLocation (IndicatorLocation.Center);//指示器位置-中Center
        loopLayout.initializeData (this);
        // 准备数据
        ArrayList<BannerInfo> bannerInfos = new ArrayList<> ();
        List<Object> bgList = new ArrayList<> ();
        bannerInfos.add (new BannerInfo (R.mipmap.banner_1, "first"));
        bannerInfos.add (new BannerInfo (R.mipmap.banner_2, "second"));
        bgList.add (R.mipmap.banner_bg1);
        bgList.add (R.mipmap.banner_bg2);
        // 设置监听
        loopLayout.setOnLoadImageViewListener (new OnDefaultImageViewLoader () {
            @Override
            public void onLoadImageView(ImageView view, Object object) {
                Glide.with (view.getContext ())
                        .load (object)
                        .into (view);
            }
        });
        loopLayout.setOnBannerItemClickListener (new OnBannerItemClickListener () {
            @Override
            public void onBannerClick(int index, ArrayList<BannerInfo> banner) {
                ToastUtil.showShort (index + "被点击了");
            }
        });
        if (bannerInfos.size () == 0) {
            return;
        }
        loopLayout.setLoopData (bannerInfos);
        bannerBgContainer.setBannerBackBg (this, bgList);
        loopLayout.setBannerBgContainer (bannerBgContainer);
        loopLayout.startLoop ();
    }

    private void initListener() {
        ivBack.setOnClickListener (ivlisenter);
    }

    private View.OnClickListener ivlisenter=new View.OnClickListener () {
        @Override
        public void onClick(View v) {
            finish ();
        }
    };

    void setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = getWindow ();
            // Translucent status bar
            window.setFlags (
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                    WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow ();
            window.clearFlags (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
                    | WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
            window.getDecorView ().setSystemUiVisibility (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags (WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor (Color.TRANSPARENT);
        }
    }
}
