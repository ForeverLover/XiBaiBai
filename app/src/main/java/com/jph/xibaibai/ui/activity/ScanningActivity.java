package com.jph.xibaibai.ui.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.utils.SystermUtils;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Eric on 2015/11/18.
 * 扫描二维码
 */
public class ScanningActivity extends TitleActivity {

    @ViewInject(R.id.rating_img)
    ImageView rating_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scanning);
    }

    @Override
    public void initData() {
        super.initData();
        final RotateAnimation animation = new RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF,
                0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        animation.setDuration(5000);//设置动画持续时间
        rating_img.setAnimation(animation);

    }

    @Override
    public void initView() {
        super.initView();
    }
}
