package com.jph.xibaibai.mview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

/**
 * Created by Eric
 * Created Date 2015/8/25 20:56
 * Describe：
 */
public class SelfGridview extends GridView {
    public SelfGridview(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SelfGridview(Context context) {
        super(context);
    }

    public SelfGridview(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }
}
