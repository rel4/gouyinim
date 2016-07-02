package com.gouyin.im.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jb on 2016/6/17.
 */
public class TimeUtils {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    private static final String YEAR_MONTH_DAY = "yyyyMMdd";
    private static final String HOUR_MINUTE_SECOND = "HHmmss";
    private static final String HOUR = "HH";

    /**
     * 格式当前时间
     *
     * @return
     */
    public static String formatCurrentDate() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);
        String format = simpleDateFormat.format(getDate());
        return format;
    }

    /**
     * 格式化当前的年月日
     *
     * @return
     */
    public static String getCurrentYearMonthDay() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(YEAR_MONTH_DAY);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前的小时
     *
     * @return
     */
    public static String getCurrentHour() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HOUR);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前的时分秒
     *
     * @return
     */
    public static String getCurrentHourMinuteSecond() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(HOUR_MINUTE_SECOND);
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取当前的时间戳
     *
     * @return
     */
    public static long getCurrentTimestamp() {
        return System.currentTimeMillis();
    }

    private static Date getDate() {
        return new Date();
    }

    public static String format(long time) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(DATE_FORMAT);

        return simpleDateFormat.format(new Date(time));
    }
}
