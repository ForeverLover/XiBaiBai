package com.jph.xibaibai.mview;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class ScrollAbleViewPager extends ViewPager {

	private boolean scrollAble = true;// 是否允许滑动

	public ScrollAbleViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ScrollAbleViewPager(Context context) {
		super(context);
	}

	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (scrollAble)
			return super.onTouchEvent(event);

		return false;
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent event) {
		if (scrollAble)
			return super.onInterceptTouchEvent(event);

		return false;
	}

	public boolean isScrollAble() {
		return scrollAble;
	}

	public void setScrollAble(boolean scrollAble) {
		this.scrollAble = scrollAble;
	}

}
