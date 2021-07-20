package com.example.windqq.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.windqq.Hollo;
import com.example.windqq.R;
import com.example.windqq.activity.fragment.FowFragment;
import com.example.windqq.activity.fragment.FoodFragment;
import com.example.windqq.activity.fragment.VideoFragment;
import com.example.windqq.activity.fragment.LXRFragment;
import com.example.windqq.service.MqttService;
import com.example.windqq.util.HideIMEUtil;
import com.example.windqq.util.ScrollableViewPager;
import com.gyf.barlibrary.BarHide;
import com.gyf.barlibrary.ImmersionBar;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private Toolbar mToolbar;
    private ScrollableViewPager mVp;
    private TabLayout mTab;
    private NavigationView mNavi;
    private DrawerLayout mDrawer;
    private Toast toast;
    private ImageView imageView;
    private TextView tv_user;
    private ImageView zdh;
    private AnimationDrawable drawable;
    private ImageView iv_wifi;
    private AnimationDrawable drawable1;
    private CountDownTimer start;
    private CountDownTimer toast1;
    private EditText et_qianming;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        ImmersionBar.with(this)
                .hideBar(BarHide.FLAG_HIDE_NAVIGATION_BAR)
                .fullScreen(true)
                //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
                .transparentNavigationBar()
                .init();
        HideIMEUtil.wrap(this);
        startAllService();
        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_BATTERY_CHANGED);
        this.registerReceiver(broadcastReceiver, filter);
        setAnimation();
    }

    private void startAllService() {
        startService(new Intent(MainActivity.this, MqttService.class));
    }

    //自动收起导航栏
    public BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if (action.equals(Intent.ACTION_BATTERY_CHANGED)) {
                int status = intent.getIntExtra("status", BatteryManager.BATTERY_STATUS_UNKNOWN);
                if (status == BatteryManager.BATTERY_STATUS_CHARGING) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
//                            MediaPlayer mPlayer = MediaPlayer.create (MainActivity.this, R.raw.qto);
//                            mPlayer.start ();
                            iv_wifi.setImageResource(R.drawable.anims_two);
                            drawable1 = (AnimationDrawable) iv_wifi.getDrawable();
                            drawable1.start();
                        }
                    });

                } else if (status != BatteryManager.BATTERY_STATUS_CHARGING) {
                    //DoSomeThing

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (drawable1 != null) {
                                drawable1.stop();
                            }
                        }
                    });
                }
            }
        }
    };

    private void setAnimation() {
        zdh.setImageResource(R.drawable.anims);
        drawable = (AnimationDrawable) zdh.getDrawable();
        drawable.start();
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        mVp = (ScrollableViewPager) findViewById(R.id.vp);
        mTab = (TabLayout) findViewById(R.id.tab);
        mNavi = (NavigationView) findViewById(R.id.navi);
        mDrawer = (DrawerLayout) findViewById(R.id.drawer);

        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new FoodFragment());
        fragments.add(new LXRFragment());
        fragments.add(new VideoFragment());
        fragments.add(new FowFragment());
        mVp.setOffscreenPageLimit(0);
        mVp.setScanScroll(false);
        mVp.setNoScroll(false);
        mVp.setPageTransformer(true, new CustomPageTransformer());
        mVp.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
        });
        final TextView tv_text = mToolbar.findViewById(R.id.tv_text);
        iv_wifi = mToolbar.findViewById(R.id.iv_wifi);
        mTab.setupWithViewPager(mVp);
        mTab.getTabAt(0).setText("消息").setIcon(R.drawable.select);
        mTab.getTabAt(1).setText("联系人").setIcon(R.drawable.select_two);
        mTab.getTabAt(2).setText("看点").setIcon(R.drawable.select_three);
        mTab.getTabAt(3).setText("动态").setIcon(R.drawable.select_fow);
        setSupportActionBar(mToolbar);
        mVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                switch (i) {
                    case 0:
                        ImmersionBar.with(MainActivity.this)
                                .transparentStatusBar()
                                .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
                                .statusBarDarkFont(false)
                                .init();
                        tv_text.setText(" 消息 ");
                        mToolbar.setVisibility(View.VISIBLE);
                        iv_wifi.setTranslationX((float) 5.2);
                        mNavi.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        ImmersionBar.with(MainActivity.this)
                                .transparentStatusBar()
                                .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
                                .statusBarDarkFont(false)
                                .init();
                        tv_text.setText("联系人");
                        mNavi.setVisibility(View.VISIBLE);
                        iv_wifi.setTranslationX((float) -11);
                        mToolbar.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        ImmersionBar.with(MainActivity.this)
//                                .statusBarColor(R.color.colorWhite)
                                .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
                                .statusBarDarkFont(true)
                                .init();

                        iv_wifi.setTranslationX((float) 4.2);
                        mToolbar.setVisibility(View.GONE);
                        mNavi.setVisibility(View.GONE);
                        break;
                    case 3:
                        ImmersionBar.with(MainActivity.this)
                                .transparentStatusBar()
                                .transparentNavigationBar()  //透明导航栏，不写默认黑色(设置此方法，fullScreen()方法自动为true)
                                .statusBarDarkFont(false)
                                .init();
                        tv_text.setText(" 动态 ");
                        mNavi.setVisibility(View.VISIBLE);
                        mToolbar.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        initListener();
//        //这是不带Home旋转开关按钮
//        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle (this, mDrawer,
//                R.string.nv_open, R.string.nv_close);
//        mDrawer.addDrawerListener (toggle);
//        toggle.syncState ();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawer, mToolbar, R.string.nv_open, R.string.nv_close) {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                //获取mDrawerLayout中的第一个子布局，也就是布局中的RelativeLayout
                //获取抽屉的view
                View mContent = mDrawer.getChildAt(0);
                float scale = 1 - slideOffset;
                float endScale = 0.8f + scale * 0.2f;
                float startScale = 1 - 0.3f * scale;
                //设置左边菜单滑动后的占据屏幕大小
                drawerView.setScaleX(startScale);
                drawerView.setScaleY(startScale);
                //设置菜单透明度
                drawerView.setAlpha(0.6f + 0.4f * (1 - scale));
                //设置内容界面水平和垂直方向偏转量
                //在滑动时内容界面的宽度为 屏幕宽度减去菜单界面所占宽度
                mContent.setTranslationX(drawerView.getMeasuredWidth() * (1 - scale));
                //设置内容界面操作无效（比如有button就会点击无效）
                mContent.invalidate();
                //设置右边菜单滑动后的占据屏幕大小
                mContent.setScaleX(endScale);
                mContent.setScaleY(endScale);
            }
        };
        mDrawer.addDrawerListener(toggle);
//        toggle.syncState ();

    }

    private void checkPermisson() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(MainActivity.this, "相机授权成功", Toast.LENGTH_SHORT).show();
            takePhoto();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 100);
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            takePhoto();
        } else {
            Toast.makeText(MainActivity.this, "授权失败", Toast.LENGTH_SHORT).show();
        }
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //请求码我们可以设置一个本类的静态的常量，我在这里就用100来表示了
        startActivityForResult(intent, 100);
    }

    private void initListener() {
        ImageView iv_header_tu = mToolbar.findViewById(R.id.iv_header_tu);
        iv_header_tu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.openDrawer(Gravity.LEFT);
            }
        });
        View headerView = mNavi.getHeaderView(0);
        ImageView iv_header = headerView.findViewById(R.id.iv_hedaer);
        ImageView iv_crea = headerView.findViewById(R.id.iv_crea);
        ImageView iv_user = headerView.findViewById(R.id.iv_user);
        ImageView iv_erwei = headerView.findViewById(R.id.iv_erwei);
        et_qianming = headerView.findViewById(R.id.et_qianming);
        zdh = headerView.findViewById(R.id.zdh);
        String s = et_qianming.getText().toString();
        et_qianming.setSelection(et_qianming.length());
        tv_user = headerView.findViewById(R.id.tv_user);
        tv_user.setOnClickListener(this);

        iv_erwei.setOnClickListener(this);
        iv_user.setOnClickListener(this);
        et_qianming.setOnClickListener(this);
        iv_crea.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkPermisson();
            }
        });
        iv_header.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDrawer.closeDrawer(Gravity.LEFT);
            }
        });
        mNavi.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        menu.add(1, 11, 111, "扫一扫");
        menu.add(1, 12, 111, "退出登录");
        return super.onCreateOptionsMenu(menu);
    }

    @SuppressLint("WrongConstant")
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case 11:
                final Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.tencent.mm", "com.tencent.mm.ui.LauncherUI"));
                intent.putExtra("LauncherUI.From.Scaner.Shortcut", true);
                intent.setFlags(335544320);
                intent.setAction("android.intent.action.VIEW");
                startActivity(intent);
                toast = Toast.makeText(getApplicationContext(), "123", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER, 0, 0);
                imageView = new ImageView(MainActivity.this);
                imageView.setImageResource(R.mipmap.wecat);
                toast.setView(imageView);
                toast.show();
                break;
            case 12:
                SharedPreferences sp = getSharedPreferences("mima", 0);
                SharedPreferences.Editor edit = sp.edit();
                edit.clear().commit();
                toast1 = new CountDownTimer(500, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        Toast.makeText(MainActivity.this, "退出登录", Toast.LENGTH_SHORT).show();

                    }
                }.start();

                this.start = new CountDownTimer(1000, 100) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        finish();
                        Intent intent1 = new Intent(MainActivity.this, Hollo.class);
                        startActivity(intent1);
                    }
                }.start();
                break;
        }
        return super.

                onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_user:
                String url = "mqqapi://card/show_pslcard?src_type=internal&version=1&uin=" + 1638253187
                        + "&card_type=person&source=qrcode";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));

                break;
            case R.id.iv_erwei:
                startActivity(new Intent(MainActivity.this, ErWei.class));
                break;
            case R.id.tv_user:
                String url1 = "mqqapi://card/show_pslcard?src_type=internal&version=1&uin=" + 1638253187
                        + "&card_type=person&source=qrcode";
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url1)));
                break;
            case R.id.et_qianming:

                break;
        }
    }

    public class CustomPageTransformer implements ViewPager.PageTransformer {
        //最小状态时，Size缩小为90%
        private static final float MIN_SCALE = 0.9F;

        @RequiresApi(api = Build.VERSION_CODES.HONEYCOMB)
        @Override
        public void transformPage(View view, float position) {
            //这里自定义动画
            float scale = Math.max(MIN_SCALE, 1 - Math.abs(position));
            if (position < -1.0f) {
                view.setScaleY(MIN_SCALE);
            } else if (position <= 0.0f) {
                view.setScaleY(scale);
            } else if (position <= 1.0f) {
                view.setScaleY(scale);
            } else {
                view.setScaleY(MIN_SCALE);
            }
        }

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.huiyuan:

                startActivity(new Intent(MainActivity.this, HuiYuan.class));
                break;
            case R.id.qianbao:
                startActivity(new Intent(MainActivity.this, QianBao.class));

                break;
            case R.id.zhuangban:
                startActivity(new Intent(MainActivity.this, ZhuangBan.class));

                break;
            case R.id.shouchang:
                startActivity(new Intent(MainActivity.this, ShouChang.class));
                break;
            case R.id.xiangce:
                startActivity(new Intent(MainActivity.this, XiangCe.class));

                break;
            case R.id.wenjian:
                startActivity(new Intent(MainActivity.this, WenJian.class));

                break;
            case R.id.liuliang:
                startActivity(new Intent(MainActivity.this, LiuLiang.class));

                break;


        }
        return false;
    }

    private long exitTime = 0;

    public void onBackPressed() {
        doubleBackQuit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //判断请求码是否是请求打开相机的那个请求码
        if (requestCode == 100 && data != null) {
            //获取照片数据
            Bitmap cameraPhoto = data.getParcelableExtra("data");
            Toast.makeText(this, "保存照片成功", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        start.cancel();
        toast1.cancel();
        unregisterReceiver(broadcastReceiver);
    }

    /**
     * 连续按两次返回键，退出应用
     */
    private void doubleBackQuit() {
        if (System.currentTimeMillis() - exitTime > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出", Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            finish();
        }
    }
}
