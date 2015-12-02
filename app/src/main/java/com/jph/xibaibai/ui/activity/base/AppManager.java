package com.jph.xibaibai.ui.activity.base;

import java.util.LinkedList;
import java.util.List;

import android.app.Activity;

/**
 * 应用程序Activity管理类
 * 
 * @author jph
 */
public class AppManager {

	private List<Activity> activityList = new LinkedList<Activity>();

	private static AppManager instance;

	private AppManager() {
	}

	private static synchronized void syncInit() {
		if (instance == null) {
			instance = new AppManager();
		}
	}

	public static AppManager getInstance() {
		if (instance == null) {
			syncInit();
		}
		return instance;
	}

	// 添加Activity 到容器中
	public void addActivity(Activity activity) {
		activityList.add(activity);
	}

	public void removeActivity(Activity activity) {
		activityList.remove(activity);
	}

	public void exit() {
		finishAllActivity();
		System.exit(0);

	}

	public void finishAllActivity() {

		for (Activity activity : activityList) {
			activity.finish();
		}
	}

	public void finishAllActivityExcept(Activity eActivity) {

		for (Activity activity : activityList) {
			if (!activity.equals(eActivity))
				activity.finish();
		}
	}
}
