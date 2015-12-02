package com.jph.xibaibai.utils;

/**
 * Created by jph on 2015/9/9.
 */
public class OrderUtil {

    public static String getStatusName(int status) {
        switch (status) {
            case 0:
                return "未付款";
            case 1:
                return "派单中";
            case 2:
                return "已派单";
            case 3:
                return "在路上";
            case 4:
                return "进行中";
            case 5:
                return "未评价";
            case 6:
                return "已评价";
            case 7:
                return "已取消";
        }
        return null;
    }
}
