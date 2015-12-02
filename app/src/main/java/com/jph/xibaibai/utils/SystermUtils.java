package com.jph.xibaibai.utils;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

import com.jph.xibaibai.ui.activity.ApointmentTimeActivity;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Eric on 2015/11/9.
 * 工具类
 */
public class SystermUtils {
    /**
     * 重新估算ListView的高度
     *
     * @param listview
     */
    public static void setListViewHeight(ListView listview) {
        int totalHeight = 0;
        ListAdapter adapter = listview.getAdapter();
        if (null != adapter) {
            for (int i = 0; i < adapter.getCount(); i++) {
                View listItem = adapter.getView(i, null, listview);
                if (null != listItem) {
                    listItem.measure(0, 0);//  注意listview子项必须为LinearLayout才能调用该方法
                    totalHeight += listItem.getMeasuredHeight();
                }
            }
            ViewGroup.LayoutParams params = listview.getLayoutParams();
            params.height = totalHeight
                    + (listview.getDividerHeight() * (listview.getCount() - 1));
            listview.setLayoutParams(params);
        }
    }

    public static String getCarTypes(int type) {
        switch (type) {
            case 1:
                return "轿车";
            case 2:
                return "SUV";
            case 3:
                return "MVP";
        }
        return "";
    }

    /**
     * 根据时间戳返回Calendar对象
     *
     * @param time
     * @return
     */
    public static Calendar getCalendar(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Long timeNum = new Long(time);
        String d = format.format(timeNum);
        try {
            Date date = format.parse(d);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据年月日时分返回时间戳
     *
     * @param time
     * @return
     */
    public static long getTimeScope(String time) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    /**
     * 根据年月日时分返回时间戳
     *
     * @param time
     * @return
     */
    public static long getTimeScopeTwo(String time) {
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (date == null) {
            return 0;
        }
        return date.getTime();
    }

    /**
     * 判断字符串是否是合法的手机号
     *
     * @param telStr
     * @return
     */
    public static boolean isTrueTel(String telStr) {
        Pattern p = Pattern.compile("^[1][34578]\\d{9}$");
        if (p != null) {
            Matcher matcher = p.matcher(telStr);
            if (matcher.find()) {
                return true;
            }
        }
        return false;
    }

    public static double getTwonum(double origitalNum){
        DecimalFormat df = new DecimalFormat("#.00");
        return Double.parseDouble(df.format(origitalNum));
    }
}
