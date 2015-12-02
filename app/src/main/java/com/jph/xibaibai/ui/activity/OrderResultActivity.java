package com.jph.xibaibai.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.jph.xibaibai.R;
import com.jph.xibaibai.model.entity.Order;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 订单结果
 * Created by jph on 2015/9/18.
 */
public class OrderResultActivity extends TitleActivity {
    public static final String EXTRA_ORDER = "extra_order";

    private Order order;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orderresult);
    }

    @Override
    public void initData() {
        super.initData();
        order = (Order) getIntent().getSerializableExtra(EXTRA_ORDER);
    }

    @OnClick(R.id.orderresult_btn_detail)
    public void onClickOrderDetail(View v) {
        Intent intentOrderDetail = new Intent(this, OrderDetailIngActivity.class);
        intentOrderDetail.putExtra(OrderDetailIngActivity.EXTRA_ORDER, order);
        startActivity(intentOrderDetail);
        finish();
    }
}
