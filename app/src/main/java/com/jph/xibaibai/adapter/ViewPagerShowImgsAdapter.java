package com.jph.xibaibai.adapter;

import android.app.Activity;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;

import com.jph.xibaibai.utils.MImageLoader;

import uk.co.senab.photoview.PhotoView;
import uk.co.senab.photoview.PhotoViewAttacher;


/**
 * 展示多张图片可缩放的Viewpager的适配器
 *
 * @author Jiang
 *
 */
public class ViewPagerShowImgsAdapter extends PagerAdapter {

	private Activity activity;
	private String[] imgUrls;

	public ViewPagerShowImgsAdapter(Activity activity, String[] imgUrls) {
		this.activity = activity;
		this.imgUrls = imgUrls;

	}

	@Override
	public int getCount() {
		return imgUrls.length;
	}

	@Override
	public View instantiateItem(ViewGroup container, int position) {
		PhotoView photoView = new PhotoView(container.getContext());
		// photoView.setImageResource(imgUrls[position]);
		MImageLoader.getInstance(activity).displayImageM(imgUrls[position],
				photoView);

		//设置photoView轻触事件-结束当前Activity
		photoView.setOnPhotoTapListener(new PhotoViewAttacher.OnPhotoTapListener() {

			@Override
			public void onPhotoTap(View arg0, float arg1, float arg2) {
				activity.finish();
			}
		});

		// Now just add PhotoView to ViewPager and return it
		container.addView(photoView, LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT);

		return photoView;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view == object;
	}

}
