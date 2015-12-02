package com.jph.xibaibai.utils;

import android.os.Environment;

import com.jph.xibaibai.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jph on 2015/8/22.
 */
public interface Constants {

    String URL_PREFIX_FILE = com.jph.xibaibai.model.utils.Constants.BASE_URL + "/";

    String URL_WEB_BASE_PRODUCT = com.jph.xibaibai.model.utils.Constants.BASE_URL + "/Weixin/Diy/index?p_id=";

    String PATH_BASE = Environment.getExternalStorageDirectory() + File.separator + "XiBaiBai";

    String PATH_VOICE = PATH_BASE + File.separator + "Voice";

    String PATH_PIC = PATH_BASE + File.separator + "Photo";

    String PATH_CACHE = PATH_PIC + File.separator + "Cache";

    String UPDATEA_PATH = PATH_BASE + File.separator + "Xibaibai.apk";

    String PATH_CRASH = PATH_BASE + File.separator + "CrashLog";

    int[] REFRESH_COLORS = {R.color.txt_orange};

    int LOCATION_SCAN_SPAN = 20000;

    List<boolean[]> beautyCheckList = new ArrayList<>();
    /**
     * apk下载地址
     */
    public static String DOWN_APK_PATH = "http://www.ysapp.cn"
            + "/esunIndustry/Public/file/SNSHW.apk";

    public static String ROOT_PATH = Environment.getExternalStorageDirectory()
            .getAbsolutePath();
    /**
     * 更新apk的存放位置
     */
    public static String APK_PATH = ROOT_PATH + "/xbb/apk/xbb.apk";
}
