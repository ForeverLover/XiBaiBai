package com.jph.xibaibai.utils.sp;

import android.content.Context;

/**
 * Created by ypuse on 2015/7/14.
 */
public class SPUserInfo extends SPHelper {

    private static SPUserInfo sInstance;

    private static final String SP_NAME = "SP_UserInfo";
    public static final String KEY_USERID = "userId",
            KEY_USERINFO = "userInfo",
            KEY_ALL_ADDRESS = "allAddress",
            KEY_CITY = "city";

    public static SPUserInfo getsInstance(Context context) {
        syncInit(context);
        return sInstance;
    }

    private static synchronized void syncInit(Context context) {
        if (sInstance == null) {
            sInstance = new SPUserInfo(context);
        }
    }

    public boolean isLogined() {
        int userId = getSPInt(KEY_USERID);
        return userId > 0;
    }

    public void logout() {
        setSP(KEY_USERID, 0);
    }

    private SPUserInfo(Context context) {
        this(context, SP_NAME);
    }

    private SPUserInfo(Context context, String sName) {
        super(context, sName);
    }
}
