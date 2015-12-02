package com.jph.xibaibai.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TimeUtil {

    /**
     * 得到固定格式字符串
     *
     * @param mill
     * @return
     */
    public static String getFormatStringByMill(long mill, String formatStr) {
        SimpleDateFormat format = new SimpleDateFormat(formatStr, Locale.CHINA);
        String date = format.format(new Date(mill*1000));
        return date;
    }

    public static String getMFormatStringByMill(long mill) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.CHINA);
        String date = format.format(new Date(mill * 1000));
        return date;
    }

    /**
     * 年龄
     */
    public static int getAge(String birthday) {

        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd",
                Locale.CHINA);
        Date date = new Date();
        try {
            date = format.parse(birthday);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        Calendar ageCalendar = Calendar.getInstance();
        ageCalendar.setTimeInMillis(date.getTime());
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.setTimeInMillis(System.currentTimeMillis());
        return nowCalendar.get(Calendar.YEAR) - ageCalendar.get(Calendar.YEAR);
    }

}
