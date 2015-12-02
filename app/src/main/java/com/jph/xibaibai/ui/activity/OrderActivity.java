package com.jph.xibaibai.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.jph.xibaibai.R;
import com.jph.xibaibai.adapter.base.PageFragAdapter;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.jph.xibaibai.ui.fragment.FinishOrderFragment;
import com.jph.xibaibai.ui.fragment.IngOrderFragment;
import com.lidroid.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单界面
 * Created by jph on 2015/8/28.
 */
public class OrderActivity extends TitleActivity implements ViewPager.OnPageChangeListener,
        RadioGroup.OnCheckedChangeListener {

    private PageFragAdapter mFragAdapter;

    @ViewInject(R.id.order_pager)
    ViewPager mViewPager;
    @ViewInject(R.id.order_rgroup_status)
    RadioGroup mRGroupStatus;
    @ViewInject(R.id.order_rbtn_ing)
    RadioButton mRBtnIng;//
    @ViewInject(R.id.order_rbtn_finish)
    RadioButton mRBtnFinish;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
    }

    @Override
    public void initData() {
        super.initData();
        List<Fragment> listFrag = new ArrayList<Fragment>();
        listFrag.add(new IngOrderFragment());
        listFrag.add(new FinishOrderFragment());
        mFragAdapter = new PageFragAdapter(getSupportFragmentManager(), listFrag);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("我的订单");

        mViewPager.setAdapter(mFragAdapter);
    }

    @Override
    public void initListener() {
        super.initListener();
        mViewPager.addOnPageChangeListener(this);
        mRGroupStatus.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.order_rbtn_ing:
                //进行中
                mViewPager.setCurrentItem(0);
//                mRBtnIng.setText(getString(R.string.order_prefix_checked) + getString(R.string.order_ing));
//                mRBtnFinish.setText(getString(R.string.order_finish));
                break;
            case R.id.order_rbtn_finish:
                //已完成
                mViewPager.setCurrentItem(1);
//                mRBtnIng.setText(getString(R.string.order_ing));
//                mRBtnFinish.setText(getString(R.string.order_prefix_checked) + getString(R.string.order_finish));
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        mRGroupStatus.check(position == 0 ? R.id.order_rbtn_ing : R.id.order_rbtn_finish);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
