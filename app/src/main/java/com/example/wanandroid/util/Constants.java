package com.example.wanandroid.util;

import android.os.Environment;

import com.example.wanandroid.base.BaseApp;

import java.io.File;

public interface Constants {
    boolean isDebug = true;

    String PATH_SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath() +
            File.separator + "code" + File.separator + "GeekNews";

    String FILE_PROVIDER_AUTHORITY = "com.baidu.geek.fileprovider";

    //网络缓存的地址
    String PATH_DATA = BaseApp.getApp().getCacheDir().getAbsolutePath() +
            File.separator + "data";

    String PATH_CACHE = PATH_DATA + "/NetCache";

    String MODE = "day_night_mode";
    String CURRENT_FRAG_TYPE = "current_frag_type";

}
