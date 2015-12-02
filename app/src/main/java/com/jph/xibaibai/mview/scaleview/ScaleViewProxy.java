package com.jph.xibaibai.mview.scaleview;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.View.MeasureSpec;

import com.jph.xibaibai.R;


/**
 * 固定比例ImageView代理类
 * 
 * @author jph
 * 
 */
public class ScaleViewProxy {

	private View v;

	public static final int MODEL_BY_WIDTH = 1;// 以width为标准
	public static final int MODEL_BY_HEIGHT = 2;// 以height为标准

	private int modelBy = 0;
	private float multiple = 1;

	public ScaleViewProxy(View v) {
		super();
		this.v = v;
	}

	public void init(Context context, AttributeSet attrs) {
		// 获得属性值
		TypedArray a = context.obtainStyledAttributes(attrs,
				R.styleable.ScaleView);
		modelBy = a.getInt(R.styleable.ScaleView_modelby, 0);// 模式
		multiple = a.getFloat(R.styleable.ScaleView_multiple, 1);// 倍数，默认一倍
		a.recycle();

	}

	public MeasureSize measureSize(int widthMeasureSpec, int heightMeasureSpec) {

		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int widthSize = MeasureSpec.getSize(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int heightSize = MeasureSpec.getSize(heightMeasureSpec);

		int width;
		int height;

		if (widthMode == MeasureSpec.EXACTLY) {
			width = widthSize;
		} else {
			int desired = v.getPaddingLeft() + v.getPaddingRight();
			width = desired;
			if (widthMode == MeasureSpec.AT_MOST) {
				width = Math.min(desired, widthSize);
			}
		}

		if (heightMode == MeasureSpec.EXACTLY) {
			height = heightSize;
		} else {
			// int rawWidth = width - getPaddingLeft() - getPaddingRight();
			int desired = (int) (v.getPaddingTop() + v.getPaddingBottom());

			height = desired;
			if (heightMode == MeasureSpec.AT_MOST) {
				height = Math.min(desired, heightSize);
			}
		}

		if (modelBy == MODEL_BY_WIDTH) {
			// 以width为准
			height = (int) (width * multiple);

		} else if (modelBy == MODEL_BY_HEIGHT) {
			// 以height为准
			width = (int) (height * multiple);
		}

		return new MeasureSize(width, height);

		// imageView.setMeasuredDimension(width, height);
	}

	public int getModelBy() {
		return modelBy;
	}

	public void setModelBy(int modelBy) {
		this.modelBy = modelBy;
		v.invalidate();
	}

	public float getMultiple() {
		return multiple;
	}

	public void setMultiple(float multiple) {
		this.multiple = multiple;
		v.invalidate();
	}

	public interface IScaleView {
		public int getModelBy();

		public void setModelBy(int modelBy);

		public float getMultiple();

		public void setMultiple(float multiple);
	}

	public class MeasureSize {
		private int width;
		private int height;

		private MeasureSize(int width, int height) {
			super();
			this.width = width;
			this.height = height;
		}

		public int getWidth() {
			return width;
		}

		public void setWidth(int width) {
			this.width = width;
		}

		public int getHeight() {
			return height;
		}

		public void setHeight(int height) {
			this.height = height;
		}

	}
}
