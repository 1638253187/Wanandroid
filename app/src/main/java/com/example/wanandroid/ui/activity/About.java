package com.example.wanandroid.ui.activity;

import android.animation.ValueAnimator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.util.ElasticOutInterpolator;
import com.example.wanandroid.util.StatusBarUtil;
import com.example.wanandroid.util.ToastUtil;
import com.scwang.smartrefresh.header.FlyRefreshHeader;
import com.scwang.smartrefresh.header.flyrefresh.FlyView;
import com.scwang.smartrefresh.header.flyrefresh.MountainSceneView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.SimpleMultiPurposeListener;
import com.scwang.smartrefresh.layout.util.DensityUtil;

public class About extends AppCompatActivity {

    private MountainSceneView mAboutUsMountain;
    private Toolbar mAboutUsToolbar;
    private CollapsingToolbarLayout mAboutUsToolbarLayout;
    private AppBarLayout mAboutUsAppBar;
    private FlyRefreshHeader mAboutUsFlyRefresh;
    private TextView mAboutVersion;
    private TextView mAboutContent;
    private NestedScrollView mAboutUsContent;
    private SmartRefreshLayout mAboutUsRefreshLayout;
    private FloatingActionButton mAboutUsFab;
    private FlyView mAboutUsFlyView;
    private View.OnClickListener mThemeListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_about);
        initView ();
        setSupportActionBar (mAboutUsToolbar);
        StatusBarUtil.immersive (this);
        StatusBarUtil.setPaddingSmart (this, mAboutUsToolbar);
        mAboutUsToolbar.setNavigationOnClickListener (v -> onBackPressedSupport ());
        initEventAndData ();
    }
    private void onBackPressedSupport() {
        finish ();
    }

    private void initView() {
        mAboutUsMountain = (MountainSceneView) findViewById (R.id.about_us_mountain);
        mAboutUsToolbar = (Toolbar) findViewById (R.id.about_us_toolbar);
        mAboutUsToolbarLayout = (CollapsingToolbarLayout) findViewById (R.id.about_us_toolbar_layout);
        mAboutUsAppBar = (AppBarLayout) findViewById (R.id.about_us_app_bar);
        mAboutUsFlyRefresh = (FlyRefreshHeader) findViewById (R.id.about_us_fly_refresh);
        mAboutVersion = (TextView) findViewById (R.id.aboutVersion);
        mAboutContent = (TextView) findViewById (R.id.aboutContent);
        mAboutUsContent = (NestedScrollView) findViewById (R.id.about_us_content);
        mAboutUsRefreshLayout = (SmartRefreshLayout) findViewById (R.id.about_us_refresh_layout);
        mAboutUsFab = (FloatingActionButton) findViewById (R.id.about_us_fab);
        mAboutUsFlyView = (FlyView) findViewById (R.id.about_us_fly_view);
    }


    private void initEventAndData() {
        showAboutContent ();
        setSmartRefreshLayout ();

        //进入界面时自动刷新
        mAboutUsRefreshLayout.autoRefresh ();

        //点击悬浮按钮时自动刷新
        mAboutUsFab.setOnClickListener (v -> mAboutUsRefreshLayout.autoRefresh ());

        //监听 AppBarLayout 的关闭和开启 给 FlyView（纸飞机） 和 ActionButton 设置关闭隐藏动画
        mAboutUsAppBar.addOnOffsetChangedListener (new AppBarLayout.OnOffsetChangedListener () {
            boolean misAppbarExpand = true;

            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                int scrollRange = appBarLayout.getTotalScrollRange ();
                float fraction = 1f * (scrollRange + verticalOffset) / scrollRange;
                double minFraction = 0.1;
                double maxFraction = 0.8;
                if (mAboutUsContent == null || mAboutUsFab == null || mAboutUsFlyView == null) {
                    return;
                }
                if (fraction < minFraction && misAppbarExpand) {
                    misAppbarExpand = false;
                    mAboutUsFab.animate ().scaleX (0).scaleY (0);
                    mAboutUsFlyView.animate ().scaleX (0).scaleY (0);
                    ValueAnimator animator = ValueAnimator.ofInt (mAboutUsContent.getPaddingTop (), 0);
                    animator.setDuration (300);
                    animator.addUpdateListener (animation -> {
                        if (mAboutUsContent != null) {
                            mAboutUsContent.setPadding (0, (int) animation.getAnimatedValue (), 0, 0);
                        }
                    });
                    animator.start ();
                }
                if (fraction > maxFraction && !misAppbarExpand) {
                    misAppbarExpand = true;
                    mAboutUsFab.animate ().scaleX (1).scaleY (1);
                    mAboutUsFlyView.animate ().scaleX (1).scaleY (1);
                    ValueAnimator animator = ValueAnimator.ofInt (mAboutUsContent.getPaddingTop (), DensityUtil.dp2px (25));
                    animator.setDuration (300);
                    animator.addUpdateListener (animation -> {
                        if (mAboutUsContent != null) {
                            mAboutUsContent.setPadding (0, (int) animation.getAnimatedValue (), 0, 0);
                        }
                    });
                    animator.start ();
                }
            }
        });
    }

    //绑定场景和纸飞机
    private void setSmartRefreshLayout() {
        mAboutUsFlyRefresh.setUp (mAboutUsMountain, mAboutUsFlyView);
        mAboutUsRefreshLayout.setReboundInterpolator (new ElasticOutInterpolator ());
        mAboutUsRefreshLayout.setReboundDuration (800);
        mAboutUsRefreshLayout.setOnRefreshListener (refreshLayout -> {
            updateTheme ();
            refreshLayout.finishRefresh (1000);
        });
        //设置让Toolbar和AppBarLayout的滚动同步
        mAboutUsRefreshLayout.setOnMultiPurposeListener (new SimpleMultiPurposeListener () {
            @Override
            public void onRefresh(RefreshLayout refreshLayout) {
                super.onRefresh (refreshLayout);
                refreshLayout.finishRefresh (2000);
            }

            @Override
            public void onLoadMore(RefreshLayout refreshLayout) {
                super.onLoadMore (refreshLayout);
                refreshLayout.finishLoadMore (3000);
            }

            @Override
            public void onHeaderMoving(RefreshHeader header, boolean isDragging, float percent, int offset, int headerHeight, int maxDragHeight) {
                super.onHeaderMoving (header, isDragging, percent, offset, headerHeight, maxDragHeight);
                if (mAboutUsAppBar == null || mAboutUsToolbar == null) {
                    return;
                }
                mAboutUsAppBar.setTranslationY (offset);
                mAboutUsToolbar.setTranslationY (-offset);
            }
        });
    }

    private class TextClick extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            //在此处理点击事件
            Intent intent = new Intent (getApplicationContext (), Web.class);
            String link = "https://github.com/hongyangAndroid/xueandroid";
            intent.putExtra ("link", link);
            startActivity (intent);

        }
    }

    private class TextClick_two extends ClickableSpan {
        @Override
        public void onClick(View widget) {
            //在此处理点击事件
            joinQQGroup ("j9k64xyX5wJ7v2MQ3EopMXDm_W85YDKQ");
        }
    }

    public void joinQQGroup(String key) {
        Intent intent = new Intent ();
        intent.setData (Uri.parse ("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        try {
            startActivity (intent);
        } catch (Exception e) {
            ToastUtil.showShort ("您还没有安装QQ，请先安装软件");
        }
    }

    private void showAboutContent() {
        SpannableStringBuilder spannable = new SpannableStringBuilder ("网站内容" + "\r\n" + "\r\n" + "   本网站每天新增20~30篇优质文章，并加入到现有分类中，" +
                "力求整理出一份优质而又详尽的知识体系，闲暇时间不妨上来学习下知识；" +
                "除此以外，并为大家提供平时开发过程中常用的工具以及常用的网址导航。" + "\r\n\r\n" + "当然这只是我们目前的功能，" +
                "未来我们将提供更多更加便捷的功能…" + "\r\n\r\n" +
                "如果您有任何好的建议：" + "\r\n\r\n\r\n" +
                "     关于网站排版" + "\r\n" + "     关于新增常用网址以及工具" + "\r\n" + "     未来你希望增加的功能等" + "\n\n" + "可以在https://github.com/hongyangAndroid/xueandroid项目中以issue的形式提出，" +
                "我将及时跟进。" + "\r\n\r\n" + "如果您希望长期关注本站，可以加入我们的QQ群：69594756" + "\r\n\r\n" + "源码位置" + "\r\n\r\n" + "本软件开源，" +
                "如果你发现任何错误，不要犹豫，马上点击GitHub，在上面发起issue或者提交pull request。");
        mAboutContent.setText (Html.fromHtml (getString (R.string.about_content)));
        mAboutContent.setMovementMethod (LinkMovementMethod.getInstance ());

        //设置字体的部分大小
        spannable.setSpan (new AbsoluteSizeSpan (55), 0, 4, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannable.setSpan (new AbsoluteSizeSpan (55), 319, 323, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //网站
        //设置字体的点击事件
        spannable.setSpan (new TextClick (), 213, 258
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置字体的颜色
        //网站
        spannable.setSpan (new ForegroundColorSpan (Color.RED), 213, 258
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        //设置字体的颜色

        spannable.setSpan (new ForegroundColorSpan (getResources ().getColor (R.color.fab_bg)), 22, 213
                , Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
        //设置字体的颜色

        //QQ群
        spannable.setSpan (new ForegroundColorSpan (Color.BLUE), 307, 317
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        //设置字体的点击事件
        spannable.setSpan (new TextClick_two (), 307, 315
                , Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mAboutContent.setText (spannable);

        try {
            String versionStr = getString (R.string.awesome_wan_android)
                    + " V" + getPackageManager ().getPackageInfo (getPackageName (), 0).versionName;
            mAboutVersion.setText (versionStr);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace ();
        }
    }


    /**
     * Update appbar theme用来作反馈变色
     */
    private void updateTheme() {
        if (mThemeListener == null) {
            mThemeListener = new View.OnClickListener () {
                int index = 0;
                int[] ids = new int[]{
                        android.R.color.holo_green_light,
                        android.R.color.holo_red_light,
                        android.R.color.holo_orange_light,
                        android.R.color.holo_blue_bright,
                        android.R.color.holo_purple,
                };

                @Override
                public void onClick(View v) {
                    int color = ContextCompat.getColor (getApplication (), ids[index % ids.length]);
                    mAboutUsRefreshLayout.setPrimaryColors (color);
                    mAboutUsFab.setBackgroundColor (color);
                    mAboutUsFab.setBackgroundTintList (ColorStateList.valueOf (color));
                    mAboutUsToolbarLayout.setContentScrimColor (color);
                    index++;
                }
            };
        }
        mThemeListener.onClick (null);
    }
}
