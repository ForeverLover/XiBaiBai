package com.jph.xibaibai.utils.sp;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Eric on 2015/11/20.
 */
public class SharePerferenceUtil {
    /*** 版本更新状态 ***/
    public static void saveState(Context con, boolean state) {
        SharedPreferences sp = con.getSharedPreferences("UpdateState",
                Context.MODE_PRIVATE);
        sp.edit().putBoolean("state", state).commit();
    }
    /**
     * 保存当前更新提示的时间
     */
    public static void setVersionTime(Context con,long time) {
        SharedPreferences sp = con.getSharedPreferences("VersionTime",
                Context.MODE_PRIVATE);
        sp.edit().putLong("time", time).commit();
    }

    /**
     * 更新清除信息
     * @param con
     */
    public static void clear(Context con) {
        SharedPreferences sp = con.getSharedPreferences("UpdateState",
                Context.MODE_PRIVATE);
        sp.edit().clear().commit();
    }
}
