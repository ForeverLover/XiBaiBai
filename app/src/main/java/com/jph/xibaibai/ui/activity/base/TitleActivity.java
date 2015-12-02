package com.jph.xibaibai.ui.activity.base;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.lidroid.xutils.ViewUtils;

/**
 * 带Title的基础Activity
 * Created by jph on 2015/8/19.
 */
public class TitleActivity extends BaseActivity {

    private View vTitle;

    private TextView txtTitle;// title文字
    private ImageView imgLeft;// 左边图片
    private ImageView imgRight;// 右边图片
    private Button btnLeft;// 左边按钮
    private Button btnRight;// 右边按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setSuperContentView(R.layout.activity_title);
    }

    @Override
    public void setContentView(int layoutResID) {
        View viewContent = View.inflate(this, layoutResID, null);
        ((FrameLayout) findViewById(R.id.title_frame_content))
                .addView(viewContent);
        ViewUtils.inject(this);
        initTitleView();
        init();
    }

    @Override
    public void setContentView(View view) {
        ((FrameLayout) findViewById(R.id.title_frame_content)).addView(view);
        ViewUtils.inject(this);
        initTitleView();
        init();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        ((FrameLayout) findViewById(R.id.title_frame_content)).addView(view);

        ViewUtils.inject(this);
        initTitleView();
        init();
    }

    protected int getTitleViewId() {
        return R.id.title_include_title;
    }

    private void initTitleView() {
        // ViewGroup decorView = (ViewGroup) getWindow().getDecorView();
        // LinearLayout layout = (LinearLayout) decorView.getChildAt(0);
        vTitle = findViewById(getTitleViewId());
        if (vTitle == null)
            return;
        vTitle.setVisibility(View.VISIBLE);
        txtTitle = (TextView) vTitle.findViewById(R.id.title_txt);
        imgLeft = (ImageView) vTitle.findViewById(R.id.title_img_left);
        btnLeft = (Button) vTitle.findViewById(R.id.title_btn_left);
        imgRight = (ImageView) vTitle.findViewById(R.id.title_img_right);
        btnRight = (Button) vTitle.findViewById(R.id.title_btn_right);
        imgLeft.setOnClickListener(titleBtnOnClickListener);
        imgRight.setOnClickListener(titleBtnOnClickListener);
        btnLeft.setOnClickListener(titleBtnOnClickListener);
        btnRight.setOnClickListener(titleBtnOnClickListener);

    }

    protected void showTitleView() {
        vTitle.setVisibility(View.VISIBLE);
    }

    protected void hideTitleView() {
        vTitle.setVisibility(View.GONE);
    }

    @Override
    public void setTitle(CharSequence title) {
        txtTitle.setText(title);
    }

    public void setBtnRight(CharSequence title){
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText(title);
    }

    @Override
    public void setTitle(int titleId) {
        txtTitle.setText(titleId);
    }

    @Override
    public void setTitleColor(int textColor) {
        txtTitle.setTextColor(textColor);
    }

    protected void showTitleImgLeft() {
        imgLeft.setVisibility(View.VISIBLE);
    }

    protected void showTitleImgLeft(int resId) {
        imgLeft.setVisibility(View.VISIBLE);
        imgLeft.setImageResource(resId);
    }

    protected ImageView getTitleImgLeft() {
        return imgLeft;
    }

    protected void hideTitleImgLeft() {
        imgLeft.setVisibility(View.GONE);
    }

    protected void showTitleImgRight(int resId) {
        imgRight.setVisibility(View.VISIBLE);
        imgRight.setImageResource(resId);
    }

    protected void hideTitleImgRight() {
        imgRight.setVisibility(View.GONE);
    }

    protected void showTitleBtnLeft(CharSequence text) {
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setText(text);
    }

    protected void showTitleBtnLeft(int resId) {
        btnLeft.setVisibility(View.VISIBLE);
        btnLeft.setText(resId);
    }

    protected void hideTitleBtnLeft() {
        btnLeft.setVisibility(View.GONE);
    }

    protected void showTitleBtnRight(CharSequence text) {
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText(text);
    }

    protected void showTitleBtnRight(int resId) {
        btnRight.setVisibility(View.VISIBLE);
        btnRight.setText(resId);
    }

    protected void hideTitleBtnRight() {
        btnRight.setVisibility(View.GONE);
    }

    protected void onClickTitleLeft(View v) {
        // 默认点击左边关闭当前界面
        finish();
    }

    protected void onClickTitleRight(View v) {

    }

    View.OnClickListener titleBtnOnClickListener = new View.OnClickListener(){
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.title_img_left:
                    onClickTitleLeft(v);
                    break;
                case R.id.title_btn_left:
                    onClickTitleLeft(v);
                    break;
                case R.id.title_img_right:
                    onClickTitleRight(v);
                    break;
                case R.id.title_btn_right:
                    onClickTitleRight(v);
                    break;

                default:
                    break;
            }
        }
    };
}
