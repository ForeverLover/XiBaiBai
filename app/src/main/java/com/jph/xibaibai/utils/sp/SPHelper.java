package com.jph.xibaibai.utils.sp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/** SPference操作工具类 */

public class SPHelper {
	private Context context;
	private String sName;

	public SPHelper(Context context, String sName) {
		this.context = context;
		this.sName = sName;
	}

	public String getSP(String sKey) {
		String str;
		SharedPreferences s = context.getSharedPreferences(sName, 0);
		str = s.getString(sKey, "");
		return str;
	}

	public float getSPFloat(String sKey) {
		float str;
		SharedPreferences s = context.getSharedPreferences(sName, 0);
		str = s.getFloat(sKey, 0);
		return str;
	}

	public boolean getSPBoolean(String key){
		SharedPreferences s = context.getSharedPreferences(sName, 0);
		return s.getBoolean(key, false);
	}

	public boolean getSPBoolean(String key,boolean defaultI){
		SharedPreferences s = context.getSharedPreferences(sName, 0);
		return s.getBoolean(key, defaultI);
	}

	public int getSPInt(String sKey) {
		int str;
		SharedPreferences s = context.getSharedPreferences(sName, 0);
		str = s.getInt(sKey, -1);
		return str;
	}

	public void setSP(String sKey, String sValue) {
		SharedPreferences s = context.getSharedPreferences(sName, 0);
		Editor editor = s.edit();
		editor.putString(sKey, sValue);
		editor.commit();
	}

	public void setSP(String sKey, boolean sValue) {
		SharedPreferences s = context.getSharedPreferences(sName, 0);
		Editor editor = s.edit();
		editor.putBoolean(sKey, sValue);
		editor.commit();
	}

	public void setSP(String sKey, float sValue) {
		SharedPreferences s = context.getSharedPreferences(sName, 0);
		Editor editor = s.edit();
		editor.putFloat(sKey, sValue);
		editor.commit();
	}

	public void setSP(String sKey, int sValue) {
		SharedPreferences s = context.getSharedPreferences(sName, 0);
		Editor editor = s.edit();
		editor.putInt(sKey, sValue);
		editor.commit();
	}

	public void setSP(String sKey, long sValue) {
		SharedPreferences s = context.getSharedPreferences(sName, 0);
		Editor editor = s.edit();
		editor.putLong(sKey, sValue);
		editor.commit();
	}

}
