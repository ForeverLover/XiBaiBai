package com.jph.xibaibai.mview.scaleview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 固定比例的ImageView
 * 
 * @author anonymous
 * 
 */
public class ScaleImageView extends ImageView implements ScaleViewProxy.IScaleView {

	private ScaleViewProxy scaleViewProxy;

	public ScaleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);

		scaleViewProxy = new ScaleViewProxy(this);
		scaleViewProxy.init(context, attrs);
	}

	public ScaleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		scaleViewProxy = new ScaleViewProxy(this);
		scaleViewProxy.init(context, attrs);
	}

	public ScaleImageView(Context context) {
		super(context);
		scaleViewProxy = new ScaleViewProxy(this);
		scaleViewProxy.init(context, null);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

		ScaleViewProxy.MeasureSize measureSize = scaleViewProxy.measureSize(widthMeasureSpec,
				heightMeasureSpec);

		setMeasuredDimension(measureSize.getWidth(), measureSize.getHeight());
	}

	@Override
	public int getModelBy() {
		return scaleViewProxy.getModelBy();
	}

	@Override
	public void setModelBy(int modelBy) {
		scaleViewProxy.setModelBy(modelBy);
	}

	@Override
	public float getMultiple() {
		return scaleViewProxy.getMultiple();
	}

	@Override
	public void setMultiple(float multiple) {
		scaleViewProxy.setMultiple(multiple);
	}
}
