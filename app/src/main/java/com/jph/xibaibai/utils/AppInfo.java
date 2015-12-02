package com.jph.xibaibai.utils;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * app信息类
 * 
 * @author jph
 * 
 */
public class AppInfo {

	Context context;
	PackageManager pm;

	public AppInfo(Context context) {
		this.context = context;
		pm = context.getPackageManager();
	}

	/***
	 * 通过报名获取程序名
	 * 
	 * @param packname
	 *            完整包名
	 * @return
	 */
	public String getAppName(String packname) {
		try {
			ApplicationInfo info = pm.getApplicationInfo(packname, 0);
			return info.loadLabel(pm).toString();
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取版本名
	 * @param packageName
	 * @return
	 */
	public String getVersionName(String packageName) {
		try {
			return pm.getPackageInfo(packageName, 0).versionName;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * 获取版本号
	 * 
	 * @param packageName
	 * @return
	 */
	public int getVersionCode(String packageName) {
		try {
			return pm.getPackageInfo(packageName, 0).versionCode;
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		return 0;
	}
}
