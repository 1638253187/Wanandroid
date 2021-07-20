package com.example.wanandroid.ui.fragment;


import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.ColorInt;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.wanandroid.R;
import com.example.wanandroid.base.BaseApp;
import com.example.wanandroid.ui.activity.MainActivity;
import com.example.wanandroid.util.Constants;
import com.example.wanandroid.util.ToastUtil;
import com.example.wanandroid.util.night.SpUtil;
import com.example.wanandroid.util.night.UIModeUtil;

/**
 * A simple {@link Fragment} subclass.
 */
public class SetiingFragment extends Fragment {


    private View view;
    /**
     * 护眼
     */
    private TextView mBtnHuyan;
    private Switch mSwItch;
    private WindowManager mWindowManager;
    private ImageView mIvHuyan;
    /**
     * 夜间
     */
    private TextView mTvDayNight;
    private Switch mStDayNight;

    public SetiingFragment() {
        // Required empty public constructor
    }

    //红蓝绿 三原色的初始值
    private int blue = 50;
    private int red = 20;
    private int green = 50;
    private int ld = 0;
    private int alapha = 100;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View inflate = inflater.inflate(R.layout.fragment_setiing, container, false);
        initView(inflate);
        initData();
//        permission ();
        return inflate;
    }


    private void initData() {

    }

//    public void permission() {
//        if (Build.VERSION.SDK_INT >= 23) {
//            if (!Settings.canDrawOverlays (getActivity ())) {
//                Intent intent = new Intent (Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
//                startActivity (intent);
//            }
//        }
//    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void initView(View inflate) {
        mBtnHuyan = (TextView) inflate.findViewById(R.id.btn_huyan);
        mSwItch = (Switch) inflate.findViewById(R.id.sw_itch);
        mIvHuyan = (ImageView) inflate.findViewById(R.id.iv_huyan);
        mTvDayNight = (TextView) inflate.findViewById(R.id.tv_DayNight);
        mStDayNight = (Switch) inflate.findViewById(R.id.st_DayNight);
        mWindowManager = (WindowManager) BaseApp.getContext().getSystemService(Context.WINDOW_SERVICE);
        final WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.type = WindowManager.LayoutParams.TYPE_SYSTEM_OVERLAY;
        final int flag = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL | WindowManager.LayoutParams.FLAG_FULLSCREEN |
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
        params.format = PixelFormat.TRANSLUCENT;
        WindowManager mWindowManager = (WindowManager) BaseApp.getContext().getSystemService(BaseApp.getContext().WINDOW_SERVICE);
        Display display = mWindowManager.getDefaultDisplay();
        Point p = new Point();
        display.getRealSize(p);
        params.width = p.x;
        params.height = p.y;
        mSwItch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked == true) {
                    red = 190;
                    blue = 199;
                    green = 237;
                    mIvHuyan.setBackgroundColor(Color.rgb(190, 237, 199));
                } else {
                    red = 255;
                    green = 255;
                    blue = 255;
                    mIvHuyan.setBackgroundColor(Color.rgb(255, 255, 255));
                }
            }
        });
        if (BaseApp.mMode == AppCompatDelegate.MODE_NIGHT_NO) {
            mStDayNight.setChecked(false);
        } else {
            mStDayNight.setChecked(true);
        }

        initLinsenter();
    }

    private void initLinsenter() {
        if (BaseApp.mMode == 2) {
            mStDayNight.setChecked(true);
        } else {
            mStDayNight.setChecked(false);
        }
        mStDayNight.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                //调用工具类,工具类会判断当前模式并切换
                //因为切换日夜间模式的时候,横竖屏切换的时候,Activity会重建
                //这个控件
                Log.d("tag", "onCheckedChanged: " + isChecked);
                //判断是否是用户点击,不是就不切模式
                if (buttonView.isPressed()) {
                    ((MainActivity) getActivity()).mType = "switch_type";
                    SpUtil.setParam(Constants.CURRENT_FRAG_TYPE, MainActivity.TYPE_SETTTINGS);
                    UIModeUtil.changeModeUI((AppCompatActivity) getActivity());
                    //保存当前Fragment的类型,切换模式重建之后切换到当前的Fragment里
                }
            }
        });
    }

    /**
     * 过滤蓝光
     *
     * @param blueFilterPercent 蓝光过滤比例[10-80]
     */
    public static @ColorInt
    int getColor(int blueFilterPercent) {
        int realFilter = blueFilterPercent;
        if (realFilter < 10) {
            realFilter = 10;
        } else if (realFilter > 80) {
            realFilter = 80;
        }
        int a = (int) (realFilter / 80f * 180);
        int r = (int) (200 - (realFilter / 80f) * 190);
        int g = (int) (180 - (realFilter / 80f) * 170);
        int b = (int) (60 - realFilter / 80f * 60);
        return Color.argb(a, r, g, b);
    }

    public void changeAppBrightness(int brightness) {   //改变系统屏幕亮度
        WindowManager.LayoutParams lp = getActivity().getWindow().getAttributes();

        lp.screenBrightness = Float.valueOf(brightness) * (0.5f / 255f);

        getActivity().getWindow().setAttributes(lp);
    }


}
