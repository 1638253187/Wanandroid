package com.example.windqq.app;

import android.annotation.SuppressLint;
import android.content.Context;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;

import com.example.windqq.bean.Constants;
import com.orhanobut.logger.Logger;

/**
 * Created by Administrator on 2018/4/3 0003.
 */
public class SysUtils {

    private static SysUtils instance;
    private static Context context;
    private static TelephonyManager mTelephonyManager;
    private static String dev;
    private static String gs;

    //适配AndroidQ解决IMEI问题
    public static String phone_sys = "";

    public static SysUtils getInstance() {
        if (instance == null) {
            synchronized (SysUtils.class) {
                if (instance == null) {
                    instance = new SysUtils(QQApp.getInstances());
                }
            }
        }
        return instance;
    }

    private SysUtils(Context mContext) {
        this.context = mContext;
        mTelephonyManager = (TelephonyManager) mContext
                .getSystemService(Context.TELEPHONY_SERVICE);
    }

    private static TelephonyManager getTelephonyManager(Context context) {
        // 获取telephony系统服务，用于取得SIM卡和网络相关信息
        if (mTelephonyManager == null) {
            mTelephonyManager = (TelephonyManager) context
                    .getSystemService(Context.TELEPHONY_SERVICE);
        }
        return mTelephonyManager;
    }


    /**
     * 唯一的设备ID： GSM手机的 IMEI 和 CDMA手机的 MEID. Return null if device ID is not
     * 取得手机IMEI
     * available.
     */
    @SuppressLint("MissingPermission")
    public static String getDeviceId() {
        String mDeviceId = getTelephonyManager(context).getDeviceId();// String
        if (mDeviceId == null) {
            mDeviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            //Android Q写死有效
            dev = "869247033570091";
            gs = "865452042815156";
        }
        if (TextUtils.isEmpty(mDeviceId)) {
            mDeviceId = Constants.DEFAULT_DEVICE_ID;
        }
        return phone_sys;
    }


    public int getSDKCode() {
        int code = 0;
        try {
            code = Integer.parseInt(android.os.Build.VERSION.SDK);
        } catch (Exception e) {
            Logger.e(e.getMessage());
            code = 0;
        }
        return code;
    }
}