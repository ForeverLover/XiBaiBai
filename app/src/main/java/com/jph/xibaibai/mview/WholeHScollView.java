package com.jph.xibaibai.mview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * 必须完整滑动
 * 
 * @author jph
 * 
 */
public class WholeHScollView extends HorizontalScrollView {

//	private int pageItemCount = 1;// 内容个数
	private int itemWidth = 0;

	public WholeHScollView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public WholeHScollView(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public WholeHScollView(Context context) {
		super(context);
	}

	public int getItemWidth() {
		return itemWidth;
	}

	public void setItemWidth(int itemWidth) {
		this.itemWidth = itemWidth;
	}
//	public int getPageItemCount() {
//		return pageItemCount;
//	}
//
//	public void setPageItemCount(int pageItemCount) {
//		this.pageItemCount = pageItemCount;
//	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		if (ev.getAction() == MotionEvent.ACTION_UP) {
			int remainder = getScrollX() % itemWidth;
			if (remainder != 0 && getScrollX() != getWidth()) {
				int scrollX = 0;
				if (remainder < itemWidth / 2)
					scrollX = itemWidth * (getScrollX() / itemWidth);
				else
					scrollX = itemWidth * (getScrollX() / itemWidth + 1);

				post(new RScroll(scrollX));
			}

			return true;
		}

		return super.onTouchEvent(ev);
	}

	/**
	 * 滑动了几个
	 * 
	 * @param itemWidth
	 * @return
	 */
	public int getScrollItemCount(int itemWidth) {
		return getScrollX() / itemWidth
				+ (this.getScrollX() % itemWidth > 0 ? 1 : 0);
	}

	private class RScroll implements Runnable {

		private int scrollX;

		public RScroll(int scrollX) {
			super();
			this.scrollX = scrollX;
		}

		@Override
		public void run() {
			smoothScrollTo(scrollX, 0);
		}

	}

}
