package com.jph.xibaibai.ui.activity;

import android.os.Bundle;
import android.view.View;

import com.jph.xibaibai.R;
import com.jph.xibaibai.ui.activity.base.TitleActivity;
import com.lidroid.xutils.view.annotation.event.OnClick;

/**
 * 我的钱包
 * Created by jph on 2015/8/30.
 */
public class WalletActivity extends TitleActivity implements View.OnClickListener {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallet);
    }

    @Override
    public void initView() {
        super.initView();
        setTitle("我的钱包");
    }

    @OnClick({R.id.wallet_vg_integral, R.id.wallet_vg_coupon, R.id.wallet_vg_balance})
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.wallet_vg_integral:
                startActivity(IntegralActivity.class);
                break;
            case R.id.wallet_vg_coupon:
                startActivity(CouponActivity.class);
                break;
            case R.id.wallet_vg_balance:
                startActivity(BalanceActivity.class);
                break;
        }
    }
}
