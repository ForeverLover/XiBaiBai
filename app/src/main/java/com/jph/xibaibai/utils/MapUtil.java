package com.jph.xibaibai.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.View;
import android.widget.ZoomControls;

import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

public class MapUtil {

	// 地图包名
	public static final String PACKAGENAME_MAP_GOOGLE = "com.google.android.apps.maps";
	public static final String PACKAGENAME_MAP_GAODE = "com.autonavi.minimap";
	public static final String PACKAGENAME_MAP_BAIDU = "com.baidu.BaiduMap";

	/**
	 * 隐藏缩放空控件
	 */
	public static void hideZoomController(MapView mapView) {
		int childCount = mapView.getChildCount();

		View zoom = null;

		for (int i = 0; i < childCount; i++) {

			View child = mapView.getChildAt(i);

			if (child instanceof ZoomControls) {

				zoom = child;

				break;

			}
		}
		zoom.setVisibility(View.GONE);
	}

	/**
	 * 获取手机上已安装的地图
	 * 
	 * @param context
	 * @return
	 */
	public static List<String> getInstalledMap(Context context) {
		List<String> maps = new ArrayList<String>();
		maps.add(PACKAGENAME_MAP_BAIDU);
		maps.add(PACKAGENAME_MAP_GAODE);
		maps.add(PACKAGENAME_MAP_GOOGLE);

		// 获取packagemanager
		final PackageManager packageManager = context.getPackageManager();
		// 获取所有已安装程序的包信息
		List<PackageInfo> packageInfos = packageManager.getInstalledPackages(0);

		// 用于存储所有已安装程序的包名
		List<String> packageList = new ArrayList<String>();
		if (packageInfos != null) {
			for (int i = 0; i < packageInfos.size(); i++) {
				String packName = packageInfos.get(i).packageName;
				if (maps.contains(packName)) {
					packageList.add(packName);
				}
			}
		}
		return packageList;
	}

	public static Intent createIntentForGoogle(LatLng start, LatLng end,
			String endAddress) {
		// 从哪到哪的路线
		Intent i = new Intent(Intent.ACTION_VIEW,
				Uri.parse("http://ditu.google.cn/maps?f=d&source=s_d&saddr="
						+ start.latitude + "," + start.longitude + "&daddr="
						+ end.latitude + "," + end.longitude + "&hl=zh"));
		// 如果强制使用googlemap地图客户端打开，就加下面两句
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
				& Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS);
		i.setClassName("com.google.android.apps.maps",
				"com.google.android.maps.MapsActivity");
		return i;
	}

	public static Intent createIntentForBaiDu(LatLng start, LatLng end,
			String endAddress) {
		Intent intent = null;
		try {
			intent = Intent
					.getIntent("intent://map/direction?origin=latlng:"
							+ start.latitude
							+ ","
							+ end.longitude
							+ "|name:当前位置&destination="
							+ endAddress
							+ "&mode=driving®ion=成都&src=yourCompanyName|yourAppName#Intent;scheme=bdapp;package=com.baidu.BaiduMap;end");
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return intent;
	}

	public static Intent createIntentForGaode(LatLng start, LatLng end,
			String endAddress) {
		Intent intent = new Intent(
				"android.intent.action.VIEW",
				Uri
						.parse("androidamap://route?sourceApplication=softname&slat="
								+ start.latitude
								+ "&slon="
								+ start.longitude
								+ "&sname="
								+ "当前位置"
								+ "&dlat="
								+ end.latitude
								+ "&dlon="
								+ end.longitude
								+ "&dname="
								+ endAddress + "&dev=0&m=0&t=2&showType=1"));
		intent.setPackage("com.autonavi.minimap");
		return intent;
	}
}
