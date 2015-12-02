package com.jph.xibaibai;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Administrator on 2015/7/29.
 */
public class XBBApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        SDKInitializer.initialize(this);

        // 异常处理，不需要处理时注释掉这两句即可！
//        CrashHandler crashHandler = CrashHandler.getInstance();
//        crashHandler.init(getApplicationContext());
    }
}
