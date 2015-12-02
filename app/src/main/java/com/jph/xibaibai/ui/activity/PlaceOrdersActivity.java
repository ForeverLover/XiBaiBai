package com.jph.xibaibai.ui.activity;

import android.os.Bundle;
import android.widget.TextView;

import com.jph.xibaibai.R;
import com.jph.xibaibai.ui.activity.base.BaseActivity;
import com.lidroid.xutils.view.annotation.ViewInject;

/**
 * Created by Eric on 2015/12/1.
 * 新下单页面
 */
public class PlaceOrdersActivity extends BaseActivity {

    @ViewInject(R.id.title_txt)
    private TextView title_txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_orders);
    }

    @Override
    public void initData() {
        super.initData();
    }

    @Override
    public void initView() {
        super.initView();
        title_txt.setText(getString(R.string.place_order));
    }
}
