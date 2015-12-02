package com.jph.xibaibai.model.utils;

import android.util.Log;

/**
 * 统一控制日志输出
 * 
 * @author jph
 * 
 */
public class MLog {

	private static boolean logable = true;

	public static boolean isLogable() {
		return logable;
	}

	public static void setLogable(boolean logable) {
		MLog.logable = logable;
	}

	public static void i(String tag, String msg) {
		if (isLogable())
			Log.i(tag, msg);
	}

	public static void e(String tag, String msg) {
		if (isLogable())
			Log.e(tag, msg);
	}

	public static void d(String tag, String msg) {
		if (isLogable())
			Log.d(tag, msg);
	}
}
