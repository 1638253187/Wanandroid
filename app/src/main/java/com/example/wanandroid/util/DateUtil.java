package com.example.wanandroid.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**

 * 时间工具类

 */

public class DateUtil {
    public static String clanderTodatetime(Calendar calendar, String style) {
        SimpleDateFormat formatter = new SimpleDateFormat(style);
        return formatter.format(calendar.getTime());
    }

}