package com.example.wanandroid.base;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatDelegate;

import com.example.wanandroid.util.Constants;
import com.example.wanandroid.util.night.SpUtil;
import com.example.wanandroid.util.night.UIModeUtil;

import java.util.HashMap;
import java.util.Map;
public class BaseApp extends Application {
    private static Application mApp;
    private static Map<String, Object> mMap;
    private static SharedPreferences mSp;
    public static int mMode ;

    @Override
    public void onCreate() {
        super.onCreate();
        mApp = this;
        mMap = new HashMap<String, Object>();
        mSp = getSharedPreferences("config", MODE_PRIVATE);
//        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE,"5d5f33564ca357af41001006");
    }
    private void setDayNightMode() {
        //根据sp里面的模式设置对应的日夜间模式 y:2;no:1
        mMode = (int) SpUtil.getParam(Constants.MODE, AppCompatDelegate.MODE_NIGHT_NO);
        //Log.d(TAG, "setDayNightMode: "+mMode);
        UIModeUtil.setAppMode(mMode);
    }

    public static Application getApp() {
        return mApp;
    }

    public static Context getContext() {
        return mApp.getApplicationContext();
    }

    public static void setObj(String key, Object obj) {
        if (mMap != null)
            mMap.put(key, obj);
    }

    public static Object getObj(String key) {
        if (mMap != null && mMap.size() > 0)
            return mMap.get(key);
        return null;
    }

    public static void releaseMap() {
        if (mMap != null)
            mMap.clear();
        mMap = null;
    }

    public static BaseApp getInstance(){
        return (BaseApp) mApp;
    }
    public static SharedPreferences getmSp() {
        if (mSp != null)
            return mSp;
        return null;
    }
}
