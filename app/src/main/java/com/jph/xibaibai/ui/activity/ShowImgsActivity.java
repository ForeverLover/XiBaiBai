package com.jph.xibaibai.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.ViewPagerShowImgsAdapter;
import com.jph.xibaibai.ui.activity.base.BaseActivity;

import uk.co.senab.photoview.PViewPager;


/**
 * 显示多张图片的界面
 *
 * @author Jiang
 */
public class ShowImgsActivity extends BaseActivity {

    public static String INTENT_KEY_SHOWIMGS_IMGURLS = "key_imgurls";

    private String[] imgUrls;

    private PViewPager viewPagerImgs;// 使用修复了android系统缩放Bug的ViewPager

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_showimgs);
    }

    public static void start(Context context, String[] imgUrls) {
        Intent intent = new Intent(context, ShowImgsActivity.class);
        intent.putExtra(INTENT_KEY_SHOWIMGS_IMGURLS, imgUrls);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void initData() {
        super.initData();

        Intent intentData = getIntent();
        imgUrls = intentData
                .getStringArrayExtra(INTENT_KEY_SHOWIMGS_IMGURLS);
    }

    @Override
    public void initView() {

        viewPagerImgs = (PViewPager) findViewById(R.id.frashowimgs_pager);
        if (imgUrls != null)
            viewPagerImgs
                    .setAdapter(new ViewPagerShowImgsAdapter(this, imgUrls));

    }

    @Override
    public void initListener() {
        super.initListener();

        viewPagerImgs.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

}
